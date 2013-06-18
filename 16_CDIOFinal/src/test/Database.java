package test;

import static org.junit.Assert.*;
import org.junit.Test;
import database.DALException;
import database.DatabaseAccess;
import database_objects.Commodity;
import database_objects.CommodityBatch;
import database_objects.Operator;
import database_objects.ProductBatch;
import database_objects.ProductBatchComp;
import database_objects.Recipe;
import database_objects.RecipeComp;

public class Database {

	@Test
	public void createDatabaseAccess() {

		try {
			new DatabaseAccess();
		} catch (DALException e) {
			fail();
		}
	}
	
	
	//Commodity_________________________________________________________
	@Test
	public void createCommodity(){
		Commodity com1 = new Commodity(666, "Pandekage", "kitchen");
		try{
			DatabaseAccess db = new DatabaseAccess();
			db.createCommodity(com1);
			Commodity ComRet1 = db.getCommodity(com1.getCommodityId());
			if(!com1.equals(ComRet1))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	@Test
	public void updateCommodity(){
		Commodity com2 = new Commodity(666, "Diabolske Pandekage", "Hells Kitchen");
		try{
			DatabaseAccess db = new DatabaseAccess();
			db.updateCommodity(com2);
			Commodity ComRet2 = db.getCommodity(com2.getCommodityId());
			if(!com2.equals(ComRet2))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	//commoditybatch________________________________________________________________________________
	@Test
	public void createCommodityBatch(){
		try{
			CommodityBatch cb1 = new CommodityBatch(666, 666, 1);
			DatabaseAccess db = new DatabaseAccess();
			db.createCommodityBatch(cb1);
			CommodityBatch cbRet1 = db.getCommodityBatch(cb1.getCbId());
			if(!cb1.equals(cbRet1))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}

	@Test
	public void updateCommodityBatch(){
		try{
			CommodityBatch cb2 = new CommodityBatch(666, 666, 10);
			DatabaseAccess db = new DatabaseAccess();
			db.updateCommodityBatch(cb2);
			CommodityBatch cbRet2 = db.getCommodityBatch(cb2.getCbId());
			if(!cb2.equals(cbRet2))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	//Operators___________________________________________________________________________
	@Test
	public void createOperator(){
		try{
			Operator opr1 = new Operator(666, "kim", "krr", "3333333333", "The_Kim", 1);
			DatabaseAccess db = new DatabaseAccess();
			db.createOperator(opr1);
			Operator OpeRetur1 = db.getOperator(opr1.getOprId());
			if(!opr1.equals(OpeRetur1))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	@Test
	public void updateOperator(){
		try{
			Operator opr2 = new Operator(666, "kimi", "k", "6666666666", "..!.", 3);
			DatabaseAccess db = new DatabaseAccess();
			db.updateOperator(opr2);
			Operator OpeRetur2 = db.getOperator(opr2.getOprId());
			if(!opr2.equals(OpeRetur2))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	//Recipe_______________________________________________________________________________
	@Test
	public void createRecipe(){
		try{
			Recipe rec = new Recipe(666, "Diabolic Vodka");
			DatabaseAccess db = new DatabaseAccess();
			db.createRecipe(rec);
			Recipe recRet = db.getRecipe(rec.getRecipeId());
			if(!rec.equals(recRet))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	//RecipeComponent_____________________________________________________________________
	@Test
	public void createRecipeComp(){
		try{
			RecipeComp rc = new RecipeComp(666, 666, 10, 10);
			DatabaseAccess db = new DatabaseAccess();
			db.createRecipeComp(rc);
			RecipeComp rcRet = db.getRecipeComp(rc.getRecipeId(), rc.getCommodityId());
			if(!rc.equals(rcRet))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	//ProductBatch_________________________________________________________________________
	@Test
	public void createProductBatch(){
		try{
			ProductBatch pb1 = new ProductBatch(666, 666, "Kim_Time", null, 1);
			DatabaseAccess db = new DatabaseAccess();
			db.createProductBatch(pb1);
			ProductBatch pbRet1 = db.getProductBatch(pb1.getPbId());
			if(!(pb1.getPbId() == pbRet1.getPbId()))
				fail();
			if(!(pb1.getReceptId() == pbRet1.getReceptId()))
				fail();
			if(!(pb1.getStatus() == pbRet1.getStatus()))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	@Test
	public void updateProductBatch(){
		try{
			ProductBatch pb2 = new ProductBatch(666, 666, "Kim_Time", "Tea_Time", 3);
			DatabaseAccess db = new DatabaseAccess();
			db.updateProductBatch(pb2);
			ProductBatch pbRet2 = db.getProductBatch(pb2.getPbId());
			if(!(pb2.getPbId() == pbRet2.getPbId()))
				fail();
			if(!(pb2.getReceptId() == pbRet2.getReceptId()))
				fail();
			if(!(pb2.getStatus() == pbRet2.getStatus()))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
	
	//ProductBatchComponent________________________________________________________________
	@Test
	public void createProductBatchComp(){
		try{
			ProductBatchComp pbc1 = new ProductBatchComp(666, 666, 666.1, 666.2, 666, 1);
			DatabaseAccess db = new DatabaseAccess();
			db.createProductBatchComp(pbc1);
			ProductBatchComp pbcRet1 = db.getProductBatchComp(pbc1.getPbId(), pbc1.getCbId());
			if(!pbc1.equals(pbcRet1))
				fail();
		}
		catch(DALException f)
		{
			fail();
		}
	}
}
