package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;

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

public class MainActivity extends BaseActivity {

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
                        Intent intent = new Intent(getContext(), RecipeActivity.class);
                        intent.putExtra(Constants.RECIPE_ID, recipe.getRecipeId());
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
                                        mRepository.deleteRecipe(recipe.getRecipeId());
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .show();
                    }
                });
        recipeList.setAdapter(recipeAdapter);

        recipeAdapter.setRecipes(mRepository.getAllRecipes());
    }
}
