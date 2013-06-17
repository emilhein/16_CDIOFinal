package web;

import java.util.ArrayList;
import java.util.List;
import database.DALException;
import database.DatabaseAccess;
import database_objects.CommodityBatch;
import database_objects.Operator;
import database_objects.Commodity;
import database_objects.ProductBatch;
import database_objects.Recipe;
import database_objects.RecipeComp;

public class Session {

	private static DatabaseAccess databaseAccess;
	private static List<Page> pages = new ArrayList<Page>();
	private Operator operator = null;
	
	//# New
	
	public Session() {

		if (databaseAccess == null) {
			try {
				databaseAccess = new DatabaseAccess();
			} catch (DALException e) {
				e.printStackTrace();
			}
			pages.add(new Page("Home", "Home", 4));
			pages.add(new Page("Operators", "Operators", 1));
			pages.add(new Page("Commodities", "Commodities", 2));
			pages.add(new Page("Commodity Batches", "CommodityBatches", 3));
			pages.add(new Page("Recipes", "Recipes", 2));
			pages.add(new Page("Recipe Components", "RecipeComponents", 2));
			pages.add(new Page("Product Batches", "ProductBatches", 3));
		}

	}
	
	//# Properties
	
	public List<Page> getPages() {
		
		return pages;
	}
	public boolean isLoggedIn() {
		
		return operator != null;
	}
	public String getName() {
		
		return operator.getOprName();
	}
	public int getRights() {
		
		return operator.getRights();
	}
	public List<Operator> getOperators() {
		
		try {
			return databaseAccess.getOperatorList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Commodity> getCommodities() {
	
		try {
			return databaseAccess.getCommodityList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Recipe> getRecipes() {
		
		try {
			return databaseAccess.getRecipeList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<CommodityBatch> getCommodityBatches() {
		
		try {
			return databaseAccess.getCommodityBatchList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<CommodityBatch> getLowCommodityBatches() {
		
		try {
			// TODO Kim skal lave en metode i database_access som jeg kan bruge
		} catch (DALException) {
	}
	public List<ProductBatch> getProductBatches() {
		
		try {
			return databaseAccess.getProductBatchList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<RecipeComp> getRecipeComponents(String recipeId) {
		
		if (recipeId != null) {
			try {
				
				int parsedRecipeId = Integer.parseInt(recipeId);
				
				try {
					return databaseAccess.getRecipeCompList(parsedRecipeId);
				} catch (DALException e) {
					e.printStackTrace();
					return null;
				}
				
			} catch (Exception e) {
			}
		}
		
		try {
			return databaseAccess.getRecipeCompList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//# Functions
	
	public Page getPage(String page) {
		
		if (page != null) {
			for (Page x : pages) {
				if (page.equalsIgnoreCase(x.getName()) && getRights() <= x.getRightsRequired()) {
					return x;
				}
			}
		}
		
		return pages.get(0);
	}
	public String login(String id, String password) {
		
		logout();
		
		// Id
		
		if (id == null || id.length() < 1) {
			return "You must enter a id.";
		}

		int parsedId;
		
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "You must enter a valid id.";
		}
		
		// Password
		
		if (password == null || password.length() < 1) {
			return "You must enter a password.";
		}
		
		// Check
		
		Operator operator;
		
		try {
			operator = databaseAccess.getOperator(parsedId);
		} catch (Exception e) {
			System.err.println("Wrong user id or password (" + e.getMessage() + ").");
			return "Wrong user id or password.";
		}
		
		if (!operator.getPassword().equals(password)) {
			return "Wrong user id or password.";
		}
		
		if (operator.getRights() == 5) {
			return "Operator is blocked.";
		}
		
		this.operator = operator;
		return null;
	}
	public void logout() {
		
		operator = null;
		
	}
	public static String updateOperator(String id, String name, String initials, String password, String rights) {
		
		// Id
		
		Operator operator;
		int parsedId;
		
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}
		
		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must be between 1 and 99999999.";
		}
		
		try {
			operator = databaseAccess.getOperator(parsedId);
		} catch (Exception e) {
			return "Could not find operator (" + e.getMessage() + ").";
		}
		
		// Name
		
		if (!name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}
		
		// Initials
		
		if (!initials.matches("^.{2,4}$")) {
			return "Initials length must be between 2 and 4 characters.";
		}
		
		// Password
		
		if (!password.matches("^.{5,8}$")) {
			return "Password length must be between 5 and 8 characters.";
		}
		
		// Rights
		
		int parsedRights;
		
		try {
			parsedRights = Integer.parseInt(rights);
		} catch (Exception e) {
			return "Rigths must be a number.";
		}
		
		if (parsedRights < 1 || parsedRights > 5) {
			return "Rights must be between 1 and 5.";
		}
		
		// Update
		
		operator.setOprName(name);
		operator.setIni(initials);
		operator.setPassword(password);
		operator.setRights(parsedRights);
		
		try {
			databaseAccess.updateOperator(operator);
		} catch (Exception e) {
			return "Could not update operator (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addOperator(String id, String name, String initials, String cpr, String password, String rights) {
		
		// Id
		
		int parsedId;
		
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}
		
		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must between 1 and 99999999.";
		}
		
		// Name
		
		if (!name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}
		
		// Initials
		
		if (!initials.matches("^.{2,4}$")) {
			return "Initials length must be between 2 and 4 characters.";
		}
		
		// CPR
		
		if (!cpr.matches("^[0-9]{10}$")) {
			return "CPR must be a 10 digit number.";
		}
		
		// Password
		
		if (!password.matches("^.{5,8}$")) {
			return "Password length must be between 5 and 8 characters.";
		}
		
		// Rights
		
		int parsedRights;
		
		try {
			parsedRights = Integer.parseInt(rights);
		} catch (Exception e) {
			return "Rigths must be a number.";
		}
		
		if (parsedRights < 1 || parsedRights > 5) {
			return "Rights must between 1 and 5.";
		}
		
		// Add
		
		try {
			databaseAccess.createOperator(new Operator(parsedId, name, initials, cpr, password, parsedRights));
		} catch (Exception e) {
			return "Could not add operator (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addCommodity(String id, String name, String supplier) {

		// Id
		
		int parsedId;
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}

		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must between 1 and 99999999.";
		}
		
		// Name

		if (!name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}

		// Supplier

		if (!supplier.matches("^.{2,20}$")) {
			return "Supplier length must be between 2 and 20 characters.";
		}
		
		// Add
		
		try {
			databaseAccess.createCommodity(new Commodity(parsedId, name, supplier));
		} catch (Exception e) {
			return "Could not add commodity (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String addRecipe(String id, String name) {
		
		// Id
		
		int parsedId;
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}

		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must between 1 and 99999999.";
		}
		
		// Name

		if (!name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}

		// Add

		try {
			databaseAccess.createRecipe(new Recipe(parsedId, name));
		} catch (Exception e) {
			return "Could not add recipe (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String updateCommodityBatch(String id, String quantity) {
		
		// Id
		
		CommodityBatch commodityBatch;
		int parsedId;
		
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}
		
		try {
			commodityBatch = databaseAccess.getCommodityBatch(parsedId);
		} catch (Exception e) {
			return "Could not find commodity batch (" + e.getMessage() + ").";
		}
		
		// Quantity
		
		double parsedQuantity;

		try {
			parsedQuantity = Double.parseDouble(quantity);
		} catch (Exception e) {
			return "Quantity must be a number.";
		}
		
		if (parsedQuantity < 1 || parsedQuantity > 99999999) {
			return "Quantity must be between 1 and 99999999.";
		}
				
		// Update
		
		commodityBatch.setMaengde(parsedQuantity);
		
		try {
			databaseAccess.updateCommodityBatch(commodityBatch);
		} catch (Exception e) {
			return "Could not update commodity batch (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addCommodityBatch(String id, String commodityId, String quantity) {
	
		// Id
		
		int parsedId;
				
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}
				
		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must be between 1 and 99999999.";
		}
					
		// Commodity Id
				
		int parsedCommodityId;

		try {
			parsedCommodityId = Integer.parseInt(commodityId);
		} catch (Exception e) {
			return "Commodity Id must be a number.";
		}
				
		if (parsedCommodityId < 1 || parsedCommodityId > 99999999) {
			return "Commodity Id must be between 1 and 99999999.";
		}
				
		// Quantity
				
		double parsedQuantity;

		try {
			parsedQuantity = Double.parseDouble(quantity);
		} catch (Exception e) {
			return "Quantity must be a number.";
		}
				
		if (parsedQuantity < 1 || parsedQuantity > 99999999) {
			return "Quantity must be between 1 and 99999999.";
		}
		
		// Add

		try {
			databaseAccess.createCommodityBatch(new CommodityBatch(parsedId, parsedCommodityId, parsedQuantity));
		} catch (Exception e) {
			return "Could not add commodity batch (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String addProductBatch(String id, String receptId, String timestamp) {
		
		// Id
		
		int parsedId;
			
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}
				
		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must be between 1 and 99999999.";
		}
					
		// Recept Id
				
		int parsedReceptId;

		try {
			parsedReceptId = Integer.parseInt(receptId);
		} catch (Exception e) {
			return "Recept Id must be a number.";
		}
				
		if (parsedReceptId < 1 || parsedReceptId > 99999999) {
			return "Recept Id must be between 1 and 99999999.";
		}
				
		// Timestamp

		if (!timestamp.matches("^.{2,20}$")) {
			return "Timestamp length must be between 2 and 20 characters.";
		}
		
		// Add

		try {
			databaseAccess.createProductBatch(new ProductBatch(parsedId, parsedReceptId, timestamp, 0));
		} catch (Exception e) {
			return "Could not add product batch (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String addRecipeComponent(String recipeId, String commodityId, String quantity, String tolerance) {
		
		// Recipe Id
		
		int parsedRecipeId;
			
		try {
			parsedRecipeId = Integer.parseInt(recipeId);
		} catch (Exception e) {
			return "Recipe Id must be a number.";
		}
				
		if (parsedRecipeId < 1 || parsedRecipeId > 99999999) {
			return "Recipe Id must be between 1 and 99999999.";
		}
					
		// Commodity Id
				
		int parsedCommodityId;

		try {
			parsedCommodityId = Integer.parseInt(commodityId);
		} catch (Exception e) {
			return "Commodity Id must be a number.";
		}
				
		if (parsedCommodityId < 1 || parsedCommodityId > 99999999) {
			return "Commodity Id must be between 1 and 99999999.";
		}
				
		// Quantity
		
		double parsedQuantity;

		try {
			parsedQuantity = Double.parseDouble(quantity);
		} catch (Exception e) {
			return "Quantity must be a number.";
		}
				
		if (parsedQuantity < 1 || parsedQuantity > 99999999) {
			return "Quantity must be between 1 and 99999999.";
		}
					
		// Tolerance
		
		double parsedTolerance;

		try {
			parsedTolerance = Double.parseDouble(tolerance);
		} catch (Exception e) {
			return "Tolerance must be a number.";
		}
		
		if (parsedTolerance < 1 || parsedTolerance > 99999999) {
			return "Tolerance must be between 1 and 99999999.";
		}

		// Add
		
		try {
			databaseAccess.createRecipeComp(new RecipeComp(parsedRecipeId, parsedCommodityId, parsedQuantity, parsedTolerance));
		} catch (Exception e) {
			return "Could not add recipe component (" + e.getMessage() + ").";
		}
				
		return null;
	}
	
}
