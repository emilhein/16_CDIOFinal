package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulator extends Thread {

	private ServerSocket listener;
	private Boundary boundary;
	private double brutto = 0;
	private double tara = 0;
	private boolean closing = false;

	// # New

	public Simulator(Boundary boundary) {

		this.boundary = boundary;

		while (true) {

			// Start
			
			System.out.println();
			System.out.println("Port:");
			System.out.println();

			int port = boundary.readInt("", 0, 65535);

			try {
				listener = new ServerSocket(port);
				start();
			} catch (Exception e) {
				System.err.println();
				System.err.println("Cannot start simulator (" + e.getMessage() + ").");
				continue;
			}
			
			System.out.println();
			System.out.println("Simulator started.");

			// Menu
			
			menu:
			while (true) {

				System.out.println();
				System.out.println("1. Change brutto");
				System.out.println("2. Change tara");
				System.out.println("0. Exit");
				System.out.println();

				switch (boundary.readInt("!", 0, 2)) {

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
						closing = true;
						try {
							join();
						} catch (InterruptedException e) {
						}
						break menu;

				}

			}

			break;

		}

	}

	// # Functions

	public void run() {

		Pattern patternD = Pattern.compile("^D \"([^\"]*)\"$");
		Pattern patternRM20_8 = Pattern.compile("^RM20 8 \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$");
		Pattern patternRM20_4 = Pattern.compile("^RM20 4 \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$");
		
		Socket socket = null;
		BufferedReader reader = null;
		DataOutputStream writer = null;
		
		while (!closing) {
			
			// Accept
			
			System.out.println();
			System.out.println("Waiting for client...");
			
			try {
				socket = listener.accept();
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				System.err.println();
				System.err.println("Client connection failed (" + e.getMessage() + ").");
				continue;
			}
			
			System.out.println();
			System.out.println("Client connected (" + socket.getInetAddress().getHostAddress() + ").");
			
			// Process
			
			try {
				while (!closing) {
					
					// Receive
					
					String line = reader.readLine();
					
					if (line == null) {
						break;
					}
					
					// Process

					Matcher matcherD = patternD.matcher(line);
					Matcher matcherRM20_8 = patternRM20_8.matcher(line);
					Matcher matcherRM20_4 = patternRM20_4.matcher(line);
					
					if (line.equals("S")) {

						// Retuner netto.
						
						writer.writeBytes("S S " + Double.toString(brutto - tara).replace(",", ".") + " kg\r\n");

					} else if (line.equals("T")) {
						
						// Sæt og retuner tara (tara bliver sat til nuvaerende butto).
						
						tara = brutto;
						writer.writeBytes("T S " + Double.toString(tara).replace(",", ".") + " kg\r\n");

					} else if (line.equals("DW")) {

						// Fjern meddelsen fra displayet og vend tilbage til visning af netto vægt.

						writer.writeBytes("DW A\r\n");

					} else if (matcherD.matches()) {

						// Vis meddelse på displayet.

						System.out.println();
						System.out.println(matcherD.group(1));
						writer.writeBytes("D A\r\n");

					} else if (matcherRM20_8.matches()) {

						// Vis tre meddelser på displayet og retuner den indtastede værdi.
						
						writer.writeBytes("RM20 B\r\n");
						System.out.println();
						System.out.println(matcherRM20_8.group(1));
						System.out.println(matcherRM20_8.group(2) + " " + matcherRM20_8.group(3));
						System.out.println();
						writer.writeBytes("RM20 A \"" + boundary.readString("\\#").replace("\"", "") + "\"\r\n");

					} else if (matcherRM20_4.matches()) {

						// Vis tre meddelser på displayet og retuner den indtastede værdi.
						
						writer.writeBytes("RM20 B\r\n");
						System.out.println();
						System.out.println(matcherRM20_4.group(1));
						System.out.println(matcherRM20_4.group(2) + " " + matcherRM20_4.group(3));
						System.out.println();
						writer.writeBytes("RM20 A \"" + boundary.readInt("\\#", Integer.MIN_VALUE, Integer.MAX_VALUE) + "\"\r\n");

					} else if (line.equals("Z")) {

						// Fjern meddelsen fra displayet og vend tilbage til visning af netto vægt.
						
						tara = 0;
						brutto = 0;

					}

				}
			} catch (Exception e) {
				System.err.println();
				System.err.println("Crashed (" + e.getMessage() + ").");
			}
			
			// Close
			
			try {
				socket.close();
			} catch (IOException e) {
			}
			try {
				reader.close();
			} catch (IOException e) {
			}
			try {
				writer.close();
			} catch (IOException e) {
			}
			
			System.out.println();
			System.out.println("Client disconnected (" + socket.getInetAddress().getHostAddress() + ").");
			
		}
		
		// Close
		
		try {
			listener.close();
		} catch (Exception e) {
		}
	
	}

}
