package test;
import database.DatabaseAccess;
import database_objects.*;
import database.DALException;

public class DatabaseAccess_test {

	public static void main(String[] args) {
		try{
			DatabaseAccess db = new DatabaseAccess();
			
			//Operators__________________________________________________________________________
			Operator opr1 = new Operator(666, "kim", "krr", "3333333333", "The_Kim", 1);
			db.createOperator(opr1);
			Operator retur1 = db.getOperator(opr1.getOprId());
			System.out.println(opr1.equals(retur1));
			
			Operator opr2 = new Operator(666, "kimi", "k", "6666666666", "..!.", 3);
			db.updateOperator(opr2);
			Operator retur2 = db.getOperator(opr2.getOprId());
			System.out.println(opr2.equals(retur2));
		}
		catch(DALException e)
		{
			e.printStackTrace();
		}

	}

}
