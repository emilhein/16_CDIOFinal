package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ASE {
	
	//# New

	public ASE(Boundary boundary) {
		
		while (true) {
			
			System.out.println();
			System.out.println("1. Connect to weight");
			System.out.println("0. Exit");
			System.out.println();
			
			if (boundary.readInt("", 0, 1) == 0) {
				break;
			}

			System.out.println();
			System.out.println("Address:");
			System.out.println();
			
			String address = boundary.readString("");
			
			System.out.println();
			System.out.println("Port:");
			System.out.println();
			
			int port = boundary.readInt("", 0, 65535);

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
				join(); // ### KAN IKKE STARTE FLERE PÅ EN GANG ###
				
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

			// 1: Bekræft
			// 0: Annuller/Tilbage
			
			try {
				operator:
				while (true) {

					// 3. Operatøren indtaster operatør nr.
					// 4. Vægten svarer tilbage med operatørnavn som så godkendes
					int operator = getOperator();
					
					productBatch:
					while (true) {
						
						// 5. Operatøren indtaster produktbatch nummer
						// 6. Vægten svarer tilbage med navn på recept der skal produceres (eks: saltvand med citron)
						int productBatch = getProductBatch();
						if (productBatch == 0) {
							continue operator;
						}
						
						containerWeight:
						while (true) {
							
							// 7. Operatøren kontrollerer at vægten er ubelastet og trykker ’ok’
							// 8. Vægten tareres
							// 9. Vægten beder om første tara beholder
							// 10. Operatør placerer første tarabeholder og trykker ’ok’
							// 11. Vægten af tarabeholder registreres
							// 12. Vægten tareres
							double containerWeight = getContainerWeight();
							if (containerWeight == Double.NaN) {
								continue productBatch;
							}
							
							materialBatch:
							while (true) {
								
								// 13. Vægten beder om raavarebatch nummer på første råvare
								int materialBatch = getMaterialBatch();
								if (materialBatch == 0) {
									continue containerWeight;
								}
								
								// 14. Operatøren afvejer op til den ønskede mængde og trykker ’ok’
								double materialWeight = getMaterialWeight();
								if (containerWeight == Double.NaN) {
									continue materialBatch;
								}
								
								// ### TILFØJ INDTASTNINGER TIL EN LISTE ###

								// ### ER DER FLERE RÅVARER DER SKAL AFVEJES? ###
								
								// 15. Pkt. 7 – 14 gentages indtil alle råvarer er afvejet
								if (false) {
									continue containerWeight;
								}
								
								break productBatch;
								
							}
							
						}
						
					}
					
					// 16. Systemet sætter produktbatch nummerets status til ”Afsluttet”
					
					// ### IKKE LAVET ENDNU ###
					
					// 17. Det kan herefter genoptages af en ny operatør
					
				}
			} catch (Exception e) {
				System.err.println("Weight crashed ('" + address + ":" + port + "'): " + e.getMessage());
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
		
		//# Functions
		
		private int getOperator() throws Exception {
			
			while (true) {
				
				int number = readInt("Operator:", "", "#");
				String name;
				
				// === HARDCODED ===
				if (number == 1) {
					name = "Bo";
				} else {
					display("Not found");
					continue;
				}
				// === HARDCODED ===
				
				if (readInt(name + "?", "1", "") != 1) {
					continue;
				}
				
				return number;
				
			}
			
		}
		private int getProductBatch() throws Exception {
			
			while (true) {
				
				int number = readInt("Product batch:", "", "#");
				
				if (number == 0) {
					return 0;
				}
				
				String name;
				
				// === HARDCODED ===
				if (number == 1) {
					name = "Saltvand";
				} else {
					display("Not found");
					continue;
				}
				// === HARDCODED ===
				
				if (readInt(name + "?", "1", "") != 1) {
					continue;
				}
				
				return number;
				
			}
			
		}
		private double getContainerWeight() throws Exception {
			
			while (true) {
				
				if (readInt("Clear weight", "1", "") != 1) {
					return Double.NaN;
				}

				tare();
				
				if (readInt("Place container", "1", "") != 1) {
					continue;
				}

				double weight = weight();

				tare();
				
				return weight;
				
			}
			
		}
		private int getMaterialBatch() throws Exception {
			
			while (true) {
				
				int number = readInt("Material batch:", "", "#");
				
				if (number == 0) {
					return 0;
				}
				
				String name;
				
				// === HARDCODED ===
				if (number == 1) {
					name = "Vand";
				} else {
					display("Not found");
					continue;
				}
				// === HARDCODED ===
				
				if (readInt(name + "?", "1", "") != 1) {
					continue;
				}
				
				return number;
				
			}
			
		}
		private double getMaterialWeight() throws Exception {
			
			if (readInt("Place material", "1", "") != 1) {
				return Double.NaN;
			}
			
			return weight();
			
		}
		
		private void display(String message) throws Exception {
			
			// Send
			writer.writeBytes("D \"" + message + "\"\r\n");
			
			// Receive
			String response = reader.readLine();
			if (!response.equals("D A")) {
				throw new Exception("Received message '" + response + "' differs from 'D A'.");
			}
			
			// Wait
			Thread.sleep(2000);

			// Send
			writer.writeBytes("DW\r\n");
						
			// Receive
			response = reader.readLine();
			if (!response.equals("DW A")) {
				throw new Exception("Received message '" + response + "' differs from 'DW A'.");
			}
			
		}
		private int readInt(String message, String input, String unit) throws Exception {
			
			// Send
			writer.writeBytes("RM20 4 \"" + message + "\" \"" + input + "\" \"" + unit + "\"\r\n");
			
			// Receive
			String response = reader.readLine();
			if (!response.equals("RM20 B")) {
				throw new Exception("Received message '" + response + "' differs from 'RM20 B'.");
			}
			
			// Receive
			final Pattern pattern = Pattern.compile("^RM20 A \"([^\"]*)\"$");
			response = reader.readLine();
			Matcher matcher = pattern.matcher(response);
			if (!matcher.matches()) {
				throw new Exception("Received message '" + response + "' differs from pattern '^RM20 A \"([^\"]*)\"$'.");
			}
			
			return Integer.parseInt(matcher.group(1));
		}
		private double weight() throws Exception {
			
			// Send
			writer.writeBytes("S\r\n");
			
			// Receive
			final Pattern pattern = Pattern.compile("^S S \\s*-?([0-9\\.]*) kg$");
			String response = reader.readLine();
			Matcher matcher = pattern.matcher(response);
			if (!matcher.matches()) {
				throw new Exception("Received message '" + response + "' differs from pattern '^S S \\s*-?([0-9\\.]*) kg$'.");
			}
			
			return Double.parseDouble(matcher.group(1));
		}
		private double tare() throws Exception {
			
			// Send
			writer.writeBytes("T\r\n");
			
			// Receive
			final Pattern pattern = Pattern.compile("^T S \\s*-?([0-9\\.]*) kg$");
			String response = reader.readLine();
			Matcher matcher = pattern.matcher(response);
			if (!matcher.matches()) {
				throw new Exception("Received message '" + response + "' differs from pattern '^T S \\s*-?([0-9\\.]*) kg$'.");
			}
			
			return Double.parseDouble(matcher.group(1));
		}
		
	}
	
}
