package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ASE {
	
	//# New

	public ASE(Boundary boundary) {
		
		while (true) {
			
			System.out.println();
			System.out.println("1. Connect to weight");
			System.out.println("0. Exit");
			System.out.println();
			
			if (boundary.readInt(0, 1) == 0) {
				break;
			}

			System.out.println();
			System.out.println("Address:");
			System.out.println();
			
			String address = boundary.readString();
			
			System.out.println();
			System.out.println("Port:");
			System.out.println();
			
			int port = boundary.readInt(0, 65535);

			System.out.println();
			
			try {
				new Weight(address, port);
				System.out.println("Connected to weight.");
			} catch (Exception e) {
				System.out.println("Cannot connect to weight (" + e.getMessage() + ").");
			}
			
		}
		
	}
	
	//# Classes
	
	private class Weight extends Thread {

		private Socket socket = null;
		private BufferedReader reader = null;
		private DataOutputStream writer = null;
		
		private String address;
		private int port;
		
		//# New
		
		public Weight(String address, int port) throws Exception {
			
			this.address = address;
			this.port = port;
			
			try {

				socket = new Socket(address, port);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new DataOutputStream(socket.getOutputStream());

				start();

			} finally {
				try {
					socket.close();
				} catch (Exception e) {
				}
				try {
					reader.close();
				} catch (Exception e) {
				}
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
			
		}
		
		//# Properties
		
		public String getAddress() {
			
			return address;
		}
		public int getPort() {
			
			return port;
		}
		
		//# Thread
		
		@Override
		public void run() {

			

			
		}
		
	}
	
}
