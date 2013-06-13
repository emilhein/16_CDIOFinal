package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulator extends Thread{

	private ServerSocket listener;
	private Boundary boundary;
	private double brutto;
	private double tara;
	
	//# New
	
	public Simulator(Boundary boundary) {
		
		this.boundary = boundary;
		
		while (true) {
			
			System.out.println();
			System.out.println("Port:");
			System.out.println();
			
			int port = boundary.readInt("", 0, 65535);

			System.out.println();
			
			try {
				listener = new ServerSocket(port);
				start();
				System.out.println("Simulator started.");
			} catch (Exception e) {
				System.err.println("Cannot start simulator (" + e.getMessage() + ").");
				continue;
			}
	
			menu:
			while(true){
				
				System.out.println();
				System.out.println("!1. Change brutto");
				System.out.println("!2. Change tara");
				System.out.println("!0. Exit");
				System.out.println();
				
				switch(boundary.readInt("!", 0, 2)){
					
					case 1:
						
						System.out.println();
						System.out.println("Brutto:");
						System.out.println();
						brutto = boundary.readDouble("!", Double.MIN_VALUE, Double.MAX_VALUE);
						break;
						
					case 2:
						
						System.out.println();
						System.out.println("Tara:");
						System.out.println();
						tara = boundary.readDouble("!", Double.MIN_VALUE, Double.MAX_VALUE);
						break;
						
					default:			
						break menu;
				}
					
			}
			
			break;
			
		}
	
	}

	//# Close
	
	public void close() {
		
		try {
			listener.close();
		} catch (IOException e) {
		}
		
	}
		
		//# Functions
		
	
		public void run() {
			try {
				while (true) {
					new Client(listener.accept());
				}
			} catch (IOException e) {
			}
		}
		
		//# Classes
	
		private class Client extends Thread {
			
			Socket socket;
			BufferedReader reader;
			DataOutputStream writer;
			
			
			//# New
			
			public Client(Socket socket) {
				System.out.println(" " + socket.getInetAddress().getHostAddress() + ": Connected.");
				this.socket = socket;
				
				try {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					writer = new DataOutputStream(socket.getOutputStream());
					start();
				} catch (IOException e) {
				}
			}
			
			//# Functions
			
			@Override
			public void run() {
				Pattern patternRM20_8 = Pattern.compile("^RM20 8 \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$"); //Moenster
				Pattern patternRM20_4= Pattern.compile("^RM20 4 \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$");
				try {
					while (true) {
						String line = reader.readLine();
						if (line == null) {
							break;
						}
						System.out.println(" " + socket.getInetAddress().getHostAddress() + ": Received: " + line + ".");
						Matcher matcherRM20_8 = patternRM20_8.matcher(line);
						Matcher matcherRM20_4 = patternRM20_4.matcher(line);
						if (line.equals("S")) {
							
							// Retuner netto vægt.
							writer.writeBytes("S S " + ("" + (brutto - tara)).replace(",", ".") + " kg\r\n");

						} else if (line.equals("T")) {
							//Saet og retuner tara(tara bliver sat til nuvaerende brutto).
							tara = brutto;	
							writer.writeBytes("T S " + ("" + tara).replace(",", ".") + " kg\r\n");

						} else if (line.equals("DW")) {
						
							// Fjern meddelsen fra displayet og vend tilbage til visning af netto vægt.
							
							writer.writeBytes("DW A\r\n");
						
						} else if (line.startsWith("D ") && line.length() > 2) {
							
							// Vis meddelse på displayet(paa vaegten).
							
							writer.writeBytes("D A\r\n");

						} else if (matcherRM20_8.matches()) {

							// Vis tre meddelser på displayet og retuner den indtastede værdi.
							writer.writeBytes("RM20 B\r\n");
							System.out.println(matcherRM20_8.group(1));
							System.out.println(matcherRM20_8.group(2) + matcherRM20_8.group(3));
							writer.writeBytes("RM20 A \"" + boundary.readString("@").replace("\"", "_") + "\"\r\n");
						
						}else if (matcherRM20_4.matches()) {

							// Vis tre meddelser på displayet og retuner den indtastede værdi.
							writer.writeBytes("RM20 B\r\n");
							System.out.println(matcherRM20_4.group(1));
								System.out.println(matcherRM20_4.group(2) + matcherRM20_4.group(3));
								writer.writeBytes("RM20 A \"" + boundary.readInt("@", Integer.MIN_VALUE,Integer.MAX_VALUE) + "\"\r\n");
							
						} else if (line.equals("Z")) {
						
							// Fjern meddelsen fra displayet og vend tilbage til visning af netto vægt.
							writer.writeBytes("Veagten er nulstillet: ");
							tara = 0;
							brutto = 0;
						}
						else {
							
							// Retuner en fejlmeddelse.
							writer.writeBytes("Error\r\n"); //# TODO: Send rigtig fejlkode.
						
						}
						
					}
				} catch (IOException e) {
				}
				System.out.println(" " + socket.getInetAddress().getHostAddress() + ": Disconnected.");
			}
		}
		
		
	}
	

