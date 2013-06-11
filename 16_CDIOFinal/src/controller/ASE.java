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
			
			start();
			
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
					int operatorNumber = readInt("Operator:", "", "#");
					
					// 4. Vægten svarer tilbage med operatørnavn som så godkendes
					String operatorName;
					try {
						operatorName = resolveOperator(operatorNumber);
					} catch (Exception e) {
						display("Not found");
						continue operator;
					}
					if (readInt(operatorName + "?", "1", "") != 1) {
						continue operator;
					}
					
					productBatch:
					while (true) {
						
						// 5. Operatøren indtaster produktbatch nummer.
						int productBatchNumber = readInt("Product batch:", "", "#");
						if (productBatchNumber == 0) {
							continue operator;
						}
					
						// 6. Vægten svarer tilbage med navn på recept der skal produceres (eks: saltvand med citron)
						String productBatchName;
						try {
							productBatchName = resolveProductBatch(productBatchNumber);
						} catch (Exception e) {
							display("Not found");
							continue productBatch;
						}
						if (readInt(productBatchName + "?", "1", "") != 1) {
							continue productBatch;
						}
						
					}
					
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
		
		private String resolveOperator(int number) throws Exception {
			
			if (number == 1) {
				return "Bo";
			}
			
			throw new Exception("Cannot find operator with number '" + number + "'.");
		}
		private String resolveProductBatch(int number) throws Exception {
			
			if (number == 1) {
				return "Saltvand";
			}
			
			throw new Exception("Cannot find product batch with number '" + number + "'.");
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
		
	}
	
}
