package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import database.DALException;
import database.DatabaseAccess;
import database_objects.Commodity;
import database_objects.CommodityBatch;
import database_objects.Operator;
import database_objects.ProductBatch;
import database_objects.ProductBatchComp;
import database_objects.Recipe;
import database_objects.RecipeComp;

/**
 * This class control the weight.
 * @author Jens Werner Nielsen (s123115).
 */
public class ASE {
	
	//# New

	/**
	 * When this class gets initialized it shows a menu to start background threads that sends commands to the weight or simulator class.
	 * @param boundary This parameter is used to pass the boundary class from the main class to this class.
	 */
	public ASE(Boundary boundary) {
		
		boolean first = true;

		// Show a menu the user can use to connect to a weight or simulator class.
		while (true) {
			
			// Do not show this menu the first time.
			if (!first) {
			
				System.out.println();
				System.out.println("1. Connect to weight");
				System.out.println("0. Exit");
				System.out.println();
				
				try {
					if (boundary.readInt("", 0, 1) == 0) {
						break;
					}
				} catch (Exception e) {
					break;
				}
				
			}
			
			first = false;
			
			System.out.println();
			System.out.println("Address:");
			System.out.println();
			
			// Waits for the user to enter the address.
			String address;
			try {
				address = boundary.readString("");
			} catch (Exception e) {
				break;
			}
			
			System.out.println();
			System.out.println("Port:");
			System.out.println();
			
			// Waits for the user to enter the port number.
			int port;
			try {
				port = boundary.readInt("", 0, 65535);
			} catch (Exception e) {
				break;
			}

			// Restarts the menu if the port number is zero.
			if (port == 0) {
				continue;
			}
			
			System.out.println();
			System.out.println("Terminal:");
			System.out.println();
			
			// Waits for the user to enter the terminal number.
			int terminal;
			try {
				terminal = boundary.readInt("", 0, Integer.MAX_VALUE);
			} catch (Exception e) {
				break;
			}

			// Restarts the menu if the port number is zero.
			if (terminal == 0) {
				continue;
			}
			
			System.out.println();
			
			// Connect to weight.
			try {
				new Weight(address, port, terminal);
				System.out.println("Connected to weight.");
			} catch (Exception e) {
				System.err.println("Cannot connect to weight (" + e.getMessage() + ").");
			}
			
		}
		
	}
	
	//# Classes

	/**
	 * This class controls a single weight by sending commands to it.
	 */
	private class Weight extends Thread {

		private Socket socket = null;
		private BufferedReader reader = null;
		private DataOutputStream writer = null;
		private DatabaseAccess databaseAccess = null;
		
		private String address;
		private int port;
		private int terminal;
		
		//# New
		
		/**
		 * When this class gets initialized it opens a connection and starts a background thread that sends commands to the weight or simulator class.
		 * @param address This parameter specifies the address or hostname of the weight.
		 * @param port This parameter specifies the port number to be used.
		 * @param terminal This parameter specifies the terminal number the weight has.
		 * @throws Exception Throw an exception when the connection cannot be established.
		 */
		public Weight(String address, int port, int terminal) throws Exception {
			
			this.address = address;
			this.port = port;
			this.terminal = terminal;
			
			try {

				// Connect to the weight.
				socket = new Socket(address, port);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new DataOutputStream(socket.getOutputStream());
				databaseAccess = new DatabaseAccess();
				
			} catch (Exception e) {
				
				// Could not connect.
				try {
					socket.close();
				} catch (Exception ex) {
				}
				try {
					reader.close();
				} catch (Exception ex) {
				}
				try {
					writer.close();
				} catch (Exception ex) {
				}
				try {
					databaseAccess.close();
				} catch (Exception ex) {
				}
				
				throw e;
				
			}
			
			// Start the background thread.
			start();
			
		}
		
		//# Thread
		
		/**
		 * This function contains the code for the background thread.
		 */
		@Override
		public void run() {

			// 1: Bekr�ft
			// 0: Annuller/Tilbage

			int step = 0;
			Procedure procedure = null;
			
			try {
				
				// Loop this code to continue doing steps.
				while (true) {
				
					// Select the current step (using the variable named step).
					switch (step) {
					
						case 0:
							
							// Create a new Procedure.
							procedure = new Procedure();
							
							// Ask for the operator.
							getOperator(procedure);
							
							// Continue to the next step.
							step += 1;
							break;
							
						case 1:
					
							// Ask for the product batch number and continue to the next or previous step.
							if (getProductBatch(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;
					
						case 2:
							
							// Ask for the containers weight and continue to the next or previous step.
							if (getContainerWeight(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;
							
						case 3:

							// Ask for the commodity batch number and continue to the next or previous step.
							if (getCommodityBatch(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;

						case 4:

							// Ask for the commodity weight and continue to the next or previous step.
							if (getCommodityWeight(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;
					
						case 5:

							// Change the status of this product batch to 'under production'.
							procedure.productBatch.setStatus(1);
							
							// Reduce the available amount of this commodity.
							procedure.commodityBatch.setMaengde(procedure.commodityBatch.getMaengde() - procedure.commodityWeight);
							
							// Save changes to the database.
							databaseAccess.updateProductBatch(procedure.productBatch);
							databaseAccess.updateCommodityBatch(procedure.commodityBatch);
							databaseAccess.createProductBatchComp(new ProductBatchComp(procedure.productBatch.getPbId(), procedure.commodityBatch.getCbId(), procedure.containerWeight, procedure.commodityWeight, procedure.operator.getOprId(), terminal));
							
							// Remove the finished component.
							procedure.recipeComps.remove(procedure.recipeComp);
							
							// Repeat from step 2 if there are any missing components.
							if (procedure.recipeComps.size() > 0) {
								step = 2;
							} else {
								step += 1;
							}
							break;
					
						case 6:

							// Change the status of this product batch to 'finished'.
							procedure.productBatch.setStatus(2);

							// Save changes to the database.
							databaseAccess.updateProductBatch(procedure.productBatch);
							databaseAccess.setEndTimeStamp(procedure.productBatch);
							
							// Go to the default step.
							step += 1;
							break;
							
						default:
							
							// Restart.
							step = 0;
							
					}

				}
				
			} catch (Exception e) {
			
				// Something went wrong.
				System.err.println("Weight crashed ('" + address + ":" + port + "'): " + e.getMessage());
			
			} finally {
				
				// Close the connection.
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
				try {
					databaseAccess.close();
				} catch (Exception e) {
				}
				
			}
			
		}
		
		//# Functions
		
		/**
		 * This function asks the weight for the operator number.
		 * @param procedure This parameter specifies the current procedure.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private void getOperator(Procedure procedure) throws Exception {
			
			// Continue until a valid operator number is entered.
			while (true) {
				
				// Ask the operator for the operator number.
				int number = readInt("Operator:", "", "#");
				
				// Find the operator in the database.
				try {
					procedure.operator = databaseAccess.getOperator(number);
				} catch (DALException e) {
					display("Invalid");
					continue;
				}
				
				// Check if the operator is blocked.
				if (procedure.operator.getRights() == 5) {
					display("Blocked");
					continue;
				}
				
				// Ask the operator to confirm.
				if (readInt(procedure.operator.getOprName() + "?", "1", "") != 1) {
					continue;
				}
				
				return;
				
			}
			
		}

		/**
		 * This function asks the weight for the product batch number.
		 * @param procedure This parameter specifies the current procedure.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private boolean getProductBatch(Procedure procedure) throws Exception {
			
			// Continue until a valid product batch number is entered.
			while (true) {
				
				// Ask the operator for the product batch number.
				int number = readInt("Product batch:", "", "#");
				
				// Return to the previous step.
				if (number == 0) {
					return false;
				}
				
				// Find the product batch in the database.
				try {
					procedure.productBatch = databaseAccess.getProductBatch(number);
					procedure.recipe = databaseAccess.getRecipe(procedure.productBatch.getReceptId());
					procedure.recipeComps = databaseAccess.getRestRecipeComp(procedure.productBatch.getPbId());
				} catch (DALException e) {
					display("Invalid");
					continue;
				}
				
				// Check it is not already finished.
				if (procedure.recipeComps.size() < 1) {
					display("Done");
					continue;	
				}
				
				// Ask the operator to confirm.
				if (readInt(procedure.recipe.getRecipeName() + "?", "1", "") != 1) {
					continue;
				}
				
				return true;
				
			}
			
		}
		
		/**
		 * This function asks the weight for the weight of the container.
		 * @param procedure This parameter specifies the current procedure.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private boolean getContainerWeight(Procedure procedure) throws Exception {
			
			// Continue until the weight is tared.
			while (true) {

				// Ask the operator to confirm.
				if (readInt("Clear weight", "1", "") != 1) {
					return false;
				}

				// Tare the weight.
				tare();

				// Ask the operator to confirm.
				if (readInt("Place container", "1", "") != 1) {
					continue;
				}

				// Get the container weight.
				procedure.containerWeight = weight();

				// Tare the weight.
				tare();
				
				return true;
				
			}
			
		}
		
		/**
		 * This function asks the weight for the commodity batch number.
		 * @param procedure This parameter specifies the current procedure.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private boolean getCommodityBatch(Procedure procedure) throws Exception {
			
			// Continue until a valid commodity batch number is entered.
			loop:
			while (true) {
				
				// Ask the operator for the commodity batch number.
				int number = readInt("Commodity batch:", "", "#");

				// Return to the previous step.
				if (number == 0) {
					return false;
				}
				
				// Find the commodity batch in the database.
				try {
					procedure.commodityBatch = databaseAccess.getCommodityBatch(number);
					procedure.commodity = databaseAccess.getCommodity(procedure.commodityBatch.getCommodityId());
				} catch (DALException e) {
					display("Invalid");
					continue;
				}
				
				// Check whether this commodity could be used in this product.
				check:
				while (true) {
					for (RecipeComp recipeComp : procedure.recipeComps) {
						if (recipeComp.getCommodityId() == procedure.commodityBatch.getCommodityId()) {
							procedure.recipeComp = recipeComp;
							break check;
						}
					}
					display("Wrong");
					continue loop;
				}
				
				// Check there is enough in this commodity batch.
				if (procedure.commodityBatch.getMaengde() < procedure.recipeComp.getNomNetto() - (procedure.recipeComp.getNomNetto() * procedure.recipeComp.getTolerance() * 0.01)) {
					display("Missing");
					continue;
				}
				
				// Ask the operator to confirm.
				if (readInt(procedure.commodity.getCommodityName() + "?", "1", "") != 1) {
					continue;
				}
				
				return true;
				
			}
			
		}

		/**
		 * This function asks the weight for the weight of the commodity.
		 * @param procedure This parameter specifies the current procedure.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private boolean getCommodityWeight(Procedure procedure) throws Exception {
			
			// Calculate the target weight and tolerance.
			double target = procedure.recipeComp.getNomNetto();
			double tolerance = procedure.recipeComp.getNomNetto() * procedure.recipeComp.getTolerance() * 0.01;
			boolean stopped = false;
			
			// Continue until the weight is near the target and within the tolerance.
			while (true) {

				// Get the commodity weight.
				procedure.commodityWeight = weight();
				
				// Checks if the weight is too high or too low and shows the distance to the target on the display.
				boolean greater = procedure.commodityWeight > target + tolerance;
				boolean less = procedure.commodityWeight < target - tolerance;
				if (greater || less) {
					String message = (greater ? "+ " : "- ") + Double.toString(Math.abs(procedure.commodityWeight - target));
					if (message.length() > 7) {
						display(message.substring(0, 7));
					} else {
						display(message);
					}
					stopped = false;
					continue;
				}
				
				// Show stop message on the display when the weight is acceptable.
				if (!stopped) {
					display("Stop");
					stopped = true;
					continue;
				}
				
				return true;
				
			}
			
		}

		/**
		 * This function shows a message on the weights display.
		 * @param message This parameter specifies the message to be shown.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private void display(String message) throws Exception {
			
			// Send command.
			writer.writeBytes("D \"" + message + "\"\r\n");
			
			// Receive response.
			String response;
			while (true) {
				response = reader.readLine();
				if (response.equals("D A")) {
					break;
				}
				System.err.println();
				System.err.println("Received message '" + response + "' differs from 'D A'.");
			}
		
			// Wait
			Thread.sleep(2000);

			// Send command.
			writer.writeBytes("DW\r\n");
						
			// Receive response.
			while (true) {
				response = reader.readLine();
				if (response.equals("DW A")) {
					break;
				}
				System.err.println();
				System.err.println("Received message '" + response + "' differs from 'DW A'.");
			}
			
		}

		/**
		 * This function asks the operator to enter an integer.
		 * @param message This parameter specifies the message to be shown.
		 * @param input This parameter specifies the default answer.
		 * @param unit This parameter specifies the unit.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private int readInt(String message, String input, String unit) throws Exception {
			
			// Send command.
			writer.writeBytes("RM20 4 \"" + message + "\" \"" + input + "\" \"" + unit + "\"\r\n");
			
			// Receive response.
			String response;
			while (true) {
				response = reader.readLine();
				if (response.equals("RM20 B")) {
					break;
				}
				System.err.println();
				System.err.println("Received message '" + response + "' differs from 'RM20 B'.");
			}
			
			// Receive response.
			final Pattern pattern = Pattern.compile("^RM20 A \"([^\"]*)\"$");
			Matcher matcher;
			while (true) {
				response = reader.readLine();
				matcher = pattern.matcher(response);
				if (matcher.matches()) {
					break;
				}
				System.err.println();
				System.err.println("Received message '" + response + "' differs from pattern '^RM20 A \"([^\"]*)\"$'.");
			}
			
			return Integer.parseInt(matcher.group(1));
		}
		

		/**
		 * This function gets the weight.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private double weight() throws Exception {
			
			// Send command.
			writer.writeBytes("S\r\n");
			
			// Receive response.
			final Pattern pattern = Pattern.compile("^S S \\s*-?([0-9\\.]*) kg$");
			String response;
			Matcher matcher;
			while (true) {
				response = reader.readLine();
				matcher = pattern.matcher(response);
				if (matcher.matches()) {
					break;
				}
				System.err.println();
				System.err.println("Received message '" + response + "' differs from pattern '^S S \\s*-?([0-9\\.]*) kg$'.");
			}
			
			return Double.parseDouble(matcher.group(1));
		}
		
		/**
		 * This function tara the weight.
		 * @throws Exception Throws an exception if the connection wes lost.
		 */
		private double tare() throws Exception {
			
			// Send command.
			writer.writeBytes("T\r\n");
			
			// Receive response.
			final Pattern pattern = Pattern.compile("^T S \\s*-?([0-9\\.]*) kg$");
			String response;
			Matcher matcher;
			while (true) {
				response = reader.readLine();
				matcher = pattern.matcher(response);
				if (matcher.matches()) {
					break;
				}
				System.err.println();
				System.err.println("Received message '" + response + "' differs from pattern '^T S \\s*-?([0-9\\.]*) kg$'.");
			}
			
			return Double.parseDouble(matcher.group(1));
		}
		
		//# Classes
		
		/**
		 * This class is used to store the database objects.
		 */
		private class Procedure {
			
			public Operator operator = null;
			public ProductBatch productBatch = null;
			public Recipe recipe = null;
			public List<RecipeComp> recipeComps = null;
			public RecipeComp recipeComp = null;
			public CommodityBatch commodityBatch = null;
			public Commodity commodity = null;
			public double containerWeight = 0;
			public double commodityWeight = 0;
			
		}
		
	}
	
}
