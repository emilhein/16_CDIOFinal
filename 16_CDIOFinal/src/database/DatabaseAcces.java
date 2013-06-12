package database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import database_objects.*;

class DatabaseAcces {
	public DatabaseAcces()
	{
		
	}
	
	
	
	//Commodity______________________________________________________________
	public Commodity getCommodity(int commodityId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM commodity WHERE commodityId = " + commodityId);
	    try {
	    	if (!rs.first()) throw new DALException("The commodity " + commodityId + " doesn't exist."); 
	    	return new Commodity (rs.getInt(1), rs.getString(2), rs.getString(3));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//CommodityBatch__________________________________________________________________________________
	public CommodityBatch getCommodityBatch(int cbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM commodityBatch WHERE cbId = " + cbId);
	    try {
	    	if (!rs.first()) throw new DALException("The commodityBatch " + cbId + " doesn't exist");
	    	return new CommodityBatch(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//Operator_______________________________________________________________________________________
	public Operator getOperator(int oprId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM Operator WHERE oprId = " + oprId);
	    try {
	    	if (!rs.first()) throw new DALException("The operator " + oprId + " doesn't exist");
	    	return new Operator(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	public void updateOperator(Operator opr) throws DALException{
		Connector.doUpdate("UPDATE Operator SET opr_name = '" + opr.getOprName()
				+ "', ini = '" + opr.getIni() + "', cpr = '" + opr.getCpr() +"', oprPassword ='" + opr.getPassword() + "', rights = '" + opr.getRights() + "' where oprId = " + opr.getOprId());
	}
	
	public List<Operator> getOperatorList() throws DALException {
		List<Operator> list = new ArrayList<Operator>();
		ResultSet rs = Connector.doQuery("SELECT * FROM Operator");
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
		ResultSet rs = Connector.doQuery("SELECT * FROM productBatch WHERE pbId = " + pbId);
	    try {
	    	if (!rs.first()) throw new DALException("The productbatch " + pbId + " doesn't exist");
	    	return new ProductBatch(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//ProductBatchComp___________________________________________________________________________
	public ProductBatchComp getProductBatchComp(int pbId, int cbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM ProductBatchComponent WHERE pbId = " + pbId + " AND cbId = " + cbId);
	    try {
	    	if (!rs.first()) throw new DALException("The productbatchcomponent " + pbId + " , " + cbId + " doesn't exist");
	    	return new ProductBatchComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5));
	    }
	    catch (SQLException e) {throw new DALException(e);}
	}
	
	//Recipe______________________________________________________________________________
		public Recipe getRecipe(int recipeId) throws DALException {
			ResultSet rs = Connector.doQuery("SELECT * FROM recipe WHERE recipeId = " + recipeId);
		    try {
		    	if (!rs.first()) throw new DALException("The recipe " + recipeId + " doesn't exist");
		    	return new Recipe(rs.getInt(1), rs.getString(2));
		    }
		    catch (SQLException e) {throw new DALException(e);}
		}
	
		//RecipeComp___________________________________________________________________________
		public RecipeComp getRecipeComp(int recipeId, int commodityId) throws DALException {
			ResultSet rs = Connector.doQuery("SELECT * FROM recipeComponent WHERE recipeId = " + recipeId + " AND commodityId = " + commodityId);
		    try {
		    	if (!rs.first()) throw new DALException("The RecipeComponent " + recipeId + " , " + commodityId + " doesn't exist");
		    	return new RecipeComp(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4));
		    }
		    catch (SQLException e) {throw new DALException(e);}
		}
}
