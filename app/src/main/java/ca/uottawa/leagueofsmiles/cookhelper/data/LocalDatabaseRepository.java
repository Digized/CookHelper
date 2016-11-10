package ca.uottawa.leagueofsmiles.cookhelper.data;

import com.orm.SugarRecord;

import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/10/2016.
 */

public class LocalDatabaseRepository implements Repository {
    @Override
    public List<Recipe> getAllRecipes() {
        return SugarRecord.listAll(Recipe.class);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        SugarRecord.save(recipe);
    }

    @Override
    public Recipe getRecipe(final long recipeId) {
        return SugarRecord.findById(Recipe.class, recipeId);
    }

    @Override
    public void deleteRecipe(long recipedId) {
        SugarRecord.deleteAll(Recipe.class, "id=?", new String[]{ String.valueOf(recipedId) });
    }
}
