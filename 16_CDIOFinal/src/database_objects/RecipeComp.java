package database_objects;

public class RecipeComp
{
	int recipeId;                  // auto genereres fra 1..n   
	int commodityId;             // i området 1-99999999
	double nomNetto;            // skal være positiv og passende stor
	double tolerance;           // skal være positiv og passende stor
	
	public RecipeComp(int recipeId, int commodityId, double nomNetto, double tolerance)
	{
		this.recipeId = recipeId;
		this.commodityId = commodityId;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
	public int getCommodityId() { return commodityId; }
	public void setCommodityId(int commodityId) { this.commodityId = commodityId; }
	public double getNomNetto() { return nomNetto; }
	public void setNomNetto(double nomNetto) { this.nomNetto = nomNetto; }
	public double getTolerance() { return tolerance; }
	public void setTolerance(double tolerance) { this.tolerance = tolerance; }
	public String toString() { 
		return recipeId + "\t" + commodityId + "\t" + nomNetto + "\t" + tolerance; 
	}
	@Override
	public boolean equals(Object obj)
	{
		RecipeComp test = (RecipeComp)obj;
		if(test.getRecipeId() == this.recipeId
				&& test.getCommodityId() == this.commodityId
				&& test.getNomNetto() == this.nomNetto
				&& test.getTolerance() == this.tolerance)
			return true;
		else
			return false;
	}
}
