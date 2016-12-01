package ca.uottawa.leagueofsmiles.cookhelper;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

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
                        Toast.makeText(MainActivity.this, recipe.getId()+"", Toast.LENGTH_SHORT).show();

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
        //isStoragePermissionGranted();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecipeList();
    }

    private void updateRecipeList() {
        recipeAdapter.setRecipes(mRepository.getAllRecipes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
