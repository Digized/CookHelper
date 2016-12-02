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
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

public class SearchableActivity extends AppCompatActivity {

    @Inject
    Repository mRepository;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_LONG).show();
            //doMySearch(query);
        }
    }
    /*protected List<Recipe> doMySearch(String find){

        String[] queryparts=find.trim().split();
        for (int i=0;i<queryparts.length;i++){
            if (queryparts[i]=="AND"){

            }else if(queryparts[i]=="OR"){

            }else if(isIngridient(queryparts[i])){

            }
            else if(isCategory(queryparts[i])){

            }
        }*/
    //}
    protected List<List<Recipe>> isIngridient(String input){
        List<List<Recipe>> returnList=new ArrayList<List<Recipe>>();
        for (int i=0;i<Recipe.ingredientList().size();i++){
            if (Recipe.ingredientList().contains(input)){
                returnList.add(Recipe.ingredientList().get(i).getRecipes());
            }
        }return returnList;
    }
    protected List<List<Recipe>> isCategory(String input){
        List<List<Recipe>> returnList=new ArrayList<List<Recipe>>();
        for (int i=0;i<mRepository.getAllRecipes().size();i++){
            //if mRepository.getAllRecipes().get(i);
        }
        return null;
    }

}

