package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import database_objects.*;

public class DatabaseAccess {
	
	private static final String[] tables = new String[] {"operator", "commodity", "commodityBatch", "recipe", "recipeComponent", "productBatch", "productBatchComponent"};
	private Connector connector;
	
	//# New
	
	public DatabaseAccess() throws DALException {
		
		try {
			
			// Connect
			connector = new Connector("sql-lab1.cc.dtu.dk", 3306, "s123115", "s123115", "F5iCtVPs4rtHu4oM");
			
			// Check
			ResultSet resultSet = connector.doQuery("SHOW TABLES;");
			ArrayList<String> list = new ArrayList<String>();
			try {
				while (resultSet.next()) {
					list.add(resultSet.getString(1));
				}
			} catch (SQLException e) {
				throw new DALException(e);
			}
			for (String table : tables) {
				if (!list.contains(table)) {
					throw new DALException("Table '" + table + "' is missing.");
				}
			}
			
		} finally {
			
			// Close
			try {
				connector.Close();
			} catch (Exception e) {
			}
		}
		
	}
	
	//# Close
	
	public void Close() {
		
		try {
			connector.Close();
		} catch (Exception e) {
		}
		
	}
	
	//# Functions
	
	public static void reset() throws DALException {

		Connector connector = null;
		
		try {
			
			// Connect
			connector = new Connector("sql-lab1.cc.dtu.dk", 3306, "s123115", "s123115", "F5iCtVPs4rtHu4oM");
			
			// Drop
			for (String table : tables) {
				try {
					connector.doUpdate("DROP TABLE " + table + ";");
				} catch (DALException e) {
				}
			}
			
			// Create
			connector.doUpdate("CREATE TABLE operator(oprId INTEGER NOT NULL AUTO_INCREMENT, oprName VARCHAR(20), ini VARCHAR(4), cpr VARCHAR(10) NOT NULL, Upassword VARCHAR(10) NOT NULL, rights INTEGER NOT NULL, PRIMARY KEY(oprId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE commodity(commodityId INTEGER NOT NULL AUTO_INCREMENT, commodityName VARCHAR(20), supplier VARCHAR(20), PRIMARY KEY(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE commodityBatch(cbId INTEGER NOT NULL AUTO_INCREMENT, commodityId INTEGER, maengde REAL NOT NULL, PRIMARY KEY(cbId), FOREIGN KEY (commodityId) REFERENCES commodity(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE recipe(recipeId INTEGER NOT NULL AUTO_INCREMENT, recipeName VARCHAR(20), PRIMARY KEY(recipeId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE recipeComponent(recipeId INTEGER, commodityId INTEGER, nomNetto REAL NOT NULL, tolerance REAL NOT NULL, PRIMARY KEY(recipeId, commodityId), FOREIGN KEY(recipeId) REFERENCES recipe(recipeId), FOREIGN KEY(commodityId) REFERENCES commodity(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE productBatch(pbId INTEGER NOT NULL AUTO_INCREMENT, recipeId INTEGER, ts DATETIME, state INTEGER NOT NULL, PRIMARY KEY(pbId), FOREIGN KEY(recipeId) REFERENCES recipe(recipeId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE productBatchComponent(pbId INTEGER, cbId INTEGER, tara REAL NOT NULL, netto REAL NOT NULL, oprId INTEGER, PRIMARY KEY(pbId, cbId), FOREIGN KEY(pbId) REFERENCES productBatch(pbId), FOREIGN KEY(cbId) REFERENCES commodityBatch(cbId), FOREIGN KEY(oprId) REFERENCES operator(oprId)) ENGINE=innoDB;");
			
		} finally {
			
			// Close
			try {
				connector.Close();
			} catch (Exception e) {
			}
			
		}
		
	}
	
	//Commodity______________________________________________________________
	public Commodity getCommodity(int commodityId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM commodity WHERE commodityId = " + commodityId);
	    try {
	    	if (!rs.first()) throw new DALException("The commodity " + commodityId + " doesn't exist."); 
	    	return new Commodity (rs.getInt(1), rs.getString(2), rs.getString(3));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//CommodityBatch__________________________________________________________________________________
	public CommodityBatch getCommodityBatch(int cbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM commodityBatch WHERE cbId = " + cbId);
	    try {
	    	if (!rs.first()) throw new DALException("The commodityBatch " + cbId + " doesn't exist");
	    	return new CommodityBatch(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//Operator_______________________________________________________________________________________
	public Operator getOperator(int oprId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM operator WHERE oprId = " + oprId);
	    try {
	    	if (!rs.first()) throw new DALException("The operator " + oprId + " doesn't exist");
	    	return new Operator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	public void updateOperator(Operator opr) throws DALException{
		connector.doUpdate("UPDATE operator SET opr_name = '" + opr.getOprName()
				+ "', ini = '" + opr.getIni() + "', cpr = '" + opr.getCpr() +"', oprPassword ='" + opr.getPassword() + "', rights = '" + opr.getRights() + "' where oprId = " + opr.getOprId());
	}
	
	public List<Operator> getOperatorList() throws DALException {
		List<Operator> list = new ArrayList<Operator>();
		ResultSet rs = connector.doQuery("SELECT * FROM operator");
		try {
			while (rs.next()) {
				list.add(new Operator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}
	
	//ProductBatch______________________________________________________________________________
	public ProductBatch getProductBatch(int pbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM productBatch WHERE pbId = " + pbId);
	    try {
	    	if (!rs.first()) throw new DALException("The productbatch " + pbId + " doesn't exist");
	    	return new ProductBatch(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//ProductBatchComp___________________________________________________________________________
	public ProductBatchComp getProductBatchComp(int pbId, int cbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM ProductBatchComponent WHERE pbId = " + pbId + " AND cbId = " + cbId);
	    try {
	    	if (!rs.first()) throw new DALException("The productbatchcomponent " + pbId + " , " + cbId + " doesn't exist");
	    	return new ProductBatchComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//Recipe______________________________________________________________________________
		public Recipe getRecipe(int recipeId) throws DALException {
			ResultSet rs = connector.doQuery("SELECT * FROM recipe WHERE recipeId = " + recipeId);
		    try {
		    	if (!rs.first()) throw new DALException("The recipe " + recipeId + " doesn't exist");
		    	return new Recipe(rs.getInt(1), rs.getString(2));
		    }
		    catch (SQLException e) {throw new DALException(e);}
		}
	
		//RecipeComp___________________________________________________________________________
		public RecipeComp getRecipeComp(int recipeId, int commodityId) throws DALException {
			ResultSet rs = connector.doQuery("SELECT * FROM recipeComponent WHERE recipeId = " + recipeId + " AND commodityId = " + commodityId);
		    try {
		    	if (!rs.first()) throw new DALException("The RecipeComponent " + recipeId + " , " + commodityId + " doesn't exist");
		    	return new RecipeComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4));
		    }
		    catch (SQLException e) {throw new DALException(e);}
		}
}
