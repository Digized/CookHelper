package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

public class RecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent= getIntent();

        Recipe recipe= (Recipe) intent.getSerializableExtra("Selected Recipe");   //if you want to send the intent another way, change this line

        TextView title=(TextView) findViewById(R.id.recipeTitle);
        TextView recipeStats=(TextView) findViewById(R.id.recipeStats_textView);
        TextView ingredients=(TextView) findViewById(R.id.ingredientsbox);
        TextView steps=(TextView) findViewById(R.id.stepsbox);

        //TODO imagefunctionality

        title.setText(recipe.getName());
        recipeStats.setText("Prepare Time: "+recipe.getTimeToPrepare()+" minutes\nCalories: "+recipe.getCalories()); //will fix this later
        ingredients.setText(recipe.getIngredients());
        steps.setText(recipe.getSteps());

    }
}
