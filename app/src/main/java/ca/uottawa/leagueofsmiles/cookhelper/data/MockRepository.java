package ca.uottawa.leagueofsmiles.cookhelper.data;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/8/2016.
 */
//TODO
public class MockRepository implements Repository {
    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> mockRecipes = new ArrayList<>();

        for(int x = 0; x < 10; x++) {
            mockRecipes.add(new Recipe(x, "RecipeName " + x, 10, 5, new String[]{"Tomato"}, "Step1"));
        }
        return mockRecipes;
    }

    @Override
    public void saveRecipe(Recipe recipe) {

    }

    @Override
    public Recipe getRecipe(int recipeId) {
        return new Recipe(1, "RecipeName " + 1, 10, 5, new String[]{"Tomato"}, "Step1");
    }

    @Override
    public void deleteRecipe(int recipedId) {

    }

}
