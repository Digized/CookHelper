package ca.uottawa.leagueofsmiles.cookhelper;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Recipe.Categories());
        spinCategory.setAdapter(categoryAdapter);
        spinCategory.setSelection(1);
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    final EditText editText=new EditText(getContext());
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.add_new_category)
                            .setView(editText)
                            .setPositiveButton(R.string.dialog_add_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    spinCategory.setSelection(Recipe.addCategory(editText.getText().toString()));
                                }
                            })
                            .setNegativeButton(android.R.string.cancel,null)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,Recipe.Types());
        spinType.setAdapter(typeAdapter);
        spinType.setSelection(1);
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    final EditText editText=new EditText(getContext());
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.add_new_type)
                            .setView(editText)
                            .setPositiveButton(R.string.dialog_add_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    spinType.setSelection(Recipe.addType(editText.getText().toString()));
                                }
                            })
                            .setNegativeButton(android.R.string.cancel,null)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent=getIntent();
        if (intent.hasExtra(Constants.RECIPE_ID)){
            recipeID=intent.getLongExtra(Constants.RECIPE_ID,0L);
            recipe=mRepository.getRecipe(recipeID);
            editTitle.setText(recipe.getTitle());
            editCalories.setText(String.valueOf(recipe.getCalories()));
            editCookTime.setText(String.valueOf(recipe.getCookTime()));
            editPrepTime.setText(String.valueOf(recipe.getPrepTime()));
            editIngredients.setText(recipe.getIngredients());
            editSteps.setText(recipe.getSteps());
            spinCategory.setSelection(Recipe.Categories().indexOf(recipe.getCategory()));
            spinType.setSelection(Recipe.Types().indexOf(recipe.getType()));
            imagePath=recipe.getImagePath();
            if(imagePath!=null){
                btnImageIcon.setImageBitmap(ImageLoader.loadImage(imagePath));
            }
            else {
                btnImageIcon.setImageResource(R.drawable.ic_book_black_24dp);
            }



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
                if(recipeID==-1){
                mRepository.putRecipe(new RecipeBuilder()
                        .setTitle(editTitle.getText().toString())
                        .setCalories(Integer.parseInt((!editCalories.getText().toString().equals(""))?editCalories.getText().toString():"0"))
                        .setPrepTime(Integer.parseInt((!editPrepTime.getText().toString().equals(""))?editPrepTime.getText().toString():"0"))
                        .setCookTime(Integer.parseInt((!editCookTime.getText().toString().equals(""))?editCookTime.getText().toString():"0"))
                        .setIngredients(parseIngredients())
                        .setSteps(editSteps.getText().toString())
                        .setType(spinType.getSelectedItemPosition())
                        .setCategory(spinCategory.getSelectedItemPosition())
                        .setImagePath(imagePath)
                        .build());
                    Toast.makeText(this, "Recipe Saved", Toast.LENGTH_SHORT).show();

                }else{
                    mRepository.updateRecipe(recipeID,new RecipeBuilder()
                            .setTitle(editTitle.getText().toString())
                            .setCalories(Integer.parseInt((!editCalories.getText().toString().equals(""))?editCalories.getText().toString():"0"))
                            .setPrepTime(Integer.parseInt((!editPrepTime.getText().toString().equals(""))?editPrepTime.getText().toString():"0"))
                            .setCookTime(Integer.parseInt((!editCookTime.getText().toString().equals(""))?editCookTime.getText().toString():"0"))
                            .setIngredients(parseIngredients())
                            .setSteps(editSteps.getText().toString())
                            .setType(spinType.getSelectedItemPosition())
                            .setCategory(spinCategory.getSelectedItemPosition())
                            .setImagePath(imagePath)
                            .update(recipeID));
                    Snackbar.make(findViewById(R.id.activity_main),"Recipe "+mRepository.getRecipe(recipeID).getTitle()+" updated",Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(this, "Recipe Updated "+ mRepository.getRecipe(recipeID).getTitle(), Toast.LENGTH_SHORT).show();

                }
                finish();
                break;
            case R.id.delete:
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_message)
                        .setPositiveButton(R.string.dialog_delete_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mRepository.deleteRecipe(recipeID);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel,null)
                        .show();

                break;
        }
        return true;
    }

    private String[] parseIngredients(){
        String[] reader;
        String lines=editIngredients.getText().toString();
        reader=lines.split(Constants.INGREDIANTS_DELIMITER);
        return reader;

    }
    public void OnbtnImageIconClick(View view){
        if(isStoragePermissionGranted()) {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");


            startActivityForResult(pickIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode==0)&&(resultCode==RESULT_OK)){
            Cursor cursor=getContentResolver().query(data.getData(),new String[]{MediaStore.Images.Media.DATA},null,null,null);
            cursor.moveToFirst();
            imagePath=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            imagePath=ImageLoader.saveImage(getContext(), bitmap,String.valueOf(recipeID));
            btnImageIcon.setImageBitmap(ImageLoader.loadImage(imagePath));
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if((requestCode==1)&&(grantResults[0]==PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
        }
    }
}