package routeServers;
import java.net.*;
import java.util.*;
import java.io.*;

public class RouteServer extends Thread{
	private static int numCommuters=15; //# of commuters 
	private static int trainCapacity = 10; //default train capacity
	private static Vector ezPassLane = new Vector(); //If they have EZ pass they go in this notification object
	private static Vector cashLane = new Vector(); //if they don't then they go in this notification object
	private static Object attendAvail = new Object(); //attendants wait for commuters on this condition var
	private static Object carAvail = new Object(); //commuters wait for attendants on this condition var
	private static Object subStation = new Object(); //commutetrs wait for subway train on this cond var;
	private static Object tLineA = new Object(); //different train lines in different directions
	private static Object tLineB = new Object(); //different train lines in different directions
	private static Object trainWaiting = new Object(); //special wait for train until the first commuter comes into subway
	private static boolean atEZBooth = false; //shared variable, to see if the booth is occupied currently
	private static boolean atCashBooth = false; //shared variables, to see if the booth is occupied currently
	private static boolean platA = false; //is train at platform A
	private static boolean platB = false; //is train at platform B
	private static int trainCapA; //used to check the train capacity after random commuters enter before our commuters
	private static int trainCapB; //same as above
	private static int numCarWaiting = 0; //shared local var to see how many cars waiting on line
	private static int numAttendAvail = 0; //shared local var to see if an attendant is ready to serve the car
	private static int numCarAttended = 0; //total num of cars that went through; eventually is equal to numCommuters
	private static int numAtStation = 0; //used to 'wakeup' trains 
	private static int numTrainAttended = 0; //total of commuters that have used the trains
	public 	static long time = System.currentTimeMillis();
	private static Object routeMonitor = new Object();
	private Socket clientHelper;
	private int id;
	private String clientName;
	private String subscrpt = null;
	private char platform;

	
	public RouteServer(Socket socket) {
		clientHelper = socket;
	}
	
	public void run() {
		try {
			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(clientHelper.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientHelper.getInputStream()));
			
			String send = "start";
			String receive;
			String stringid;
			out.println(send);
			Boolean finished = false;
			
			while (true) {
				receive = in.readLine();
				if (receive==null) {
					break;
				}
				if (finished) {
					break;
				}
				switch (receive) {
					
					//What type of thread is it?
					case "Commuter":
						stringid = in.readLine();
						subscrpt = in.readLine();
						platform = in.readLine().charAt(0);
						id = Integer.parseInt(stringid);
						clientName = "CommuterClient-" + id;
						System.out.println(clientName + ": connected");
						out.println("OkCommuter");
						break;
						
					case "Attendant":
						stringid = in.readLine();
						id = Integer.parseInt(stringid);
						clientName = "AttendantClient-" + id;
						System.out.println(clientName + ": connected");
						out.println("OkAttendant");
						break;
						
					case "Train":
						stringid = in.readLine();
						platform = in.readLine().charAt(0);
						id = Integer.parseInt(stringid);
						clientName = "TrainClient-" + id;
						System.out.println(clientName + ": connected");
						out.println("OkTrain");
						break;
						
					//Commuter methods
					case "enterLane":
						boolean whichLane;
						if (subscrpt=="EZpass")
							whichLane = true;
						else
							whichLane = false;
						crossBridge(whichLane);
						out.println("leavelane");
						break;
						
					case "exitbridge":
						boolean exitLane;
						if (subscrpt=="EZpass")
							exitLane = true;
						else 
							exitLane = false;
						leaveBridge(exitLane);
						out.println("garage");
						break;
						
					case "gopark":
						goPark();
						out.println("leavegarage");
						break;
						
					case "entersubway":
						goSubway(platform, id);
						out.println("leavetrain");
						break;
					
					
					//Attendant methods
					case "attendcar":
						boolean attend = attendCar();
						if (attend) {
							out.println("nocar");
						}
						else {
							out.println("parkcar");
						}
						break;	
					case "waitforcomm":
						waitForCommuter(true);
						out.println("parkcar");
						break;
					case "letcommpark":
						boolean done = letCommPark();
						if (done) {
							out.println("doneforday");
						}
						else 
							out.println("nextcar");
						break;
						
					//Train methods
					case "specialwait":
						specialWait();
						out.println("commatstation");
						break;
					case "arrivstation":
						arrivAtStat(platform, id);
						out.println("atstation");
						break;
						
					case "leavestation":
						boolean status1 = leaveStation(platform, id);
						if (status1)
							out.println("nomorecomm");
						else
							out.println("commatstation");
						break;
						
					//disconnect method	
					case "disconnect":
						msg(clientName + ": disconnected");
						finished = true;	
				}
			}
			out.close();
			in.close();
			
		}
		catch (Exception e) {
			
		}
	}
	
	
	//Bridge Methods
	public void crossBridge(boolean b) { 
		Object lane = new Object(); //Unique object to block in
		synchronized (lane) { //synchronized on that unique object
				if (cannotCrossToll(lane, b))  //checks if can cross toll
					while(true) {
						try {
							lane.wait();  //wait on unique object 
							break; 
						} //used while true loop to stop race condition here since they are not synchronized to same monitor cond var
						catch (InterruptedException e) {
							continue;
						}
					}
		}
	}
	
	private boolean cannotCrossToll(Object o, boolean b) {
		synchronized (routeMonitor) {
			boolean status; //return boolean variable
			boolean waiting; //this variable is used to take on the boolean values of either booths
			if (b) { //if they have subscription
				waiting = atEZBooth; //waiting is referencing EZ pass booth
				if (waiting) { //if its true
					ezPassLane.addElement(o); //add element to EzPassLane vector
					status = true; //return true
				}
				else {
					atEZBooth = true; //if not true, make EZBooth occupied now by switching it to true
					status = false; //return false
				}
			}
			else { //if they don't have subscription
				waiting = atCashBooth; //waiting is referencing CashBooth
				if(waiting) { //if it is waiting
					cashLane.addElement(o); //add element to cashLane vector
					status = true; //return true
				}
				else {
					atCashBooth = true; //if not true, make CashBooth occupied now by switching it to true
					status = false; //return false
				}
			}
		return status;
		}
	}
	
	public void leaveBridge(boolean b) {
		synchronized (routeMonitor) {
			if(b) { //if EZPass subscription
				atEZBooth = false; //no longer using booth so switch EZBooth to false
			
				if(ezPassLane.size()> 0) { //if theres anyone in the ezPassLane vector
					synchronized (ezPassLane.elementAt(0)) { //synchronize on them
						ezPassLane.elementAt(0).notify(); //notify one
					}
					ezPassLane.removeElementAt(0); //remove that element from the vector
				}

			}
			else { 
				atCashBooth = false; //no longer using booth so switch CashBooth to false
			
				if(cashLane.size()> 0) { //if theres anyone in the cashlane vector
					synchronized (cashLane.elementAt(0)) { //synchronize with them
						cashLane.elementAt(0).notify(); //allow the next one to proceed to the booth
					}
					cashLane.removeElementAt(0); //remove them from the vector
				}
			}
	
		}
	}
	
	//Parking Garage Methods
	public void goPark() {
		synchronized (routeMonitor) { //I made it this way because I wanted the attendants to specifically unblock commuters
			numCarWaiting++; //increment car waiting
				synchronized (attendAvail) { //notify an attendant that you are waiting
					attendAvail.notify();
				}

		}
		synchronized (carAvail) {  
			try {
				carAvail.wait(); //block on that cond var
			}
			catch (InterruptedException e) {
				
			}
		}
		synchronized (routeMonitor) {
			numAttendAvail--; //decrement number of Attendants Available
		}
	}
	
	public boolean attendCar() {
		boolean noCar = false; //if there is a waiting car or not
			synchronized (routeMonitor) {
				numAttendAvail++; //Attendant is available
				if (numCarWaiting==0) {
					noCar = true; //if no cars then set noCar true
				}
			}
			return noCar;
	}
	
	public void waitForCommuter(Boolean b) {
		if (b) {
			synchronized (attendAvail) {
				try {
					attendAvail.wait();
				}
				catch (InterruptedException e) {
					
				}
			}
		}
	}
	
	
	public boolean letCommPark() {
		boolean status = false;
		synchronized (routeMonitor) {
			numCarWaiting--; //when unblocked by commuter decrement car waiting
			numCarAttended++; //increment car attended
			synchronized (carAvail) {
				carAvail.notify(); //wake up commuter thats blocked on carAvail con var
			}
			if (numCarAttended>=numCommuters) { //I realize that this number will go over but this is what changes status variable
				synchronized (attendAvail) { //notify all attendants(even if there is 1 other, i wanted the code to work with varying amounts)
					attendAvail.notify(); 
				}
				status = true; //no more commuters = true
			}
		}
		return status;
	}
	
	//Subway methods
	public void goSubway(char c, int i) {
		boolean onTrain = false; //individual variable for each commuter
		synchronized (routeMonitor) { //wake all trains up when first commuter gets to subway station so trains aren't constantly notifying the program
			numAtStation++;
			if (numAtStation>=1) {
				synchronized (trainWaiting) {
					trainWaiting.notifyAll();
				}
			}
		}
		while (true) {
			synchronized (subStation) { //go to con var subStation until train arrives and allows to board
				try {
					subStation.wait(); //wait at Subway Station
				}
				catch (InterruptedException e) {
					
				}
			}
			synchronized (routeMonitor) { //synchronized to monitor 
				if (platA) { //if train is available on this platform
					if (trainCapA>0) { //A commuter attempts to board the train as long as that specific trains Capacity is greater than 0
						trainCapA--;  //enter the train then decrement capacity by 1
						onTrain = true; //commuter is on train
					}
					else if (trainCapA==0) {
						platA = false;
					}
				}
				else if (platB) { //if train is available on this platform
					if (trainCapB>0) { //same thing as above but with the b line
					trainCapB--;
					onTrain = true;
					}
					else if (trainCapB==0) {
						platB = false;
					}
				}
			}
			if (platA && onTrain) { //since we must now wait on the train we choose which train they entered
				synchronized (tLineA) { //synchronized to train on Line A on cond variable
						try {
							msg("Commuter-" + i + " enters train on line "+ c); 
							tLineA.wait(); //wait on cond variable
							break;
						}
						catch (InterruptedException e) {
							continue;
						}
				}
			}
			else if (platB && onTrain) { //same as above but for line B
				synchronized (tLineB) {
						try {
							msg("Commuter-" + i + " enters train on line "+ c);
							tLineB.wait();
							break;
						}
						catch (InterruptedException e) {
							continue;
						}
				}
			}
		}
		synchronized (routeMonitor) { //when train lets commuters off they synchronize to monitor object and increment shared variable numTrainAttended
			numTrainAttended++; //which is used to let trains know when to end
		}
	}
	
	public void specialWait() {
		boolean noCommAtSt = false; //no Commuters at Station
		synchronized (routeMonitor) {
			if (numAtStation==0) //checks if there are any commuters at the station
				noCommAtSt = true; //then set no Commuters at Station true
		}
		if (noCommAtSt) { //blocking on the trainWaiting cond var
			synchronized (trainWaiting) { //special wait so it doesn't keep posting messages
				try {
					trainWaiting.wait();  //trainWaiting cond var only used to stop trains from running before a commuter gets there
				}
				catch (InterruptedException e) {
					
				}
			}	
		}
	}
	
	public void arrivAtStat(char c, int i) { //arrive at station method, takes in what line (c) and the number of the train (i)
		synchronized (routeMonitor) { //checks which line, synchronized to monitor
			if (c=='A') { //if line A
				platA = true; //train is on this platform
				trainCapA = (trainCapacity - (random()%7+1)); //capacity after random commuters enter
				msg((10-trainCapA) + " random commuter(s) enter the train.");
			}
			else {
				platB = true; //same as above
				trainCapB = (trainCapacity - (random()%7+1));
				msg((10-trainCapB) + " random commuter(s) enter the train.");
			}
			
			synchronized (subStation) { //wakes up all and commuters will try to get on the train if its available on the platform or block on subStation again
				subStation.notifyAll();
			}
		}
		
	}
	
	
	public boolean leaveStation(char c, int i) { //the leave station method for trains, returns true if all commuters are gone; synchronized to monitor
		synchronized (routeMonitor) {
			boolean status = false; //are there no more commuters left
			try {
				Thread.sleep(3000); //simulate travel time from leaving station to the end of the line
			}
			catch(InterruptedException e) {
			
			}
			if (c=='A' ) { //if on Line A
				platA = false; //then it cannot be at platA so change this to false
				synchronized (tLineA) { 
					tLineA.notifyAll(); //notify all commuters on this train to leave
				}
			}
			else { //if on line B same as above
				platB = false;
				synchronized (tLineB) {
					tLineB.notifyAll();
				}
			}
			if (numTrainAttended>=numCommuters) { //I realize this hangs a bit after all commuters have left before it ends
				status = true; //changes value of status if we've gone through all the commuters
			}
			return status;
		}
	}
	
	//Other methods
	private int random() {
		Random number = new Random();
		int value = number.nextInt(90000) + 30000;
		return value;
	}
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] " +m);
		 }
}
