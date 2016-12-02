package ca.uottawa.leagueofsmiles.cookhelper;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Ingredients;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

import static java.security.AccessController.getContext;

public class SearchableActivity extends AppCompatActivity {

    @Inject
    Repository mRepository;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        // Get the intent, verify the action and get the query
        for(int i=0;i<Recipe.ingredientList().size();i++) {
            Toast.makeText(this, Recipe.ingredientList().get(i).toString() + ""+Recipe.ingredientList().get(i).getIngredient(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
           // Toast.makeText(this,doMySearch(query).size()+"", Toast.LENGTH_SHORT).show();
        }
    }

    protected List<Recipe> doMySearch(String find){

        String[] queryitems=find.toLowerCase().trim().split(" ");
        List<Recipe> list=new ArrayList<Recipe>();
        for (int i=0;i<queryitems.length;i++){
            if( queryitems[i].equalsIgnoreCase("and")) {
               list.addAll(and(queryitems[i - 1], queryitems[i + 1]));
            }else if(queryitems[i]=="or") {
                //or(queryitems[i-1],queryitems[i+1]);
            }
        }return list;
    }
    protected List<Recipe> getRecipeFromIngredient(String input){
        List<Recipe> recipeList=new ArrayList<Recipe>();
        List<Ingredients> list=Recipe.ingredientList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIngredient().equalsIgnoreCase(input)){
                //Toast.makeText(this, list.get(i).getRecipes().size()+""+list.get(i).getIngredient()+"", Toast.LENGTH_SHORT).show();
                recipeList.addAll(list.get(i).getRecipes());
               // return recipeList;
            }
        }return recipeList;
    }
    protected List<List<Recipe>> isCategory(String input){
        List<List<Recipe>> returnList=new ArrayList<List<Recipe>>();
        for (int i=0;i<mRepository.getAllRecipes().size();i++){
            //if mRepository.getAllRecipes().get(i);
        }
        return null;
    }
    protected boolean isIngredient(String input){
        List<Ingredients> list=Recipe.ingredientList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIngredient().equalsIgnoreCase(input)){
                return true;
            }
        }return false;

    }
    protected List<Recipe> or(String q1,String q2) {
        List<Recipe> rec=new ArrayList<Recipe>();
        if(isIngredient(q1) && isIngredient(q2)){
            rec.addAll(getRecipeFromIngredient(q1));
            rec.addAll(getRecipeFromIngredient(q2));
        }return rec;
    }
    private List<Recipe> and(String q1,String q2){
        List<Recipe> rec=new ArrayList<Recipe>();

        if(isIngredient(q1) && isIngredient(q2)){
           for(Recipe recipe :getRecipeFromIngredient(q1)){
             //  Toast.makeText(this, q1+" "+q2+" "+recipe.getTitle()+"", Toast.LENGTH_SHORT).show();
               if(getRecipeFromIngredient(q2).contains(recipe)){
                   rec.add(recipe);
               }
           }
        }return rec;
    }
}

