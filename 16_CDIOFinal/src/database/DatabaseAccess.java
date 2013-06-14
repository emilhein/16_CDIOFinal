package database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import database_objects.*;

public class DatabaseAccess {

	private static final String[] tables = new String[] {"productBatchComponent", "productBatch", "recipeComponent","recipe", "commodityBatch", "commodity","operator"};
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

		} catch (Exception e) {

			// Close
			try {
				connector.Close();
			} catch (Exception ex) {
			}
			
			throw new DALException(e);
		}

	}

	//# Close

	public void close() {

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
			connector.doUpdate("CREATE TABLE operator(oprId INTEGER NOT NULL, oprName VARCHAR(20), ini VARCHAR(4), cpr VARCHAR(10) NOT NULL, oprPassword VARCHAR(10) NOT NULL, rights INTEGER NOT NULL, PRIMARY KEY(oprId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE commodity(commodityId INTEGER NOT NULL, commodityName VARCHAR(20), supplier VARCHAR(20), PRIMARY KEY(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE commodityBatch(cbId INTEGER NOT NULL, commodityId INTEGER, quantity REAL NOT NULL, PRIMARY KEY(cbId), FOREIGN KEY (commodityId) REFERENCES commodity(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE recipe(recipeId INTEGER NOT NULL, recipeName VARCHAR(20), PRIMARY KEY(recipeId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE recipeComponent(recipeId INTEGER, commodityId INTEGER, nomNetto REAL NOT NULL, tolerance REAL NOT NULL, PRIMARY KEY(recipeId, commodityId), FOREIGN KEY(recipeId) REFERENCES recipe(recipeId), FOREIGN KEY(commodityId) REFERENCES commodity(commodityId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE productBatch(pbId INTEGER NOT NULL, recipeId INTEGER, ts VARCHAR(30), state INTEGER NOT NULL, PRIMARY KEY(pbId), FOREIGN KEY(recipeId) REFERENCES recipe(recipeId)) ENGINE=innoDB;");
			connector.doUpdate("CREATE TABLE productBatchComponent(pbId INTEGER, cbId INTEGER, tara REAL NOT NULL, netto REAL NOT NULL, oprId INTEGER, PRIMARY KEY(pbId, cbId), FOREIGN KEY(pbId) REFERENCES productBatch(pbId), FOREIGN KEY(cbId) REFERENCES commodityBatch(cbId), FOREIGN KEY(oprId) REFERENCES operator(oprId)) ENGINE=innoDB;");

			
			
			//operator --- oprId: int, oprName: varchar, initialer: varchar, cpr: varchar, password: varchar, rights: int.
			//commodity --- commodityID: int, commodityName: Varchar, supplier: varchar. 
			//commoditybatch --- cbId: int, commodityId: int, quantity: real.
			//recipe --- recipeId: int, recipeName: varchar, 
			//recipeComponent --- recipeId: int, commodityId int, nomNetto: Real, Tolerance: real.
			//productbatch --- pbId: int, recipeId: int, ts: VARCHAR(30), state: int.
			//productsbatchComponent --- pbId: int, cbId: int, tara: real, netto: real, oprId: int.
			
			
			// indsæt operatoere.
			connector.doUpdate("INSERT INTO operator VALUES(1,'Mathias','MEL','2404922559','123',1)");
			connector.doUpdate("INSERT INTO operator VALUES(2,'Emil','EHE','2404922559','123',2)");
			connector.doUpdate("INSERT INTO operator VALUES(3,'Jens','JWN','2404922559','123',3)");
			connector.doUpdate("INSERT INTO operator VALUES(4,'Khan','KN','2404922559','123',4)");
			connector.doUpdate("INSERT INTO operator VALUES(5,'Kim','KIM','2404922559','123',1)");
			
			// indsæt commodity.
			connector.doUpdate("INSERT INTO commodity VALUES(1,'Citron','Spain')");
			connector.doUpdate("INSERT INTO commodity VALUES(2,'salt','Samsoe')");
			connector.doUpdate("INSERT INTO commodity VALUES(3,'vand','Norge')");
			
			// indsæt commodityBatch.
			connector.doUpdate("INSERT INTO commodityBatch VALUES(1,1,2.3)");
			connector.doUpdate("INSERT INTO commodityBatch VALUES(2,3,25.3)");
			connector.doUpdate("INSERT INTO commodityBatch VALUES(3,2,1.0)");
			
			// indsæt recipe.
			connector.doUpdate("INSERT INTO recipe VALUES(1,'Citronvand')");
			connector.doUpdate("INSERT INTO recipe VALUES(2,'Citronsalt')");
			connector.doUpdate("INSERT INTO recipe VALUES(3,'CitronMedSalt')");
			
			// indsæt recipeComponent.
			connector.doUpdate("INSERT INTO recipeComponent VALUES(1,1, 1.2, 0.7)");
			connector.doUpdate("INSERT INTO recipeComponent VALUES(1,2, 12.2, 0.4)");
			
			// indsæt productBatch.
			connector.doUpdate("INSERT INTO productBatch VALUES(1,1,'" + new Date().toString() + "',1)");
			
			// indsæt productBatchComponent.
			//connector.doUpdate("INSERT INTO productBatchComponent VALUES(1,1, 12.2,12.0,1)");
			
			
		} finally {

			// Close
			try {
				connector.Close();
			} catch (Exception e) {
			}

		}

	}

	//Commodity______________________________________________________________
	public void createCommodity(Commodity com)throws DALException {
		connector.doUpdate(
				"INSERT INTO commodity(commodityId, commodityName, supplier) VALUES " +
						"(" + com.getCommodityId() + ", '" + com.getCommodityName() + "', '" + com.getSupplier()+ "')");

	}

	public Commodity getCommodity(int commodityId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM commodity WHERE commodityId = " + commodityId);
		try {
			if (!rs.first()) throw new DALException("The commodity " + commodityId + " doesn't exist."); 
			return new Commodity (rs.getInt(1), rs.getString(2), rs.getString(3));
		}
		catch (SQLException e) {throw new DALException(e);}
	}

	public void updateCommodity(Commodity com) throws DALException{
		connector.doUpdate("UPDATE commodity SET commodityName = '" + com.getCommodityName()
				+ "', supplier = '" + com.getSupplier()  + "' where commodityId = " + com.getCommodityId());
	}

	public List<Commodity> getCommodityList() throws DALException {
		List<Commodity> list = new ArrayList<Commodity>();
		ResultSet rs = connector.doQuery("SELECT * FROM commodity");
		try {
			while (rs.next()) {
				list.add(new Commodity(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	//CommodityBatch__________________________________________________________________________________
	public void createCommodityBatch(CommodityBatch cb)throws DALException {
		connector.doUpdate(
				"INSERT INTO commodityBatch(cbId,commodityId, quantity real) VALUES " +
						"(" + cb.getCbId() + ", " + cb.getCommodityId() + ", " + cb.getMaengde() + ")");

	}

	public CommodityBatch getCommodityBatch(int cbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM commodityBatch WHERE cbId = " + cbId);
		try {
			if (!rs.first()) throw new DALException("The commodityBatch " + cbId + " doesn't exist");
			return new CommodityBatch(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
		}
		catch (SQLException e) {throw new DALException(e);}
	}

	public void updateCommodityBatch(CommodityBatch cb) throws DALException{
		connector.doUpdate("UPDATE commodityBatch SET quantity = '" + cb.getMaengde() + "' where cbId = " + cb.getCbId());
	}

	public List<CommodityBatch> getCommodityBatchList() throws DALException {
		List<CommodityBatch> list = new ArrayList<CommodityBatch>();
		ResultSet rs = connector.doQuery("SELECT * FROM commodityBatch");
		try {
			while (rs.next()) {
				list.add(new CommodityBatch(rs.getInt(1), rs.getInt(2), rs.getDouble(3)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	public List<CommodityBatch> getCommodityBatchList(int ComId) throws DALException {
		List<CommodityBatch> list = new ArrayList<CommodityBatch>();
		ResultSet rs = connector.doQuery("SELECT * FROM commodityBatch where commodityId = " + ComId);
		try {
			while (rs.next()) {
				list.add(new CommodityBatch(rs.getInt(1), rs.getInt(2), rs.getDouble(3)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	//Operator_______________________________________________________________________________________
	public void createOperator(Operator opr)throws DALException {
		connector.doUpdate(
				"INSERT INTO operator(oprId, oprName, ini, cpr, oprPassword, rights) VALUES " +
						"(" + opr.getOprId() + ", '" + opr.getOprName() + "','" + opr.getIni() + "','" + opr.getCpr() + "','" + opr.getPassword() + "'," + opr.getRights() + ")");

	}

	public Operator getOperator(int oprId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM operator WHERE oprId = " + oprId);
		try {
			if (!rs.first()) throw new DALException("The operator " + oprId + " doesn't exist");
			return new Operator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
		}
		catch (SQLException e) {throw new DALException(e);}
	}

	public void updateOperator(Operator opr) throws DALException{
		connector.doUpdate("UPDATE operator SET oprName = '" + opr.getOprName()
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
	public void createProductBatch(ProductBatch pb)throws DALException {
		connector.doUpdate(
				"INSERT INTO productBatch(pbId, recipeId ,ts , state) VALUES " +
						"(" + pb.getPbId() + ", " + pb.getReceptId() + ",'" + pb.getTimeStamp() + "'," + pb.getStatus() + ")");

	}

	public ProductBatch getProductBatch(int pbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM productBatch WHERE pbId = " + pbId);
		try {
			if (!rs.first()) throw new DALException("The productbatch " + pbId + " doesn't exist");
			return new ProductBatch(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
		}
		catch (SQLException e) {throw new DALException(e);}
	}

	public void updateProductBatch(ProductBatch pb) throws DALException{
		connector.doUpdate("UPDATE productBatch SET state = " + pb.getStatus() + " where pbId = " + pb.getPbId());
	}
	
	public List<ProductBatch> getProductBatchList() throws DALException {
		List<ProductBatch> list = new ArrayList<ProductBatch>();
		ResultSet rs = connector.doQuery("SELECT * FROM productBatch");
		try {
			while (rs.next()) {
				list.add(new ProductBatch(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}
	
	//ProductBatchComp___________________________________________________________________________
	public void createProductBatchComp(ProductBatchComp pbc)throws DALException {
		connector.doUpdate(
				"INSERT INTO productBatchComponent(pbId, cbId, tara, netto, oprId) VALUES " +
						"(" + pbc.getPbId() + ", " + pbc.getCbId() + "," + pbc.getTara() + "," + pbc.getNetto() + "," + pbc.getOprId() + ")");

	}

	public ProductBatchComp getProductBatchComp(int pbId, int cbId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM productBatchComponent WHERE pbId = " + pbId + " AND cbId = " + cbId);
		try {
			if (!rs.first()) throw new DALException("The productBatchComponent " + pbId + " , " + cbId + " doesn't exist");
			return new ProductBatchComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5));
		}
		catch (SQLException e) {throw new DALException(e);}
	}

	public List<ProductBatchComp> getProductBatchCompList() throws DALException {
		List<ProductBatchComp> list = new ArrayList<ProductBatchComp>();
		ResultSet rs = connector.doQuery("SELECT * FROM productBatchComponent");
		try {
			while (rs.next()) {
				list.add(new ProductBatchComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	public List<ProductBatchComp> getProductBatchCompList(int pbId) throws DALException {
		List<ProductBatchComp> list = new ArrayList<ProductBatchComp>();
		ResultSet rs = connector.doQuery("SELECT * FROM productBatchComponent where pbId = " + pbId);
		try {
			while (rs.next()) {
				list.add(new ProductBatchComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}
	
	//Recipe______________________________________________________________________________
	public void createRecipe(Recipe recipe)throws DALException {
		connector.doUpdate(
				"INSERT INTO recipe(recipeId, recipeName) VALUES " +
						"(" + recipe.getRecipeId() + ", '" + recipe.getRecipeName() + "')");

	}	

	public Recipe getRecipe(int recipeId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM recipe WHERE recipeId = " + recipeId);
		try {
			if (!rs.first()) throw new DALException("The recipe " + recipeId + " doesn't exist");
			return new Recipe(rs.getInt(1), rs.getString(2));
		}
		catch (SQLException e) {throw new DALException(e);}
	}

	public List<Recipe> getRecipeList() throws DALException {
		List<Recipe> list = new ArrayList<Recipe>();
		ResultSet rs = connector.doQuery("SELECT * FROM recipe");
		try {
			while (rs.next()) {
				list.add(new Recipe(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	//RecipeComp___________________________________________________________________________
	public void createRecipeComp(RecipeComp RecComp)throws DALException {
		connector.doUpdate(
				"INSERT INTO recipeComponent(recipeId, commodityId, nomNetto, tolerance) VALUES " +
						"(" + RecComp.getRecipeId() + ", " + RecComp.getCommodityId() + "," + RecComp.getNomNetto() + "," + RecComp.getTolerance() + ")");

	}

	public RecipeComp getRecipeComp(int recipeId, int commodityId) throws DALException {
		ResultSet rs = connector.doQuery("SELECT * FROM recipeComponent WHERE recipeId = " + recipeId + " AND commodityId = " + commodityId);
		try {
			if (!rs.first()) throw new DALException("The RecipeComponent " + recipeId + " , " + commodityId + " doesn't exist");
			return new RecipeComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4));
		}
		catch (SQLException e) {throw new DALException(e);}
	}
	
	public List<RecipeComp> getRecipeCompList() throws DALException {
		List<RecipeComp> list = new ArrayList<RecipeComp>();
		ResultSet rs = connector.doQuery("SELECT * FROM recipeComponent");
		try {
			while (rs.next()) {
				list.add(new RecipeComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	public List<RecipeComp> getRecipeCompList(int recipeId) throws DALException {
		List<RecipeComp> list = new ArrayList<RecipeComp>();
		ResultSet rs = connector.doQuery("SELECT * FROM recipeComponent where recipeId = " + recipeId);
		try {
			while (rs.next()) {
				list.add(new RecipeComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}
	
	public List<RecipeComp> getRestRecipeComp(int pbId) throws DALException {
		List<RecipeComp> list = new ArrayList<RecipeComp>();
		ResultSet rs = connector.doQuery("	Select recipeId, commodityId, nomNetto, tolerance from recipeComponent natural join productBatch WHERE pbId = "+ pbId +" AND commodityId <> ALL ( Select commodityId from commodityBatch NATURAL JOIN productBatchComponent WHERE pbId = " + pbId + " )");
		try {
			while (rs.next()) {
				list.add(new RecipeComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}
}
