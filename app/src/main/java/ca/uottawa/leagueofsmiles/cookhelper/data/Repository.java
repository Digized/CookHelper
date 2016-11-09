package ca.uottawa.leagueofsmiles.cookhelper.data;

import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/8/2016.
 */

public interface Repository {
    List<Recipe> getAllRecipes();

    void saveRecipe(Recipe recipe);

    Recipe getRecipe(int recipeId);

    void deleteRecipe(int recipedId);
}
