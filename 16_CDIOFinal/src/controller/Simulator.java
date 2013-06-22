package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class simulates the weight.
 * @author Jens Werner Nielsen (s123115).
 */
public class Simulator extends Thread {

	private ServerSocket listener;
	private Boundary boundary;
	private double brutto = 0;
	private double tara = 0;
	private boolean closing = false;

	// # New
	
	/**
	 * When this class gets initialized it starts a background thread that handles commands send by the ASE class.
	 * @param boundary This parameter is used to pass the boundary class from the main class to this class.
	 */
	public Simulator(Boundary boundary) {

		this.boundary = boundary;

		// Continue to ask for at port number until a valid one is entered.
		while (true) {

			System.out.println();
			System.out.println("Port:");
			System.out.println();

			// Wait for the user to type a port number.
			int port;
			try {
				port = boundary.readInt("", 0, 65535);
			} catch (Exception e1) {
				break;
			}
			
			// Exit if the port number is zero.
			if (port == 0) {
				break;
			}

			// Start a listener to listen for incomming connections on the specified port.
			try {
				listener = new ServerSocket(port);
			} catch (Exception e) {
				System.err.println();
				System.err.println("Cannot start simulator (" + e.getMessage() + ").");
				continue;
			}
		
			break;
		}
		
		// Start the background thread to handle commands send by the ASE.
		start();
		
		System.out.println();
		System.out.println("Simulator started.");

		// Show a menu the user can use to set brutto and tara.
		menu:
		while (true) {

			System.out.println();
			System.out.println("1. Change brutto");
			System.out.println("2. Change tara");
			System.out.println("0. Exit");
			System.out.println();

			// Wait for the user to type a number between zero and two, with '!' as a prefix.
			int response;
			try {
				response = boundary.readInt("!", 0, 2);
			} catch (Exception e) {
				break;
			}
			
			switch (response) {

				case 1:

					// The user has chosen to change brutto.
					System.out.println();
					System.out.println("Brutto:");
					System.out.println();
					try {
						brutto = boundary.readDouble("!", Double.MIN_VALUE, Double.MAX_VALUE);
					} catch (Exception e) {
						break menu;
					}
					break;

				case 2:

					// The user has chosen to change tara.
					System.out.println();
					System.out.println("Tara:");
					System.out.println();
					try {
						tara = boundary.readDouble("!", Double.MIN_VALUE, Double.MAX_VALUE);
					} catch (Exception e) {
						break menu;
					}
					break;

				default:

					// The user has chosen to close the simulator.
					closing = true;
					try {
						join();
					} catch (InterruptedException e) {
					}
					break menu;

			}

		}

	}

	// # Functions

	/**
	 * This function contains the code for the background thread.
	 */
	public void run() {

		// Creates the patterns for the D, RM20 8 and RM20 4 commands.
		Pattern patternD = Pattern.compile("^D \"([^\"]*)\"$");
		Pattern patternRM20_8 = Pattern.compile("^RM20 8 \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$");
		Pattern patternRM20_4 = Pattern.compile("^RM20 4 \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$");
		
		Socket socket = null;
		BufferedReader reader = null;
		DataOutputStream writer = null;

		// Continue accepting connections until close() is called.
		while (!closing) {
			
			System.out.println();
			System.out.println("Waiting for client...");

			// Wait for and accept the first incomming connection.
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
			
			
			try {
				
				// Continue handling commands until close() is called.
				while (!closing) {
					
					// Receive command.
					String line = reader.readLine();
					
					// The connection was closed.
					if (line == null) {
						break;
					}
					
					// Match command against all patterns.
					Matcher matcherD = patternD.matcher(line);
					Matcher matcherRM20_8 = patternRM20_8.matcher(line);
					Matcher matcherRM20_4 = patternRM20_4.matcher(line);
					
					if (line.equals("S")) {

						// Received command 'S'. Return netto.
						writer.writeBytes("S S " + Double.toString(brutto - tara).replace(",", ".") + " kg\r\n");

					} else if (line.equals("T")) {
						
						// Received command 'T'. Tare weight and return new tara.
						tara = brutto;
						writer.writeBytes("T S " + Double.toString(tara).replace(",", ".") + " kg\r\n");

					} else if (line.equals("DW")) {

						// Received command 'DW'. Clear the display and return confirmation.
						writer.writeBytes("DW A\r\n");

					} else if (matcherD.matches()) {

						// Received command 'D'. Show message on the display and return confirmation.
						System.out.println();
						System.out.println(matcherD.group(1));
						writer.writeBytes("D A\r\n");

					} else if (matcherRM20_8.matches()) {

						// Received command 'RM20 8'. Show all three messages on the display, return confirmation and answer.
						writer.writeBytes("RM20 B\r\n");
						System.out.println();
						System.out.println(matcherRM20_8.group(1));
						System.out.println(matcherRM20_8.group(2) + " " + matcherRM20_8.group(3));
						System.out.println();
						writer.writeBytes("RM20 A \"" + boundary.readString("\\#").replace("\"", "") + "\"\r\n");

					} else if (matcherRM20_4.matches()) {

						// Received command 'RM20 4'. Show all three messages on the display, return confirmation and answer.
						writer.writeBytes("RM20 B\r\n");
						System.out.println();
						System.out.println(matcherRM20_4.group(1));
						System.out.println(matcherRM20_4.group(2) + " " + matcherRM20_4.group(3));
						System.out.println();
						writer.writeBytes("RM20 A \"" + boundary.readInt("\\#", Integer.MIN_VALUE, Integer.MAX_VALUE) + "\"\r\n");

					} else if (line.equals("Z")) {

						// Received command 'Z'. Clear tara and brutto.
						tara = 0;
						brutto = 0;

					}

				}
				
			} catch (Exception e) {
				System.err.println();
				System.err.println("Crashed (" + e.getMessage() + ").");
			}
			
			// Close connection.
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
		
		// Close listener.
		try {
			listener.close();
		} catch (Exception e) {
		}
	
	}

}
