package web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import special_objects.Commodity_Sum;
import special_objects.FullBatchList;
import database.DALException;
import database.DatabaseAccess;
import database_objects.CommodityBatch;
import database_objects.Operator;
import database_objects.Commodity;
import database_objects.ProductBatch;
import database_objects.ProductBatchComp;
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
			pages.add(new Page("Product Batch Components", "ProductBatchComponents", 3));
			pages.add(new Page("", "Print", 3));
		}

	}
	
	//# Properties
	
	public List<Page> getPages() {
		
		return pages;
	}
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
	public boolean isLoggedIn() {
		
		return operator != null;
	}
	public String getName() {
		
		return operator.getOprName();
	}
	public int getRights() {
		
		return operator.getRights();
	}
	public List<Operator> getOperators(String operatorId) {
		
		if (operatorId != null) {
			try {
				List<Operator> temp = new ArrayList<Operator>();
				temp.add(databaseAccess.getOperator(Integer.parseInt(operatorId)));
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			return databaseAccess.getOperatorList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Commodity> getCommodities(String commodityId) {
	
		if (commodityId != null) {
			try {
				List<Commodity> temp = new ArrayList<Commodity>();
				temp.add(databaseAccess.getCommodity(Integer.parseInt(commodityId)));
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		try {
			return databaseAccess.getCommodityList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Recipe> getRecipes(String recipeId) {
		
		if (recipeId != null) {
			try {
				List<Recipe> temp = new ArrayList<Recipe>();
				temp.add(databaseAccess.getRecipe(Integer.parseInt(recipeId)));
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			return databaseAccess.getRecipeList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<CommodityBatch> getCommodityBatches(String commodityId) {
		
		if (commodityId != null) {
			try {
				return databaseAccess.getCommodityBatchList(Integer.parseInt(commodityId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			return databaseAccess.getCommodityBatchList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<Commodity_Sum> getLowCommodityBatches(double lowDefinition) {
		
		try {
			return databaseAccess.getLowCommodityList(lowDefinition);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<ProductBatch> getProductBatches(String productBatchId, String recipeId) {
		
		if (productBatchId != null) {
			try {
				List<ProductBatch> temp = new ArrayList<ProductBatch>();
				temp.add(databaseAccess.getProductBatch(Integer.parseInt(productBatchId)));
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (recipeId != null) {
			try {
				return databaseAccess.getProductBatchList(Integer.parseInt(recipeId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			return databaseAccess.getProductBatchList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<RecipeComp> getRecipeComponents(String recipeId, String commodityId) {
		
		if (recipeId != null) {
			try {
				return databaseAccess.getRecipeCompList(Integer.parseInt(recipeId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (commodityId != null) {
			try {
				return databaseAccess.getRecipeCompWithList(Integer.parseInt(commodityId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			return databaseAccess.getRecipeCompList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<ProductBatchComp> getProductBatchComponents(String productBatchId, String commodityBatchId, String operatorId) {
		
		if (productBatchId != null) {
			try {
				return databaseAccess.getProductBatchCompList(Integer.parseInt(productBatchId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (commodityBatchId != null) {
			try {
				return databaseAccess.getProductBatchCompListCbId(Integer.parseInt(commodityBatchId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else if (operatorId != null) {
			try {
				return databaseAccess.getProductBatchCompListOprId(Integer.parseInt(operatorId));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			return databaseAccess.getProductBatchCompList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//# Functions
	
	public String login(String id, String password) {
		
		logout();
		
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "You must enter a valid id.";
		}
		if (password == null || !password.matches("^.{5,8}$")) {
			return "You must enter a valid password.";
		}
		
		// Check
		
		Operator operator;
		
		try {
			operator = databaseAccess.getOperator(Integer.parseInt(id));
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
		
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (name == null || !name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}
		if (initials == null || !initials.matches("^.{2,4}$")) {
			return "Initials length must be between 2 and 4 characters.";
		}
		if (password == null || !password.matches("^.{5,8}$")) {
			return "Password length must be between 5 and 8 characters.";
		}
		if (rights == null || !rights.matches("^[0-9]{1,8}$")) {
			return "Rights must be a number between 1 and 5.";
		}

		Operator operator;
		
		try {
			operator = databaseAccess.getOperator(Integer.parseInt(id));
		} catch (Exception e) {
			return "Could not find operator (" + e.getMessage() + ").";
		}
		
		// Update
		
		operator.setOprName(name);
		operator.setIni(initials);
		operator.setPassword(password);
		operator.setRights(Integer.parseInt(rights));
		
		try {
			databaseAccess.updateOperator(operator);
		} catch (Exception e) {
			return "Could not update operator (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addOperator(String id, String name, String initials, String cpr, String password, String rights) {
		
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (name == null || !name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}
		if (initials == null || !initials.matches("^.{2,4}$")) {
			return "Initials length must be between 2 and 4 characters.";
		}
		if (cpr == null || !cpr.matches("^[0-9]{10}$")) {
			return "CPR must be a 10 digit number.";
		}
		if (password == null || !password.matches("^.{5,8}$")) {
			return "Password length must be between 5 and 8 characters.";
		}
		if (rights == null || !rights.matches("^[0-9]{1,8}$")) {
			return "Rights must be a number between 1 and 5.";
		}
		
		// Add
		
		try {
			databaseAccess.createOperator(new Operator(Integer.parseInt(id), name, initials, cpr, password, Integer.parseInt(rights)));
		} catch (Exception e) {
			return "Could not add operator (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addCommodity(String id, String name, String supplier) {

		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (name == null || !name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}
		if (supplier == null || !supplier.matches("^.{2,20}$")) {
			return "Supplier length must be between 2 and 20 characters.";
		}
		
		// Add
		
		try {
			databaseAccess.createCommodity(new Commodity(Integer.parseInt(id), name, supplier));
		} catch (Exception e) {
			return "Could not add commodity (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String addRecipe(String id, String name) {
		
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (name == null || !name.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}

		// Add

		try {
			databaseAccess.createRecipe(new Recipe(Integer.parseInt(id), name));
		} catch (Exception e) {
			return "Could not add recipe (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String updateCommodityBatch(String id, String quantity) {
		
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (quantity == null || !quantity.matches("^[0-9]+\\.?[0-9]{0,4}$")) {
			return "Quantity must be a decimal number greater than or equal to 0 with a maximum of 4 decimals.";
		}
		
		CommodityBatch commodityBatch;
		
		try {
			commodityBatch = databaseAccess.getCommodityBatch(Integer.parseInt(id));
		} catch (Exception e) {
			return "Could not find commodity batch (" + e.getMessage() + ").";
		}
						
		// Update
		
		commodityBatch.setMaengde(Double.parseDouble(quantity));
		
		try {
			databaseAccess.updateCommodityBatch(commodityBatch);
		} catch (Exception e) {
			return "Could not update commodity batch (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addCommodityBatch(String id, String commodityId, String quantity) {
	
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (commodityId == null || !commodityId.matches("^[0-9]{1,8}$")) {
			return "Commodity id must be a number between 1 and 99999999.";
		}
		if (quantity == null || !quantity.matches("^[0-9]+\\.?[0-9]{0,4}$")) {
			return "Quantity must be a decimal number greater than or equal to 0 with a maximum of 4 decimals.";
		}
		
		// Add

		try {
			databaseAccess.createCommodityBatch(new CommodityBatch(Integer.parseInt(id), Integer.parseInt(commodityId), Double.parseDouble(quantity)));
		} catch (Exception e) {
			return "Could not add commodity batch (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String addProductBatch(String id, String receptId) {
		
		if (id == null || !id.matches("^[0-9]{1,8}$")) {
			return "Id must be a number between 1 and 99999999.";
		}
		if (receptId == null || !receptId.matches("^[0-9]{1,8}$")) {
			return "Recept id must be a number between 1 and 99999999.";
		}
		
		// Add

		try {
			databaseAccess.createProductBatch(new ProductBatch(Integer.parseInt(id), Integer.parseInt(receptId), null, null, 0));
		} catch (Exception e) {
			return "Could not add product batch (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String addRecipeComponent(String recipeId, String commodityId, String quantity, String tolerance) {
		
		if (recipeId == null || !recipeId.matches("^[0-9]{1,8}$")) {
			return "Recipe id must be a number between 1 and 99999999.";
		}
		if (commodityId == null || !commodityId.matches("^[0-9]{1,8}$")) {
			return "Commodity id must be a number between 1 and 99999999.";
		}
		if (quantity == null || !quantity.matches("^[0-9]+\\.?[0-9]{0,4}$") || Double.parseDouble(quantity) < 0.05 || Double.parseDouble(quantity) > 20) {
			return "Quantity must be a decimal number between 0.05 and 20 with a maximum of 4 decimals.";
		}
		if (tolerance == null || !tolerance.matches("^[0-9]+\\.?[0-9]{0,4}$") || Double.parseDouble(tolerance) < 0.1 || Double.parseDouble(tolerance) > 10) {
			return "Tolerance must be a decimal number between 0.1 and 10 with a maximum of 4 decimals.";
		}

		// Add
		
		try {
			databaseAccess.createRecipeComp(new RecipeComp(Integer.parseInt(recipeId), Integer.parseInt(commodityId), Double.parseDouble(quantity), Double.parseDouble(tolerance)));
		} catch (Exception e) {
			return "Could not add recipe component (" + e.getMessage() + ").";
		}
				
		return null;
	}
	public static String getDate() {
	
		return new Date().toString();
	}
	
	public List<FullBatchList> getFullBatchListNotMade(int pbId) {
		
							
				try {
					List<FullBatchList> temp = new ArrayList<FullBatchList>();
					temp = databaseAccess.getFullBatchListNotMade(pbId);
					return temp;
				} catch (DALException e) {
					e.printStackTrace();
					return null;
				}
				
			} 
	
	public List<FullBatchList> getFullBatchListMade(int pbId) {
		
		
		try {
			List<FullBatchList> temp = new ArrayList<FullBatchList>();
			temp = databaseAccess.getFullBatchListMade(pbId);
			return temp;
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String decimalFormat(double tal) {
		
		final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0.####");
        
		return decimalFormat.format(tal);        
	}
				
}
