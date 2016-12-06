package ca.uottawa.leagueofsmiles.cookhelper;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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
import ca.uottawa.leagueofsmiles.cookhelper.utils.MySuggestionProvider;
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
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY,MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query,null);
            suggestions.clearHistory();
            this.query=query;

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
        recipeAdapter.setRecipes(handleMySearch(query));
    }

    private List<Recipe> handleMySearch(String find){

        String[] queryitems=find.toLowerCase().trim().split(" ");
        List<Recipe> returnList=new ArrayList<>();
        Stack<String> operators=new Stack<>();
        Stack<String> stringsgiven=new Stack<>();
        Stack<List<Recipe>> recipeListStack=new Stack<>();

        for (int i=queryitems.length-1;0<=i;i--){
            if (queryitems[i].equalsIgnoreCase("and")){
                operators.push(queryitems[i]);
            }else if(queryitems[i].equalsIgnoreCase("or")){
                operators.push(queryitems[i]);
            }else if (queryitems[i].equalsIgnoreCase("not")){
                operators.push(queryitems[i]);
            }
            else if(isIngredient(queryitems[i])){
                stringsgiven.push(queryitems[i]);
                recipeListStack.push(getRecipeFromIngredient(queryitems[i]));
            }else if (isCategory(queryitems[i])){
                stringsgiven.push(queryitems[i]);
                recipeListStack.push(getRecipeFromCategory(queryitems[i]));
            }else if (isType(queryitems[i])){
                stringsgiven.push(queryitems[i]);
                recipeListStack.push(getRecipeFromType(queryitems[i]));
            }
        }
        //Toast.makeText(this,operators.pop()+"", Toast.LENGTH_SHORT).show();
        if (operators.isEmpty()){
            if (isIngredient(find)){
                returnList.addAll(recipeListStack.pop());
            }else if(isCategory(find)){
                returnList.addAll(recipeListStack.pop());
            }else if(isType(find)){
                returnList.addAll(recipeListStack.pop());
            }else if(isRecipe(find)){
                for(Recipe recipe:mRepository.getAllRecipes()){
                    if (recipe.getTitle().equalsIgnoreCase(find)){
                        returnList.add(recipe);
                    }
                }
            }
            return returnList;
        }
        while(!(operators.isEmpty())){
            List<Recipe> q1=recipeListStack.pop();
            List<Recipe> q2=recipeListStack.pop();
            String operation=operators.pop();
            if (operation.equalsIgnoreCase("and")){
                recipeListStack.push(andRecursive(q1,q2));
            }else if(operation.equalsIgnoreCase("or")){
                recipeListStack.push(orRecursive(q1,q2));
            }else if (operation.equalsIgnoreCase("not")){
                recipeListStack.push(notRecursive(q1,q2));
            }

        }returnList.addAll(recipeListStack.peek());
        return returnList;


    }

    private List<Recipe> doMySearch(String find){
        String[] queryitems=find.toLowerCase().trim().split(" ");
        List<Recipe> returnList=new ArrayList<>();
        Toast.makeText(this,queryitems[0]+queryitems[1]+queryitems[2]+""+queryitems.length, Toast.LENGTH_SHORT).show();


        for (int i=0;i<queryitems.length;i++){
            if( queryitems[i].equalsIgnoreCase("and")) {
               returnList.addAll(and(queryitems[i - 1], queryitems[i + 1]));
            }else if(queryitems[i].equalsIgnoreCase("or")) {
                returnList.addAll(or(queryitems[i-1],queryitems[i+1]));
            }
            else if (queryitems[i].equalsIgnoreCase("not")){
                returnList.addAll(not(queryitems[i-1],queryitems[i+1]));
            }
        }
        return returnList;
    }


    private List<Recipe> getRecipeFromType(String input){
        List<Recipe> recipeList=new ArrayList<>();
        List<Recipe> list=mRepository.getAllRecipes();
        for (int i=0;i<list.size();i++){
            if(list.get(i).getType().equalsIgnoreCase(input)){
                recipeList.add(list.get(i));
            }
        }return recipeList;
    }
    private List<Recipe> getRecipeFromIngredient(String input){
        List<Recipe> recipeList=new ArrayList<>();
        List<Ingredients> list=Recipe.ingredientList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIngredient().equalsIgnoreCase(input)){
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
        Toast.makeText(this,recipeList.size()+ "", Toast.LENGTH_SHORT).show();
        return recipeList;
    }

    private boolean isRecipe(String input){
        List<Recipe> list=mRepository.getAllRecipes();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getTitle().equalsIgnoreCase(input)){
                return true;
            }
        }return false;
    }
    private boolean isType(String input){
        for (int i=1;i<Recipe.Types().size()-1;i++){
            if (Recipe.Types().get(i).equalsIgnoreCase(input)){
                return true;
            }
        }return false;
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
        }
        return false;
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
        else if(isCategory(q1) && isType(q2)){
            rec.addAll(getRecipeFromCategory(q1));
            rec.addAll(getRecipeFromType(q2));
        }
        else if(isType(q1) && isCategory(q2)){
            rec.addAll(getRecipeFromType(q1));
            rec.addAll(getRecipeFromCategory(q2));
        }
        else if(isType(q1) && isIngredient(q2)){
            rec.addAll(getRecipeFromType(q1));
            rec.addAll(getRecipeFromIngredient(q2));
        }
        else if(isIngredient(q1) && isType(q2)){
            rec.addAll(getRecipeFromIngredient(q1));
            rec.addAll(getRecipeFromType(q2));
        }
        return rec;
    }
    private List<Recipe> and(String q1, String q2){
        List<Recipe> rec=new ArrayList<>();
        Toast.makeText(this,q1+q2+ "", Toast.LENGTH_SHORT).show();
        if(isIngredient(q1) && isIngredient(q2)){
           for(Recipe recipe: getRecipeFromIngredient(q1)){
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
            for (Recipe recipe:getRecipeFromCategory(q1)){
                if (getRecipeFromCategory(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        else if (isCategory(q1) && isType(q2)){
            for (Recipe recipe:getRecipeFromCategory(q1)){
                if (getRecipeFromType(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        else if (isType(q1) && isCategory(q2)){
            for (Recipe recipe:getRecipeFromType(q1)){
                if (getRecipeFromCategory(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        else if (isType(q1) && isIngredient(q2)){
            for (Recipe recipe:getRecipeFromType(q1)){
                if (getRecipeFromIngredient(q2).contains(recipe)){
                    rec.add(recipe);
                }
            }
        }
        else if (isIngredient(q1) && isType(q2)){
            for (Recipe recipe:getRecipeFromIngredient(q2)){
                if (getRecipeFromType(q2).contains(recipe)){
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

        }
        else if(isCategory(q1) && isType(q2)){
            for (Recipe recipe: getRecipeFromCategory(q1)){
                if (!(getRecipeFromType(q2).contains(recipe))){
                    rec.add(recipe);
                }
            }

        }
        else if(isType(q1) && isCategory(q2)){
            for (Recipe recipe: getRecipeFromType(q1)){
                if (!(getRecipeFromCategory(q2).contains(recipe))){
                    rec.add(recipe);
                }
            }

        }
        else if(isType(q1) && isIngredient(q2)){
            for (Recipe recipe: getRecipeFromType(q1)){
                if (!(getRecipeFromIngredient(q2).contains(recipe))){
                    rec.add(recipe);
                }
            }

        }
        else if(isIngredient(q1) && isType(q2)){
            for (Recipe recipe: getRecipeFromIngredient(q1)){
                if (!(getRecipeFromType(q2).contains(recipe))){
                    rec.add(recipe);
                }
            }

        }
        return rec;
    }
    private List<Recipe> andRecursive(List<Recipe> list1, List<Recipe> list2){
        List<Recipe> returnList=new ArrayList<>();
        for (Recipe templ: list1){
            if (list2.contains(templ)){
                returnList.add(templ);
            }
        }
        return returnList;
    }
    private List<Recipe> orRecursive(List<Recipe> list1, List<Recipe> list2){
        List<Recipe> returnList=new ArrayList<>();
        returnList.addAll(list1);
        returnList.addAll(list2);
        return returnList;
    }
    private List<Recipe> notRecursive(List<Recipe> list1,List<Recipe> list2){
        List<Recipe> returnList=new ArrayList<>();
        for (Recipe recipe:list2){
            if (!(list1.contains(recipe))){
                returnList.add(recipe);
            }
        }
        return returnList;
    }
}

