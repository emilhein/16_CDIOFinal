package database_objects;

/**
 * Recipe Data Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class Recipe 
{
	int recipeId;
	String recipeNavn;
    
	public Recipe(int recipeId, String recipeNavn)
	{
        this.recipeId = recipeId;
        this.recipeNavn = recipeNavn;
    }

    public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
	public String getRecipeNavn() { return recipeNavn; }
	public void setRecipeNavn(String recipeNavn) { this.recipeNavn = recipeNavn; }
	public String toString() { 
		return recipeId + "\t" + recipeId; 
	}
}
