package routeServers;

import java.net.*;
import java.io.*;

public class ServerMain {
	
	public static void main(String[] args) {
		String serverAddr = "149.4.211.58";
		
		try {
			ServerSocket routeSocket = new ServerSocket(9087);
			System.out.println("Main Route Server: " + serverAddr + ":" + "9087");
			System.out.println("Server status: online");
			while (true) {
				Socket socket = routeSocket.accept();
				RouteServer serverThread = new RouteServer(socket);
				serverThread.start();
			}
		}
		catch (Exception e) {
			
		}
	}

}
