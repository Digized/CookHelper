package ca.uottawa.leagueofsmiles.cookhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;
import ca.uottawa.leagueofsmiles.cookhelper.utils.ImageLoader;

import static ca.uottawa.leagueofsmiles.cookhelper.R.string.dialog_delete_confirm;
import static ca.uottawa.leagueofsmiles.cookhelper.R.string.dialog_delete_message;
import static ca.uottawa.leagueofsmiles.cookhelper.R.string.dialog_delete_title;

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
    @BindView(R.id.btnImageIcon)
    ImageButton btnImageIcon;

    String picturePath;

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
            btnImageIcon.setImageBitmap(ImageLoader.loadImage(recipe.getImagePath()));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.add_recipe_action_overflow,menu);

        return true;
    }

    public void onIconClicked(View view){
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_open_image_title)
                .setItems(R.array.dialog_open_image_options,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int index){
                        switch (index){
                            case 0:
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(i, index);
                                break;
                            case 1:
                                Intent i1= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i1,index);
                                break;
                        }
                    }
                }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode) {
                case 0:
                    Uri uri = data.getData();
                    picturePath = ImageLoader.saveImage(getContext(), BitmapFactory.decodeFile(uri.getEncodedPath()), recipeID + "");
                    break;
                case 1:
                    Uri uri1 = data.getData();
                    picturePath=ImageLoader.saveImage(getContext(), BitmapFactory.decodeFile(uri1.getEncodedPath()), recipeID + "");
                    break;
            }

            btnImageIcon.setImageBitmap(ImageLoader.loadImage(picturePath));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.saveRecipe:
                    if(recipeID==-1)
                       recipeID=(long)mRepository.getAllRecipes().size();
                    mRepository.deleteRecipe(recipeID);
                    mRepository.saveRecipe(new Recipe(recipeID,editTitle.getText().toString(),Integer.parseInt(editCalories.getText().toString()),
                            Integer.parseInt(editPrepTime.getText().toString()),Integer.parseInt(editCookTime.getText().toString()),
                            parseIngredients(),editSteps.getText().toString(),Constants.CATEGORY_AMERICAN, Constants.TYPE_MEAL,
                            picturePath));
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
