package ca.uottawa.leagueofsmiles.cookhelper;

import com.orm.SugarContext;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ca.uottawa.leagueofsmiles.cookhelper.data.LocalRepository;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.models.RecipeBuilder;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LocalRepositoryTest {

    Repository repository;

    @Before
    public void init() {
        SugarContext.init(RuntimeEnvironment.application);
        repository = new LocalRepository();
    }

    @Test
    public void insert_isCorrect() throws Exception {
        RecipeBuilder recipeBuilder = new RecipeBuilder();

        Recipe recipe = recipeBuilder.setTitle("Hello")
                .setIngredients(new String[]{"pie", "jello"})
                .build();
        repository.putRecipe(recipe);

        Recipe returnedRecipe = repository.getAllRecipes().get(0);

        TestCase.assertEquals(recipe.getTitle(), returnedRecipe.getTitle());
        TestCase.assertEquals(recipe.getIngredients(), returnedRecipe.getIngredients());
    }

    @Test
    public void update_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void delete_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void getRecipeList_isCorrect() throws Exception {
        TestCase.fail();
    }

    @Test
    public void getRecipeById_isCorrect() throws Exception {
        TestCase.fail();
    }
}