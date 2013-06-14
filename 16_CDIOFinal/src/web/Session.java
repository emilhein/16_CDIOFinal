package web;

import java.util.List;

import database.DALException;
import database.DatabaseAccess;
import database_objects.CommodityBatch;
import database_objects.Operator;
import database_objects.Commodity;

public class Session {

	private static DatabaseAccess databaseAccess;
	private Operator operator = null;
	
	Commodity commodity;

	//# New
	
	public Session() {

		if (databaseAccess == null) {
			try {
				databaseAccess = new DatabaseAccess();
			} catch (DALException e) {
				// TODO
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
	public Commodity getCommodity(){
		return commodity;
	}
	
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
	public CommodityBatch getCommodityBatch(){
		return getCommodityBatch();
	}
	
	public void addCommodityBatch(int cbId, int commodityId, double maengde) {
		try {
			databaseAccess.createCommodityBatch(new CommodityBatch(cbId, commodityId, maengde));
		} catch (DALException e) {
			// mathias er awesome
		}
	}
	
}
