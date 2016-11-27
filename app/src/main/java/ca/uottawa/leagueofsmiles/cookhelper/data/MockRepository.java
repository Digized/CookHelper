package ca.uottawa.leagueofsmiles.cookhelper.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.models.RecipeBuilder;

/**
 * Created by Dan on 11/8/2016.
 */
public class MockRepository implements Repository {
    List<Recipe> mockRecipes;

    public MockRepository() {
        mockRecipes = new ArrayList<>();

        for (int x = 0; x < 10; x++) {
            mockRecipes.add(
                    new RecipeBuilder()
                            .setTitle("RecipeName " + x)
                            .setIngredients(new String[]{"Tomato"})
                            .build()
            );
        }
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return mockRecipes;
    }

    @Override
    public void putRecipe(Recipe recipe) {
        mockRecipes.add(recipe);
    }

    @Override
    public Recipe getRecipe(long recipeId) {
        for (int x = 0; x < mockRecipes.size(); x++) {
            if (mockRecipes.get(x).getId() == recipeId)
                return mockRecipes.get(x);
        }
        return null;
    }

    @Override
    public void deleteRecipe(long recipedId) {
        Iterator<Recipe> recipeIterator = mockRecipes.iterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if (next.getId() == recipedId)
                recipeIterator.remove();
        }
    }

    @Override
    public boolean updateRecipe(long recipeId, Recipe recipe) {
        ListIterator<Recipe> recipeIterator = mockRecipes.listIterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if (next.getId() == recipeId) {
                recipeIterator.set(recipe);
                return true;
            }
        }
        return false;
    }
}
