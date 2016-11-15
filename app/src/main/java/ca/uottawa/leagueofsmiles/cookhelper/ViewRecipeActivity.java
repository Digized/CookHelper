package ca.uottawa.leagueofsmiles.cookhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.utils.ImageLoader;

import static ca.uottawa.leagueofsmiles.cookhelper.R.string.dialog_delete_confirm;
import static ca.uottawa.leagueofsmiles.cookhelper.R.string.dialog_delete_message;
import static ca.uottawa.leagueofsmiles.cookhelper.R.string.dialog_delete_title;

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
                break;
            case R.id.delete:
                new AlertDialog.Builder(getContext())
                        .setTitle(dialog_delete_title)
                        .setMessage(dialog_delete_message)
                        .setPositiveButton(dialog_delete_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mRepository.deleteRecipe(recipeID);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;
            case R.id.open_youtube:
                Intent intent1=new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("http://www.youtube.com/results?search_query="+mRepository.getRecipe(recipeID).getTitle()));
                startActivity(intent1);
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

        ImageView recipeIcon=(ImageView) findViewById(R.id.recipeIcon);
        recipeIcon.setImageBitmap(ImageLoader.loadImage(recipe.getImagePath()));


        title.setText(recipe.getTitle());
        recipeStats.setText("Prepare Time: "+recipe.getCookTime()+" minutes\nCalories: "+recipe.getCalories()); //will fix this later
        ingredients.setText(recipe.getIngredients());
        steps.setText(recipe.getSteps());
    }
}
