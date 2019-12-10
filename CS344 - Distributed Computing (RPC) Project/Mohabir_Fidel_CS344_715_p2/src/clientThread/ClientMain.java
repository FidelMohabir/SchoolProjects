package clientThread;

import java.net.*;
import java.util.*;
import java.io.*;

public class ClientMain {
	
	public static void main(String[] args) {
		int numComm = 15;
		int numSubs = 0;
		int numNoSubs = 0;
		Boolean b;
		
		for (int i = 1; i <= numComm; i++) {
			if (numSubs > (numNoSubs+3)) { //keep Subs and No Subs within 3 of each other
				b = false;
				numNoSubs++;
			}
			else if (numNoSubs > (numSubs+3)) {
				b = true;
				numSubs++;
			}
			else if(random()%2==1) { //if odd number they do have a ezpass sub
				b = true;
				numSubs++;
			}
			else { //if even number they don't have ezpass sub
				b = false;
				numNoSubs++;
			}
			
			CommuterClient worker = new CommuterClient(i, b);
			worker.start();
			if (b) b = false;
			else b = true;
		}
		
		AttendantClient attend1 = new AttendantClient(1);
		AttendantClient attend2 = new AttendantClient(2);
		attend1.start();
		attend2.start();
		
		TrainClient trainA = new TrainClient('A', 100);
		TrainClient trainB = new TrainClient('B', 200);
		trainA.start();
		trainB.start();
		

	}
	
	private static int random() {
		Random number = new Random();
		int value = number.nextInt(90000) + 30000;
		return value;
	}
}
