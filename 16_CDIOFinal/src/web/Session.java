package web;

import java.util.List;

import database.DALException;
import database.DatabaseAccess;
import database_objects.Operator;

public class Session {

	static DatabaseAccess databaseAccess;
	Operator operator;

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
	
	
}
