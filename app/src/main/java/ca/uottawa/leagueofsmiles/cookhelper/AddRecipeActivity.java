package ca.uottawa.leagueofsmiles.cookhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.models.RecipeBuilder;
import ca.uottawa.leagueofsmiles.cookhelper.utils.ImageLoader;

public class AddRecipeActivity extends BaseActivity {

    @Inject
    Repository mRepository;
Recipe recipe;
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
    @BindView(R.id.btnImageIcon)
    ImageButton btnImageIcon;

    String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ButterKnife.bind(this);
        getComponent().inject(this);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(typeAdapter);

        Intent intent=getIntent();
        if (intent.hasExtra(Constants.RECIPE_ID)){
            recipeID=intent.getLongExtra(Constants.RECIPE_ID,0L);
            recipe=mRepository.getRecipe(recipeID);
            Toast.makeText(this, recipe.getTitle(), Toast.LENGTH_SHORT).show();
            editTitle.setText(recipe.getTitle());
            editCalories.setText(String.valueOf(recipe.getCalories()));
            editCookTime.setText(String.valueOf(recipe.getCookTime()));
            editPrepTime.setText(String.valueOf(recipe.getPrepTime()));
            editIngredients.setText(recipe.getIngredients());
            editSteps.setText(recipe.getSteps());
            spinCategory.setSelection(recipe.getCategory());
            spinType.setSelection(recipe.getType());
            btnImageIcon.setImageBitmap(ImageLoader.loadImage(recipe.getImagePath()));



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
                mRepository.putRecipe(new RecipeBuilder()
                        .setTitle(editTitle.getText().toString())
                        .setCalories(Integer.parseInt(editCalories.getText().toString()))
                        .setPrepTime(Integer.parseInt(editPrepTime.getText().toString()))
                        .setCookTime(Integer.parseInt(editCookTime.getText().toString()))
                        .setIngredients(parseIngredients())
                        .setSteps(editSteps.getText().toString())
                        .setType(spinType.getSelectedItemPosition())
                        .setCategory(spinCategory.getSelectedItemPosition())
                        .setImagePath(imagePath)
                        .build());
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
    public void OnbtnImageIconClick(View view){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode==0)&&(resultCode==RESULT_OK)){
            Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
//TODO
            Bitmap bitmap=BitmapFactory.decodeFile(data.getData()+".jpg");
           imagePath=ImageLoader.saveImage(getContext(), bitmap,String.valueOf(recipeID));
            btnImageIcon.setImageBitmap(bitmap);
        }
    }
}