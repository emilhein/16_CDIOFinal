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
			}
		}

	}
	
	//# Properties
	
	public boolean loggedIn() {
		
		return operator != null;
	}
	
	//# Functions

	public boolean login(String id, String password) {

		try {
			operator = databaseAccess.getOperator(Integer.parseInt(id));
			return operator.getPassword().equals(password);
		} catch (Exception e) {
			return false;
		}

	}
	public void logout() {
		
		operator = null;
		
	}
	
	
// Operator_____________________________________________________________________________________________
	public Operator getOperator() {
		return operator;
	}

	public List<Operator> getOperators() {
		try {
			return databaseAccess.getOperatorList();
		} catch (DALException e) {
			return null;
		}
	}

	public void addOperator(int oprId, String oprName, String ini, String cpr, String password, int rights) { 
		try {
			databaseAccess.createOperator(new Operator(oprId, oprName, ini, cpr, password, rights));
		} catch (DALException e) {
		 //TODO
		}
	}
	
	public void updateOperator(int oprId, String oprName, String ini, String cpr, String password, int rights)
	{
		try {
			databaseAccess.updateOperator(new Operator(oprId, oprName, ini, cpr, password, rights));
		} catch (DALException e) {
		 //TODO
		}
	}
	//Commodity_______________________________________________________________________________________
	
	
	public List<Commodity> getCommodityList(){
		try {
			return databaseAccess.getCommodityList();
		} catch (DALException e) {
			return null;
		}
	}
	
	public void addCommodity(int commodityId, String commodityName, String supplier) {
		try {
			databaseAccess.createCommodity(new Commodity(commodityId, commodityName, supplier));
		} catch (DALException e) {
			// mathias er awesome
		}
	}	
	//CommodityBatch________________________________________________________________________________________
	public List<CommodityBatch> getCommodityBatchList(){
		try {
			return databaseAccess.getCommodityBatchList();
		} catch (DALException e) {
			return null;
		}
	}
	
	
	public void addCommodityBatch(int cbId, int commodityId, double maengde) {
		try {
			databaseAccess.createCommodityBatch(new CommodityBatch(cbId, commodityId, maengde));
		} catch (DALException e) {
			// mathias er awesome
		}
	}
	//ProductBathch___________________________________________________________________________________________
	public List<ProductBatch> getProductBatchList(){
		try {
			return databaseAccess.getProductBatchList();
		} catch (DALException e) {
			return null;
		}
	}

	
	public void addProductBatch(int pbId, int  receptId, String timeStamp, int status) {
		try {
			databaseAccess.createProductBatch(new ProductBatch(pbId, receptId, timeStamp, status));
		} catch (DALException e) {
			// mathias er awesome
		}
	}
	
	//Recipe___________________________________________________________________________________________________
	public List<Recipe> getRecipeList(){
		try {
			return databaseAccess.getRecipeList();
		} catch (DALException e) {
			return null;
		}
	}

	
	public void addRecipe(int recipeId, String  recipeName) {
		try {
			databaseAccess.createRecipe(new Recipe(recipeId, recipeName));
		} catch (DALException e) {
			// mathias er awesome
		}
	}
	
}
