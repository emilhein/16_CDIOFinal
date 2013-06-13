package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DecimalFormat;
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

public class ASE {
	
	//# New

	public ASE(Boundary boundary) {
		
		boolean first = true;
		
		while (true) {
			
			if (!first) {
			
				System.out.println();
				System.out.println("1. Connect to weight");
				System.out.println("0. Exit");
				System.out.println();
				
				if (boundary.readInt("", 0, 1) == 0) {
					break;
				}
				
			}

			first = false;
			
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
		private DatabaseAccess databaseAccess = null;
		
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
				databaseAccess = new DatabaseAccess();
				
			} catch (Exception e) {
				
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
			
			start();
			
		}
		
		//# Thread
		
		@Override
		public void run() {

			// 1: Bekræft
			// 0: Annuller/Tilbage

			int step = 0;
			Procedure procedure = null;
			
			try {
				
				while (true) {
				
					switch (step) {
					
						case 0:
							
							// 3. Operatøren indtaster operatør nr.
							// 4. Vægten svarer tilbage med operatørnavn som så godkendes
							procedure = new Procedure();
							getOperator(procedure);
							step += 1;
							break;
							
						case 1:
					
							// 5. Operatøren indtaster produktbatch nummer
							// 6. Vægten svarer tilbage med navn på recept der skal produceres (eks: saltvand med citron)
							if (getProductBatch(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;
					
						case 2:
							
							// 7. Operatøren kontrollerer at vægten er ubelastet og trykker ’ok’
							// 8. Vægten tareres
							// 9. Vægten beder om første tara beholder
							// 10. Operatør placerer første tarabeholder og trykker ’ok’
							// 11. Vægten af tarabeholder registreres
							// 12. Vægten tareres
							if (getContainerWeight(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;
							
						case 3:
							
							// 13. Vægten beder om raavarebatch nummer på første råvare
							if (getCommodityBatch(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;

						case 4:
							
							// 14. Operatøren afvejer op til den ønskede mængde og trykker ’ok’
							if (getCommodityWeight(procedure)) {
								step += 1;
							} else {
								step -= 1;
							}
							break;
					
						case 5:
							
							// 15. Pkt. 7 – 14 gentages indtil alle råvarer er afvejet
							procedure.productBatch.setStatus(1);
							procedure.commodityBatch.setMaengde(procedure.commodityBatch.getMaengde() - procedure.commodityWeight);
							
							databaseAccess.updateProductBatch(procedure.productBatch);
							databaseAccess.updateCommodityBatch(procedure.commodityBatch);
							databaseAccess.createProductBatchComp(new ProductBatchComp(procedure.productBatch.getPbId(), procedure.commodityBatch.getCbId(), procedure.containerWeight, procedure.commodityWeight, procedure.operator.getOprId()));
							
							procedure.recipeComp.remove(0);
							
							if (procedure.recipeComp.size() > 0) {
								step = 2;
							} else {
								step += 1;
							}
							break;
					
						case 6:
							
							// 16. Systemet sætter produktbatch nummerets status til ”Afsluttet”
							procedure.productBatch.setStatus(2);
							
							databaseAccess.updateProductBatch(procedure.productBatch);
							
							step += 1;
							break;
							
						default:
							
							// 17. Det kan herefter genoptages af en ny operatør
							step = 0;
							
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
				try {
					databaseAccess.close();
				} catch (Exception e) {
				}
				
			}
			
		}
		
		//# Functions
		
		private void getOperator(Procedure procedure) throws Exception {
			
			while (true) {
				
				int number = readInt("Operator:", "", "#");
				
				try {
					procedure.operator = databaseAccess.getOperator(number);
				} catch (DALException e) {
					display("Invalid");
					continue;
				}
				
				if (readInt(procedure.operator.getOprName() + "?", "1", "") != 1) {
					continue;
				}
				
				return;
				
			}
			
		}
		private boolean getProductBatch(Procedure procedure) throws Exception {
			
			while (true) {
				
				int number = readInt("Product batch:", "", "#");
				
				if (number == 0) {
					return false;
				}
				
				try {
					procedure.productBatch = databaseAccess.getProductBatch(number);
					procedure.recipe = databaseAccess.getRecipe(procedure.productBatch.getReceptId());
					procedure.recipeComp = databaseAccess.getRecipeCompList(procedure.productBatch.getReceptId());;
				} catch (DALException e) {
					display("Invalid");
					continue;
				}
				
				if (procedure.recipeComp.size() < 1) {
					display("Invalid");
					continue;				
				}
				
				if (readInt(procedure.recipe.getRecipeName() + "?", "1", "") != 1) {
					continue;
				}
				
				return true;
				
			}
			
		}
		private boolean getContainerWeight(Procedure procedure) throws Exception {
			
			while (true) {
				
				if (readInt("Clear weight", "1", "") != 1) {
					return false;
				}

				tare();
				
				if (readInt("Place container", "1", "") != 1) {
					continue;
				}

				procedure.containerWeight = weight();

				tare();
				
				return true;
				
			}
			
		}
		private boolean getCommodityBatch(Procedure procedure) throws Exception {
			
			while (true) {
				
				int commodityId = procedure.recipeComp.get(0).getRaavareId();
				int number = readInt("Commodity batch for " + commodityId + ":", "", "#");
				
				if (number == 0) {
					return false;
				}
				
				try {
					procedure.commodityBatch = databaseAccess.getCommodityBatch(number);
					procedure.commodity = databaseAccess.getCommodity(procedure.commodityBatch.getCommodityId());
				} catch (DALException e) {
					display("Invalid");
					continue;
				}
				
				if (procedure.commodityBatch.getCommodityId() != commodityId) {
					display("Wrong");
					continue;
				}
				
				if (procedure.commodityBatch.getMaengde() < procedure.recipeComp.get(0).getNomNetto() - (procedure.recipeComp.get(0).getNomNetto() * procedure.recipeComp.get(0).getTolerance())) {
					display("Missing");
					continue;
				}
				
				if (readInt(procedure.commodity.getCommodityName() + "?", "1", "") != 1) {
					continue;
				}
				
				return true;
				
			}
			
		}
		private boolean getCommodityWeight(Procedure procedure) throws Exception {
			
			double target = procedure.recipeComp.get(0).getNomNetto();
			double tolerance = procedure.recipeComp.get(0).getNomNetto() * procedure.recipeComp.get(0).getTolerance();
			boolean stopped = false;
			int pause = 0;
			
			while (true) {
			
				if (pause <= 0 && !stopped) {
					if (readInt("Place commodity", "1", "") != 1) {
						return false;
					}
					pause = 10;
				}
				pause -= 1;
				
				procedure.commodityWeight = weight();
				
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
				
				if (!stopped) {
					display("Stop");
					stopped = true;
					continue;
				}
				
				return true;
				
			}
			
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
		
		//# Classes
		
		private class Procedure {
			
			public Operator operator = null;
			public ProductBatch productBatch = null;
			public Recipe recipe = null;
			public List<RecipeComp> recipeComp = null;
			public CommodityBatch commodityBatch = null;
			public Commodity commodity = null;
			public double containerWeight = 0;
			public double commodityWeight = 0;
			
		}
		
	}
	
}
