package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

public class AddRecipeActivity extends BaseActivity {

    @Inject
    Repository mRepository;

    private long recipeID=-1;

    @BindView(R.id.editRecipeTitle)
    EditText editTitle;
    @BindView(R.id.editCalories)
    EditText editCalories;
    @BindView(R.id.editCookTime)
    EditText editCookTime;
    @BindView(R.id.editPrepTime)
    EditText editPrepTime;
    @BindView(R.id.editIngredients)
    EditText editIngredients;
    @BindView(R.id.editSteps)
    EditText editSteps;
    @BindView(R.id.spinCategory)
    Spinner spinCategory;
    @BindView(R.id.spinType)
    Spinner spinType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ButterKnife.bind(this);
        getComponent().inject(this);

        Intent intent=getIntent();
        if (intent.hasExtra(Constants.RECIPE_ID)){
            recipeID=intent.getLongExtra(Constants.RECIPE_ID,0L);
            Recipe recipe=mRepository.getRecipe(recipeID);
            Toast.makeText(this, recipe.getTitle(), Toast.LENGTH_SHORT).show();
            editTitle.setText(recipe.getTitle());
            editCalories.setText(recipe.getCalories()+"");
            editCookTime.setText(recipe.getCookTime()+"");
            editPrepTime.setText(recipe.getPrepTime()+"");
            editIngredients.setText(recipe.getIngredients());
            editSteps.setText(recipe.getSteps());


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.add_recipe_action_overflow,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveRecipe:
                if(recipeID==-1)
                    recipeID=(long)mRepository.getAllRecipes().size();
                mRepository.deleteRecipe(recipeID);
                //TODO
                /*mRepository.saveRecipe(new Recipe(recipeID,editTitle.getText().toString(),Integer.parseInt(editCalories.getText().toString()),
                        Integer.parseInt(editPrepTime.getText().toString()),Integer.parseInt(editCookTime.getText().toString()),
                        parseIngredients(),editSteps.getText().toString(),Constants.CATEGORY_AMERICAN, Constants.TYPE_MEAL));
                        */
                Toast.makeText(this, "Recipe Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.delete:
                mRepository.deleteRecipe(recipeID);
                finish();
                break;
        }
        return true;
    }

    private String[] parseIngredients(){
        String[] reader;
        String delimiter="\n";
        String lines=editIngredients.getText().toString();
        reader=lines.split(delimiter);
        return reader;

    }
}