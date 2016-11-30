package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.utils.ImageLoader;

public class ViewRecipeActivity extends BaseActivity {

    @Inject
    Repository mRepository;
    @BindView(R.id.recipeTitle)
    TextView title;
    @BindView(R.id.recipeStats_textView)
    TextView recipeStats;
    @BindView(R.id.ingredientsbox)
    TextView ingredients;
    @BindView(R.id.stepsbox)
    TextView steps;
    @BindView(R.id.recipeIcon)
    ImageView imgRecipe;


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

        switch (item.getItemId()){
            case R.id.edit:
                Intent intent=new Intent(getContext(),AddRecipeActivity.class);
                intent.putExtra(Constants.RECIPE_ID,recipeID);
                startActivity(intent);
                break;
            case R.id.open_youtube:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+mRepository.getRecipe(recipeID).getTitle()));
                startActivity(browserIntent);
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
        if(mRepository.getRecipe(recipeID)!=null)
        updateView();
        else finish();

    }



    private void updateView(){
        Recipe recipe=mRepository.getRecipe(recipeID);
        if(recipe.getImagePath()!=null) {
            imgRecipe.setImageBitmap(ImageLoader.loadImage(recipe.getImagePath()));
        }else {
            imgRecipe.setImageResource(R.drawable.ic_book_black_24dp);
        }
        title.setText(recipe.getTitle());
        recipeStats.setText("Prepare Time: "+recipe.getCookTime()+" minutes\nCook Time: " +recipe.getCookTime()+" minutes\nCalories: "+recipe.getCalories()+ "\nCatagory: "+//will fix this later
        (getResources().getStringArray(R.array.categories_array))[recipe.getCategory()]+"\nMeal Type: "+ (getResources().getStringArray(R.array.types_array))[recipe.getType()]);
        ingredients.setText(recipe.getIngredients());
        steps.setText(recipe.getSteps());
    }
}
