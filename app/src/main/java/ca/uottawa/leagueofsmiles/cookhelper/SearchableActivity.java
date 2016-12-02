package ca.uottawa.leagueofsmiles.cookhelper;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.RecipeAdapter;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.RecipeAdapterClickListener;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.viewholders.RecipeViewHolder;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Ingredients;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.views.RecipeRecyclerView;

public class SearchableActivity extends BaseActivity {

    @Inject
    Repository mRepository;
    RecipeAdapter recipeAdapter;
    @BindView(R.id.recipeList)
    RecipeRecyclerView recipeList;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        ButterKnife.bind(this);
        getComponent().inject(this);

        // Get the intent, verify the action and get the query
        for(int i=0;i<Recipe.ingredientList().size();i++) {
            //Toast.makeText(this, Recipe.ingredientList().get(i).toString() + ""+Recipe.ingredientList().get(i).getIngredient(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            this.query=query;
            Toast.makeText(this,doMySearch(query).size()+"", Toast.LENGTH_SHORT).show();

            ButterKnife.bind(this);
            getComponent().inject(this);
            setTitle(main_screen_title);


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
    }


    @BindString(R.string.main_screen_title) String main_screen_title;
    @BindString(R.string.dialog_delete_title) String dialog_delete_title;
    @BindString(R.string.dialog_delete_message) String dialog_delete_message;
    @BindString(R.string.dialog_delete_confirm) String dialog_delete_confirm;



    @Override
    protected void onResume() {
        super.onResume();
        updateRecipeList();
    }

    private void updateRecipeList() {
        recipeAdapter.setRecipes(doMySearch(query));
    }

    ;

    private List<Recipe> doMySearch(String find){

        String[] queryitems=find.toLowerCase().trim().split(" ");
        List<Recipe> list=new ArrayList<>();
        for (int i=0;i<queryitems.length;i++){
            if( queryitems[i].equalsIgnoreCase("and")) {
               list.addAll(and(queryitems[i - 1], queryitems[i + 1]));
            }else if(queryitems[i].equalsIgnoreCase("or")) {
                list.addAll(or(queryitems[i-1],queryitems[i+1]));
            }//else if ((isCategory(queryitems[i])) ){
             //   list.addAll(getRecipeFromCategory(queryitems[i]));
            //}
            else if (queryitems[i].equalsIgnoreCase("not")){
                list.addAll(not(queryitems[i-1],queryitems[i+1]));
            }
        }return list;
    }
    private List<Recipe> getRecipeFromIngredient(String input){
        List<Recipe> recipeList=new ArrayList<>();
        List<Ingredients> list=Recipe.ingredientList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIngredient().equalsIgnoreCase(input)){
                //Toast.makeText(this, list.get(i).getRecipes().size()+""+list.get(i).getIngredient()+"", Toast.LENGTH_SHORT).show();
                recipeList.addAll(list.get(i).getRecipes());
               return recipeList;
            }
        }
        return recipeList;
    }
    private List<Recipe> getRecipeFromCategory(String input){
        List<Recipe> list=mRepository.getAllRecipes();
        List<Recipe> recipeList=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            if(list.get(i).getCategory().equalsIgnoreCase(input)){
                recipeList.add(list.get(i));
            }
        }
        return recipeList;
    }
    private boolean isCategory(String input){
        for (int i=1;i<Recipe.Categories().size()-1;i++){
            if (Recipe.Categories().get(i).equalsIgnoreCase(input)){
                return true;
            }
        }return false;
    }
    private boolean isIngredient(String input){
        List<Ingredients> list=Recipe.ingredientList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIngredient().equalsIgnoreCase(input)){
                return true;
            }
        }return false;
    }
    private List<Recipe> or(String q1, String q2) {
        List<Recipe> rec=new ArrayList<>();
        if(isIngredient(q1) && isIngredient(q2)){
            rec.addAll(getRecipeFromIngredient(q1));
            rec.addAll(getRecipeFromIngredient(q2));
        }
        else if (isIngredient(q1) && isCategory(q2)){
            rec.addAll(getRecipeFromIngredient(q1));
            rec.addAll(getRecipeFromCategory(q2));
        }else if (isCategory(q1) && isIngredient(q2)){
            rec.addAll(getRecipeFromCategory(q1));
            rec.addAll(getRecipeFromIngredient(q2));
        }else if(isCategory(q1) && isCategory(q2)){
            rec.addAll(getRecipeFromCategory(q1));
            rec.addAll(getRecipeFromCategory(q2));
        }
        return rec;
    }
    private List<Recipe> and(String q1, String q2){
        List<Recipe> rec=new ArrayList<>();

        if(isIngredient(q1) && isIngredient(q2)){
           for(Recipe recipe :getRecipeFromIngredient(q1)){
             //  Toast.makeText(this, q1+" "+q2+" "+recipe.getTitle()+"", Toast.LENGTH_SHORT).show();
               if(getRecipeFromIngredient(q2).contains(recipe)){
                   rec.add(recipe);
               }
           }
        }else if (isIngredient(q1) && isCategory(q2)){
            for (Recipe recipe: getRecipeFromIngredient(q1)){
                if (getRecipeFromCategory(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        else if (isCategory(q1) && isIngredient(q2)){
            for (Recipe recipe: getRecipeFromCategory(q1)){
                if (getRecipeFromIngredient(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        else if (isCategory(q1) && isCategory(q2)){
            for (Recipe recipe:getRecipeFromCategory(q2)){
                if (getRecipeFromCategory(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        return rec;
    }
    private  List<Recipe> not(String q1, String q2){
        List<Recipe> rec=new ArrayList<>();
        if (isIngredient(q1) && isIngredient(q2)){
            for (Recipe recipe: getRecipeFromIngredient(q1)){
                if (!(getRecipeFromIngredient(q2).contains(recipe))){
                    rec.add(recipe);
                }
            }
        }
        else if (isIngredient(q1) && isCategory(q2)){
                for (Recipe recipe: getRecipeFromIngredient(q1)){
                    if (!(getRecipeFromCategory(q2).contains(recipe))){
                        rec.add(recipe);
                    }
                }
        }
        else if(isCategory(q1) && isIngredient(q2)){
                for (Recipe recipe: getRecipeFromCategory(q1)){
                    if (!(getRecipeFromIngredient(q2).contains(recipe))){
                        rec.add(recipe);
                    }
                }

        }
        else if(isCategory(q1) && isCategory(q2)){
                for (Recipe recipe: getRecipeFromCategory(q1)){
                    if (!(getRecipeFromCategory(q2).contains(recipe))){
                        rec.add(recipe);
                    }
                }

        }return rec;
    }
    private  List<Recipe> orRecursive(List<Recipe> list1,List<Recipe> list2){
        List<Recipe> returnList=new ArrayList<>();
        returnList.addAll(list1);
        returnList.addAll(list2);
        return returnList;
    }
    private List<Recipe> andRecursive(List<Recipe> list1, List<Recipe> list2){
        List<Recipe> returnList=new ArrayList<>();
        Iterator<Recipe> l1iterator=list1.iterator();
        Iterator<Recipe> l2iterator=list2.iterator();
        while(l1iterator.hasNext()){

        }
        return returnList;
    }
    private List<Recipe> notRecursicve(List<Recipe> list1, List<Recipe> list2){
        List<Recipe> returnList=new ArrayList<>();
        return returnList;
    }
}

