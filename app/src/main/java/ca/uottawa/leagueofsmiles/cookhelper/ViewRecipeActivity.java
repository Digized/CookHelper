package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

public class ViewRecipeActivity extends BaseActivity {

    @Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ButterKnife.bind(this);
        getComponent().inject(this);

        Intent intent= getIntent();
        long recipeID= intent.getLongExtra(Constants.RECIPE_ID,0L);
        Recipe recipe=mRepository.getRecipe(recipeID);

        TextView title=(TextView) findViewById(R.id.recipeTitle);
        TextView recipeStats=(TextView) findViewById(R.id.recipeStats_textView);
        TextView ingredients=(TextView) findViewById(R.id.ingredientsbox);
        TextView steps=(TextView) findViewById(R.id.stepsbox);

        //TODO image functionality

        title.setText(recipe.getName());
        recipeStats.setText("Prepare Time: "+recipe.getTimeToPrepare()+" minutes\nCalories: "+recipe.getCalories()); //will fix this later
        ingredients.setText(recipe.getIngredients());
        steps.setText(recipe.getSteps());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.view_recipe_action_overflow,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
