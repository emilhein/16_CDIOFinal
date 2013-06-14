package web;

import java.util.List;
import database.DALException;
import database.DatabaseAccess;
import database_objects.CommodityBatch;
import database_objects.Operator;
import database_objects.Commodity;
import database_objects.ProductBatch;
import database_objects.Recipe;

public class Session {

	private static DatabaseAccess databaseAccess;
	private Operator operator = null;
	
	//# New
	
	public Session() {

		if (databaseAccess == null) {
			try {
				databaseAccess = new DatabaseAccess();
			} catch (DALException e) {
				e.printStackTrace();
			}
		}

	}
	
	//# Properties
	
	public boolean loggedIn() {
		
		return operator != null;
	}
	public String getName() {
		
		return operator.getOprName();
	}
	public int getRights() {
		
		return operator.getRights();
	}
	
	//# Functions


//	public boolean login(String id, String password) {
//
//		try {
//			operator = databaseAccess.getOperator(Integer.parseInt(id));
//			return operator.getPassword().equals(password);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}

	public String login(String id, String password) {
		
		// User id
		
		if (id == null || id.length() < 1) {
			logout();
			return "You must enter a user id.";

		}

		int parsedId;
		
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			logout();
			return "You must enter a valid user id.";
		}
		
		// Password
		
		if (password == null || password.length() < 1) {
			logout();
			return "You must enter a password.";
		}
		
		// Check
		
		try {
			operator = databaseAccess.getOperator(parsedId);
		} catch (Exception e) {
			logout();
			return "Wrong user id or password.";
		}
		
		if (!operator.getPassword().equals(password)) {
			logout();
			return "Wrong user id or password.";
		}
		
		if (operator.getRights() == 0) {
			logout();
			return "Operator is blocked.";
		}
		
		return null;
	}
	public void logout() {
		
		operator = null;
		
	}
	public static String updateOperator(String id, String name, String initials, String password, String rights) {
		
		// User id
		
		Operator operator;
		int parsedId;
		
		try {
			parsedId = Integer.parseInt(id);
		} catch (Exception e) {
			return "Id must be a number.";
		}
		
		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must between 1 and 99999999.";
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
		
		if (parsedRights < 0 || parsedRights > 4) {
			return "Rights must between 0 and 4.";
		}
		
		// Save
		
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
		
		// User id
		
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
		
		if (parsedRights < 0 || parsedRights > 4) {
			return "Rights must between 0 and 4.";
		}
		
		// Save
		
		try {
			databaseAccess.createOperator(new Operator(parsedId, name, initials, cpr, password, parsedRights));
		} catch (Exception e) {
			return "Could not add operator (" + e.getMessage() + ").";
		}
		
		return null;
	}
	public static String addCommodity(String commodityId, String commodityName, String supplier) {
		// Commodityid
		
		int parsedId;
		try {
			parsedId = Integer.parseInt(commodityId);
		} catch (Exception e) {
			return "Id must be a number.";
		}

		if (parsedId < 1 || parsedId > 99999999) {
			return "Id must between 1 and 99999999.";
		}
		// Name

		if (!commodityName.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}

		// supplier

		if (!supplier.matches("^.{2,20}$")) {
			return "Name length must be between 2 and 20 characters.";
		}
				
	

		try {
			databaseAccess.createCommodity(new Commodity(parsedId, commodityName,
					supplier));
		} catch (Exception e) {
			return "Could not at commodity (" + e.getMessage() + ").";
		}
				
				return null;

	}
	
// Operator_____________________________________________________________________________________________
	public Operator getOperator() {
		return operator;
	}

	public List<Operator> getOperators() {
		try {
			return databaseAccess.getOperatorList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void addOperator(int oprId, String oprName, String ini, String cpr, String password, int rights) { 
		try {
			databaseAccess.createOperator(new Operator(oprId, oprName, ini, cpr, password, rights));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOperator(int oprId, String oprName, String ini, String cpr, String password, int rights)
	{
		try {
			databaseAccess.updateOperator(new Operator(oprId, oprName, ini, cpr, password, rights));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	//Commodity_______________________________________________________________________________________
	
	
	public List<Commodity> getCommodityList(){
		try {
			return databaseAccess.getCommodityList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addCommodity(int commodityId, String commodityName, String supplier) {
		try {
			databaseAccess.createCommodity(new Commodity(commodityId, commodityName, supplier));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}	
	//CommodityBatch________________________________________________________________________________________
	public List<CommodityBatch> getCommodityBatchList(){
		try {
			return databaseAccess.getCommodityBatchList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	
	public void addCommodityBatch(int cbId, int commodityId, double maengde) {
		try {
			databaseAccess.createCommodityBatch(new CommodityBatch(cbId, commodityId, maengde));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	//ProductBathch___________________________________________________________________________________________
	public List<ProductBatch> getProductBatchList(){
		try {
			return databaseAccess.getProductBatchList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public void addProductBatch(int pbId, int  receptId, String timeStamp, int status) {
		try {
			databaseAccess.createProductBatch(new ProductBatch(pbId, receptId, timeStamp, status));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
	//Recipe___________________________________________________________________________________________________
	public List<Recipe> getRecipeList(){
		try {
			return databaseAccess.getRecipeList();
		} catch (DALException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public void addRecipe(int recipeId, String  recipeName) {
		try {
			databaseAccess.createRecipe(new Recipe(recipeId, recipeName));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
}
