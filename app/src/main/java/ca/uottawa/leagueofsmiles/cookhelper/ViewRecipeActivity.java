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

    private long recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        ButterKnife.bind(this);
        getComponent().inject(this);

        Intent intent= getIntent();
        recipeID= intent.getLongExtra(Constants.RECIPE_ID,0L);
        updateView();
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

        switch (item.getItemId()){
            case R.id.edit:
                Intent intent=new Intent(getContext(),AddRecipeActivity.class);
                intent.putExtra(Constants.RECIPE_ID,recipeID);
                startActivity(intent);
                finish();
                break;
            case R.id.open_youtube:
                Intent intent1 = new Intent(Intent.ACTION_SEARCH);
                intent1.setPackage("com.google.android.youtube");
                intent1.putExtra("query", "Android");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            case R.id.delete:
                mRepository.deleteRecipe(recipeID);
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();

    }

    private void updateView(){
        Recipe recipe=mRepository.getRecipe(recipeID);

        TextView title=(TextView) findViewById(R.id.recipeTitle);
        TextView recipeStats=(TextView) findViewById(R.id.recipeStats_textView);
        TextView ingredients=(TextView) findViewById(R.id.ingredientsbox);
        TextView steps=(TextView) findViewById(R.id.stepsbox);

        //TODO image functionality

        title.setText(recipe.getTitle());
        recipeStats.setText("Prepare Time: "+recipe.getCookTime()+" minutes\nCalories: "+recipe.getCalories()); //will fix this later
        ingredients.setText(recipe.getIngredients());
        steps.setText(recipe.getSteps());
    }
}
