package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.RecipeAdapter;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.RecipeAdapterClickListener;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.viewholders.RecipeViewHolder;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.views.RecipeRecyclerView;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener{

    @Inject
    Repository mRepository;

    @BindView(R.id.recipeList)
    RecipeRecyclerView recipeList;

    RecipeAdapter recipeAdapter;

    @BindView(R.id.fabAddNewRecipe)
    FloatingActionButton fabAddNewRecipe;


    @BindString(R.string.main_screen_title) String main_screen_title;
    @BindString(R.string.dialog_delete_title) String dialog_delete_title;
    @BindString(R.string.dialog_delete_message) String dialog_delete_message;
    @BindString(R.string.dialog_delete_confirm) String dialog_delete_confirm;
    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getComponent().inject(this);
        setTitle(main_screen_title);

        fabAddNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddRecipeActivity.class));
            }
        });

        recipeAdapter = new RecipeAdapter(this,
                new RecipeAdapterClickListener() {
                    @Override
                    public void onRecipeClicked(Recipe recipe, RecipeViewHolder viewHolder, int position) {
                        Intent intent = new Intent(getContext(), ViewRecipeActivity.class);
                        intent.putExtra(Constants.RECIPE_ID, recipe.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onRecipeLongClicked(final Recipe recipe, RecipeViewHolder viewHolder, int position) {
                        new AlertDialog.Builder(getContext())
                                .setTitle(dialog_delete_title)
                                .setMessage(dialog_delete_message)
                                .setPositiveButton(dialog_delete_confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mRepository.deleteRecipe(recipe.getId());
                                        updateRecipeList();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .show();
                    }
                });
        recipeList.setAdapter(recipeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecipeList();
    }

    private void updateRecipeList() {
        if(query.length() == 0) {
            recipeAdapter.setRecipes(mRepository.getAllRecipes());
        }
        else {
            recipeAdapter.setRecipes(doMySearch(query));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setOnQueryTextListener(this);
        //Types
        SubMenu typeSubMenu = menu.findItem(R.id.recipe_type).getSubMenu();
        typeSubMenu.setGroupCheckable(1, true, true);

        for(String type: Recipe.Types()) {
            typeSubMenu.add(type);
        }
        //Categories
        SubMenu categorySubMenu = menu.findItem(R.id.recipe_category).getSubMenu();
        categorySubMenu.setGroupCheckable(2, true, true);

        for(String category: Recipe.Categories()) {
            categorySubMenu.add(category);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent=new Intent(getContext(),AboutPageActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                Intent intent1=new Intent(getContext(),HelpPageActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        query = s;
        updateRecipeList();
        return true;
    }


    protected List<Recipe> doMySearch(String find) {
        String[] queryItems = find.split(" ");

        List<Recipe> returnRecipes = new LinkedList<>();

        if (queryItems.length < 3) {
            returnRecipes = getRecipeFilteredBy(queryItems[0]);
        } else if (queryItems.length == 3) {
            List<Recipe> query1 = getRecipeFilteredBy(queryItems[0]);
            List<Recipe> query2 = getRecipeFilteredBy(queryItems[2]);

            String operator = queryItems[1].toLowerCase();
            switch (operator) {
                case "and":
                    for (Recipe recipe : query1) {
                        if (query2.contains(recipe)) {
                            returnRecipes.add(recipe);
                        }
                    }
                    break;
                case "or":
                    for (Recipe recipe : query1) {
                        returnRecipes.add(recipe);
                    }
                    for (Recipe recipe2 : query2) {
                        if (!query1.contains(recipe2)) {
                            returnRecipes.add(recipe2);
                        }
                    }
                    break;
                case "not":
                    for (Recipe recipe : query1) {
                        if (!query2.contains(recipe)) {
                            returnRecipes.add(recipe);
                        }
                    }
                    break;
            }
        } else {//Freak out
        }

        return returnRecipes;
    }

    public List<Recipe> getRecipeFilteredBy(String filterBy) {
        List<Recipe> recipes = new ArrayList<>(mRepository.getAllRecipes());
        Iterator<Recipe> iterator = recipes.iterator();
        if (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (!recipe.getIngredients().toString().contains(filterBy)) {
                iterator.remove();
            }
        }
        return recipes;
    }

}
