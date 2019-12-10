package clientThread;

import java.net.*;
import java.util.*;
import java.io.*;

public class TrainClient extends Thread{
	private char trainLine;
	private int trainID;
	private Socket socket = null;
	private boolean noMoreComm = false;
	private static long time = System.currentTimeMillis();
	
	public TrainClient(char c, int i) {
		trainLine = c;
		trainID = i;
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
			
			while (!noMoreComm) {
				servComm = in.readLine();
				
				switch(servComm) {
				
					//if reads in these strings from server
					case "start":
						msg(" connects to server.");
						out.println("Train");
						out.println(trainID);
						out.println(trainLine);
						break;
					case "OkTrain":
						out.println("specialwait");
						break;
					case "commatstation":
						msg(" heads towards station.");
						out.println("arrivstation");
						break;
					case "atstation":
						msg (" on  line " + trainLine + " has arrived.");
						out.println("leavestation");
						msg(" leaves station" + trainLine);
						break;
					case "nomorecomm":
						noMoreComm = true;
						out.println("disconnect");
						break;		
				}
			}
			out.close();
			in.close();
			msg (" on line " + trainLine + " are no longer a concern.");
			msg(" disconnected.");
		}
		catch (Exception e) {
			
		}
	}
	
	public void msg(String m) {
		 System.out.println("["+(System.currentTimeMillis()-time)+"] "+"Subway Train-" + trainLine +trainID + ": "+m);
	
	}
}
