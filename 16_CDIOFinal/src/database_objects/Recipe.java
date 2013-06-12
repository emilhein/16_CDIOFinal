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
	String recipeName;
    
	public Recipe(int recipeId, String recipeName)
	{
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }

    public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
	public String getRecipeName() { return recipeName; }
	public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
	public String toString() { 
		return recipeId + "\t" + recipeId; 
	}
}
