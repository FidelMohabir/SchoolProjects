package clientThread;

import java.io.*;
import java.net.Socket;

public class AttendantClient extends Thread {
	private int attendID;
	private Socket socket = null;
	private boolean noMoreCar = false;
	public static long time = System.currentTimeMillis();
	
	public AttendantClient(int i) {
		attendID = i;
		
		try {
			socket = new Socket("149.4.211.58", 9087);
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
			
			String servComm;
			String stringid = Integer.toString(attendID);
			
			while (!noMoreCar) {
				servComm = in.readLine();
				switch (servComm) {
				
					//if reads in these strings from server
					case "start":
						msg(" connects to server.");
						out.println("Attendant");
						out.println(stringid);
						break;
						
					case "OkAttendant":
						msg(" arrives at work.");
						out.println("attendcar");
						break;
						
					case "nocar":
						msg(" waiting for commuter.");
						out.println("waitforcomm");
						break;
						
					case "parkcar":
						msg(" allows commuter to park.");
						out.println("letcommpark");
						break;
						
					case "nextcar":
						out.println("attendcar");
						break;
						
					case "doneforday":
						msg(" goes home for the day.");
						out.println("disconnect");
						noMoreCar = true;
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
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+"Attendant-" + attendID + ": "+m);
	
	}
}
