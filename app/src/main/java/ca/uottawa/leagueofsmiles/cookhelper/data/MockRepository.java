package ca.uottawa.leagueofsmiles.cookhelper.data;

import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/8/2016.
 */
//TODO
public class MockRepository implements Repository {
    @Override
    public List<Recipe> getAllRecipes() {
        return null;
    }

    @Override
    public void saveRecipe(Recipe recipe) {

    }

    @Override
    public Recipe getRecipe(int recipeId) {
        return null;
    }
}
