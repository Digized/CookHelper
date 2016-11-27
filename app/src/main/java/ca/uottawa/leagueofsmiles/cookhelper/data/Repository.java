package ca.uottawa.leagueofsmiles.cookhelper.data;

import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/8/2016.
 */

public interface Repository {
    List<Recipe> getAllRecipes();

    /**
     * Inserts or updates the recipe using the id
     * @param recipe
     */
    void putRecipe(Recipe recipe);

    /**
     * Gets the recipe with the recipeId or returns null if not found
     * @param recipeId
     * @return
     */
    Recipe getRecipe(long recipeId);

    void deleteRecipe(long recipedId);

    /**
     *
     * @param recipeId
     * @param recipe
     * @return true if recipe found and updated
     */
    boolean updateRecipe(long recipeId, Recipe recipe);

}
