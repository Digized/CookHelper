package ca.uottawa.leagueofsmiles.cookhelper.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ca.uottawa.leagueofsmiles.cookhelper.Constants;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.models.RecipeBuilder;

/**
 * Created by Dan on 11/8/2016.
 */
public class MockRepository implements Repository {
    List<Recipe> mockRecipes;

    public MockRepository() {
        mockRecipes = new ArrayList<>();
        preLoadedRecipe();
    }

    private void preLoadedRecipe(){
        mockRecipes.add(new RecipeBuilder()
                .setTitle("Lesagna")
                .setCalories(400)
                .setPrepTime(20)
                .setCookTime(60)
                .setIngredients(new String[]{"Beef","Cheese", "Parsley","Egg","Noodles"})
                .setSteps("Heat oven to 350°F.\n" +
                        "Brown meat in large skillet on medium-high heat. Meanwhile, mix 1-1/4 cups mozzarella, ricotta, 1/4 cup Parmesan, parsley and egg until blended.\n" +
                        "Drain meat; return to skillet. Stir in pasta sauce. Pour water into empty sauce jar; cover and shake well. Add to skillet; stir until blended.\n" +
                        "Spread 1 cup meat sauce onto bottom of 13x9-inch baking dish; top with layers of 3 lasagna noodles, 1/3 of the ricotta mixture and 1 cup meat sauce. Repeat layers twice. Top with remaining noodles and meat sauce. Sprinkle with remaining mozzarella and Parmesan. Cover with foil sprayed with cooking spray.\n" +
                        "Bake 1 hour or until heated through, uncovering after 45 min. Let stand 15 min. before cutting to serve.")
                .setType(Constants.TYPE_MEAL)
                .setCategory(Constants.CATEGORY_ITALIAN)
                .build());

        mockRecipes.add(new RecipeBuilder()
                .setTitle("Omelet")
                .setCalories(150)
                .setPrepTime(5)
                .setCookTime(4)
                .setIngredients(new String[]{"Egg","Cheese", "Parsley","Salt","Pepper"})
                .setSteps("Heat pot on a stove to Max and buttered°F.\n" +
                        "Mix all ingredients together.\n" +
                        "Add the mixed ingredenients into pot .\n" +
                        "Flip  the omelet every minute.\n" +
                        "Serve.")
                .setType(Constants.TYPE_MEAL)
                .setCategory(Constants.CATEGORY_CANADIAN)
                .build());

        mockRecipes.add(new RecipeBuilder()
                .setTitle("French Fries")
                .setCalories(400)
                .setPrepTime(10)
                .setCookTime(20)
                .setIngredients(new String[]{"Potato","Oil", "Salt","Pepper"})
                .setSteps("Heat a few inches of vegetable oil to 300 degrees F in a heavy pot.\n" +
                        " In 3 or 4 batches, fry the potatoes about 4 to 5 minutes per batch, or until soft.\n" +
                        " They should not be brown at all at this point-you just want to start the cooking process.\n" +
                        " Remove each batch and drain them on new, dry paper towels..")
                .setType(Constants.TYPE_MEAL)
                .setCategory(Constants.CATEGORY_FRENCH)
                .build());

        mockRecipes.add(new RecipeBuilder()
                .setTitle("Tacco")
                .setCalories(400)
                .setPrepTime(20)
                .setCookTime(60)
                .setIngredients(new String[]{"Beef","Cheese", "Parsley","Egg","Noodles"})
                .setSteps("Heat oven to 350°F.\n" +
                        "Brown meat in large skillet on medium-high heat. Meanwhile, mix 1-1/4 cups mozzarella, ricotta, 1/4 cup Parmesan, parsley and egg until blended.\n" +
                        "Drain meat; return to skillet. Stir in pasta sauce. Pour water into empty sauce jar; cover and shake well. Add to skillet; stir until blended.\n" +
                        "Spread 1 cup meat sauce onto bottom of 13x9-inch baking dish; top with layers of 3 lasagna noodles, 1/3 of the ricotta mixture and 1 cup meat sauce. Repeat layers twice. Top with remaining noodles and meat sauce. Sprinkle with remaining mozzarella and Parmesan. Cover with foil sprayed with cooking spray.\n" +
                        "Bake 1 hour or until heated through, uncovering after 45 min. Let stand 15 min. before cutting to serve.")
                .setType(Constants.TYPE_MEAL)
                .setCategory(Constants.CATEGORY_ITALIAN)
                .build());

        mockRecipes.add(new RecipeBuilder()
                .setTitle("Vegitarian Biryani")
                .setCalories(300)
                .setPrepTime(40)
                .setCookTime(60)
                .setIngredients(new String[]{"Vegetables","Rice", "Biryani Masala","Other Mixed spices"})
                .setSteps("Melt ghee in a large Dutch oven over medium heat. Add onion, and cook until softened, about 3 minutes.\n" +
                        " Stir in cumin seed, cinnamon stick, and peppercorns; cook until the spices are fragrant, and the cumin seeds begin to pop, about 3 minutes.\n" +
                        "Stir in ginger garlic paste, tomatoes, and 1/2 cup water. Bring to a simmer, and cook until the water has evaporated, about 5 minutes. Stir in peas, carrot, and potato.\n" +
                        " Season with chicken bouillon, salt, red chile, black pepper, garam masala, and turmeric. Stir well, then cover, and cook for 3 minutes.\n" +
                        "Pour in 4 cups water and bring to a boil over high heat. Once boiling, stir in basmati rice, reduce heat to medium, recover, and simmer for 10 minutes. Reduce heat to low and continue to cook until the rice has softened, 10 to 15 minutes more..")
                .setType(Constants.TYPE_MEAL)
                .setCategory(Constants.CATEGORY_INDIAN)
                .build());

        mockRecipes.add(new RecipeBuilder()
                .setTitle("Vinilla Cup Cakes")
                .setCalories(180)
                .setPrepTime(15)
                .setCookTime(25)
                .setIngredients(new String[]{"Flour","salt", "baking powder","Sugar","Egg","Milk","vanilla essence"})
                .setSteps("preheat oven to 375f or 190c; line muffin cups with papers.\n" +
                        "cream butter and sugar till light and fluffy. beat in eggs one at a time.\n" +
                        "add flour (mixed with baking powder and salt) alternating with milk beat well; stir in vanilla.\n" +
                        "divide evenly among pans and bake for 18 minutes. let cool in pans.")
                .setType(Constants.TYPE_DESSERT)
                .setCategory(Constants.CATEGORY_AMERICAN)
                .build());
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
