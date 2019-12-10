package clientThread;


import java.net.*;
import java.io.*;
import java.util.*;
public class CommuterClient extends Thread {
	private int id;
	private Socket socket = null;
	public 	static long time = System.currentTimeMillis();
	private String whichLane;
	private boolean finished = false;
	private char platform;
	private int trainCap;
	
	public CommuterClient(int i, boolean b) {
		setName("Commuter-" + i);
		id = i;
		
		if (b) //do they have a subscription
			whichLane = "EZpass";
		else 
			whichLane = "Cash";
		
		int whichTrain = random()%2; //randomize which station is closest to commuters job
		if (whichTrain==0) {
			platform = 'A';
		}
		else
			platform = 'B';
		
		try {
			socket = new Socket("149.4.211.58", 9087); //ip for hawk server, port 9087
		}
		catch (Exception e) {
			
		}
	}
	
	public void run() {
		try {
			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String serverComm;
			String stringid = Integer.toString(id);
			while (!finished) {
				serverComm = in.readLine();
				if (serverComm==null) 
					break;
				switch (serverComm) {
				
					//if reads in these strings from server
					case "start": 
						msg(" connects to server.");
						out.println("Commuter");
						out.println(stringid);
						out.println(whichLane);
						out.println(platform);
						break;
					
					
					case "OkCommuter":
						msg(" starts commute to NYC.");
						Thread.sleep(1000);
						out.println("enterLane");
						msg(" enters the "+ whichLane +" lane");
						break;
						
					case "leavelane":
						if (whichLane=="EZpass")
							Thread.sleep(1000);
						else
							Thread.sleep(2000);
						out.println("exitbridge");
						msg(" exits the bridge");
						break;
					
					case "garage":
						msg(" enters the garage.");
						out.println("gopark");
						break;
					
					case "leavegarage":
						msg(" is allowed to park and exits the garage.");
						out.println("entersubway");
						break;
					
					case "leavetrain":
						msg(" exits the subway and heads to work.");
						finished = true;
						out.println("disconnect");
						break;
							
				}
			}
			msg(" disconnected.");
			out.close();
			in.close();
		}
		catch (Exception e) {
			
		}
	}
	
	private int random() {
		Random number = new Random();
		int value = number.nextInt(90000) + 30000;
		return value;
	}
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
		 }
}
