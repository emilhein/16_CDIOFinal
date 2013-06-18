package test;
import database.DatabaseAccess;
import database_objects.*;
import database.DALException;

public class DatabaseAccess_test {

	public static void main(String[] args) {
		try{
			DatabaseAccess db = new DatabaseAccess();
			
			//Commodity_________________________________________________________________________
			System.out.println("Commodity Start");
			Commodity com1 = new Commodity(666, "Pandekage", "kitchen");
			try{
				db.createCommodity(com1);
			}
			catch(DALException f)
			{
				db.updateCommodity(com1);
			}
			Commodity ComRet1 = db.getCommodity(com1.getCommodityId());
			System.out.println(com1.equals(ComRet1));
			Commodity com2 = new Commodity(666, "Diabolske Pandekage", "Hells Kitchen");
			db.updateCommodity(com2);
			Commodity ComRet2 = db.getCommodity(com2.getCommodityId());
			System.out.println(com2.equals(ComRet2));
			System.out.println("Commodity end");
			System.out.println();
			
			//CommodityBatch____________________________________________________________________
			System.out.println("CommodityBatch Start");
			CommodityBatch cb1 = new CommodityBatch(666, 666, 1);
			try{
				db.createCommodityBatch(cb1);
			}
			catch(DALException f)
			{
				db.updateCommodityBatch(cb1);
			}
			CommodityBatch cbRet1 = db.getCommodityBatch(cb1.getCbId());
			System.out.println(cb1.equals(cbRet1));
			CommodityBatch cb2 = new CommodityBatch(666, 666, 10);
			db.updateCommodityBatch(cb2);
			CommodityBatch cbRet2 = db.getCommodityBatch(cb2.getCbId());
			System.out.println(cb2.equals(cbRet2));
			
			System.out.println("CommodityBatch End");
			System.out.println();
			
			//Operators___________________________________________________________________________
			System.out.println("Operator Start");
			Operator opr1 = new Operator(666, "kim", "krr", "3333333333", "The_Kim", 1);
			try{
				db.createOperator(opr1);
			}
			catch(DALException f)
			{
				db.updateOperator(opr1);
			}
			Operator OpeRetur1 = db.getOperator(opr1.getOprId());
			System.out.println(opr1.equals(OpeRetur1));
			
			Operator opr2 = new Operator(666, "kimi", "k", "6666666666", "..!.", 3);
			db.updateOperator(opr2);
			Operator OpeRetur2 = db.getOperator(opr2.getOprId());
			System.out.println(opr2.equals(OpeRetur2));
			System.out.println("Operator End");
			System.out.println();
			
			//Recipe_______________________________________________________________________________
			System.out.println("Recipe start");
			try{
				Recipe rec = new Recipe(666, "Diabolic Vodka");
				db.createRecipe(rec);
				Recipe recRet = db.getRecipe(rec.getRecipeId());
				System.out.print(rec.equals(recRet));
			}
			catch(DALException f)
			{
				
			}
			
			System.out.println("Recipe End");
			System.out.println();
			
			//RecipeComponent_____________________________________________________________________
			System.out.println("RecipeComponent start");
			try{
				RecipeComp rc = new RecipeComp(666, 666, 10, 10);
				db.createRecipeComp(rc);
				RecipeComp rcRet = db.getRecipeComp(rc.getRecipeId(), rc.getCommodityId());
				System.out.println(rc.equals(rcRet));
			}
			catch(DALException f){
			}
			System.out.println("RecipeComponent End");
			System.out.println();
			
			//ProductBatch_________________________________________________________________________
			System.out.println("ProductBatch start");
			try{
				ProductBatch pb1 = new ProductBatch(666, 666, "Kim_Time", null, 1);
				db.createProductBatch(pb1);
				ProductBatch pbRet1 = db.getProductBatch(pb1.getPbId());
				System.out.println(pb1.equals(pbRet1));
			}
			catch(DALException f){
			}
			ProductBatch pb2 = new ProductBatch(666, 666, "Kim_Time", "Tea_Time", 3);
			db.updateProductBatch(pb2);
			ProductBatch pbRet2 = db.getProductBatch(pb2.getPbId());
			System.out.println(pb2.equals(pbRet2));
			
			System.out.println("ProductBatch End");
			System.out.println();
			
			//ProductBatchComponent________________________________________________________________
			System.out.println("ProductBatchComponent start");
			try{
				ProductBatchComp pbc1 = new ProductBatchComp(666, 666, 666.1, 666.2, 666, 1);
				db.createProductBatchComp(pbc1);
				ProductBatchComp pbcRet1 = db.getProductBatchComp(pbc1.getPbId(), pbc1.getCbId());
				System.out.println(pbc1.equals(pbcRet1));
			}
			catch(DALException f){
				
			}
			System.out.println("ProductBatchComponent End");
			System.out.println();
			
		}
		catch(DALException e)
		{
			e.printStackTrace();
		}

	}

}
