package ca.uottawa.leagueofsmiles.cookhelper.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/8/2016.
 */
//TODO
public class MockRepository implements Repository {
    List<Recipe> mockRecipes;
    public MockRepository() {
        mockRecipes = new ArrayList<>();

        for(int x = 0; x < 10; x++) {
            mockRecipes.add(new Recipe(x, "RecipeName " + x, 10, 5, new String[]{"Tomato"}, "Step1"));
        }
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return mockRecipes;
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        mockRecipes.add(recipe);
    }

    @Override
    public Recipe getRecipe(long recipeId) {
        for(int x = 0; x < mockRecipes.size(); x++) {
            if(mockRecipes.get(x).getId() == recipeId)
                return mockRecipes.get(x);
        }
        return null;
    }

    @Override
    public void deleteRecipe(long recipedId) {
        Iterator<Recipe> recipeIterator = mockRecipes.iterator();
        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();
            if(next.getId() == recipedId)
                recipeIterator.remove();
        }
    }

}
