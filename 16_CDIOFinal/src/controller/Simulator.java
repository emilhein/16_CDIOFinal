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
	private int port = 8005;
	ServerSocket listener;
	private double netto;
	private double tara;
	private Boundary boundary;
	//# New
	
	public Simulator(Boundary boundary) {
		
		try {
			this.boundary = boundary;
			listener = new ServerSocket(port);//Hvor den lytter.
			System.out.println(" Started server on port " + port + ".");
			start();
			consol();
		} catch (Exception e) {
			System.out.println(e.getMessage());
				}
	
	}
		
	private void consol() {
		loop:
		while(true){	
			
			System.out.println();
			System.out.println("1. aendre tara");
			System.out.println("2. aendre netto");
			System.out.println("3. afslut");
			System.out.println();
			double tara = 0; 
			double brutto = 0;
			
			switch(boundary.readInt(1, 3)){
				
				case 1:
					System.out.println("Indtast veagten på skaalen efterfulgt af enter: ");
					tara = boundary.readInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
					break;
				case 2:
					System.out.println("Indtast veagten på skaalen + indhold efterfulgt af enter: ");
					brutto = boundary.readInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
					break;
				default:
					
					break loop;
			}
				
		}
		
	}

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
							writer.writeBytes("S S " + ("" + netto).replace(",", ".") + " kg\r\n");

						} else if (line.equals("T")) {
							//Saet og retuner tara(tara bliver sat til nuvaerende brutto).
								
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
							writer.writeBytes("RM20 A \"" + boundary.readString().replace("\"", "_") + "\"\r\n");
						
						}else if (matcherRM20_4.matches()) {

							// Vis tre meddelser på displayet og retuner den indtastede værdi.
							writer.writeBytes("RM20 B\r\n");
							System.out.println(matcherRM20_4.group(1));
								System.out.println(matcherRM20_4.group(2) + matcherRM20_4.group(3));
								writer.writeBytes("RM20 A \"" + boundary.readInt(Integer.MIN_VALUE,Integer.MAX_VALUE) + "\"\r\n");
							
						} else if (line.equals("Z")) {
						
							// Fjern meddelsen fra displayet og vend tilbage til visning af netto vægt.
							writer.writeBytes("Veagten er nulstillet: ");
							tara = 0;
							netto = 0;
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
	

