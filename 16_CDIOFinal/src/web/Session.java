package web;

import java.util.List;

import database.DALException;
import database.DatabaseAccess;
import database_objects.Operator;
import database_objects.Commodity;

public class Session {

	static DatabaseAccess databaseAccess;
	Operator operator;
	Commodity commodity;

	public Session() {

		if (databaseAccess == null) {
			try {
				databaseAccess = new DatabaseAccess();
			} catch (DALException e) {
				// TODO
			}
		}

	}

	public boolean login(String oprId, String password) {

		try {
			operator = databaseAccess.getOperator(Integer.parseInt(oprId));
			return operator.getPassword().equals(password);
		} catch (Exception e) {
			return false;
		}

	}

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
	
	
}
