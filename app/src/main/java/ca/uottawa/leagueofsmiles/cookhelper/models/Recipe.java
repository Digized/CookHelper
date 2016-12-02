package ca.uottawa.leagueofsmiles.cookhelper.models;

import com.orm.dsl.Table;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.Constants;

/**
 * Created by Dan on 11/8/2016.
 */

/**
 * Modified by Zuraiz on 11/9/2016
 */
@Table
public class Recipe {
    private static Long ids=1L;
    private static List<Ingredients> ingredientsList=new ArrayList<>();
    private static List<String> categories=new ArrayList<String>(){
        {
            add("Add New Category");
            add("Italian");
            add("Spanish");
            add("Indian");
            add("Jamaican");
            add("American");
            add("Canadian");
            add("French");

        }
    };
    private static List<String> types=new ArrayList<String>(){
        {
            add("Add New Meal Type");
            add("Appetizer");
            add("Dessert");
            add("Main Course");
        }
    };

    private Long id = null;

    private String imagePath;
    private String title;
    private int calories;
    private int cookTime; //in minutes
    private int prepTime;
    private String category;
    private String type;
    private String ingredients;
    private String steps;

    //Needed for Sugar
    public Recipe() {

    }

    public Recipe(String title, int calories,int prepTime, int cookTime, String ingredients, String steps, int category, int type,String imagePath){
		this.id=ids++;
        this.title = title;
        this.calories=calories;
        this.cookTime = cookTime;
        this.prepTime=prepTime;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category=categories.get(category);
        this.type=types.get(type);
        this.imagePath=imagePath;
	}
    public Recipe(String title, int calories,int prepTime, int cookTime, String ingredients, String steps, String category, String type,String imagePath){
        this.id=ids++;
        this.title = title;
        this.calories=calories;
        this.cookTime = cookTime;
        this.prepTime=prepTime;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category=category;
        this.type=type;
        this.imagePath=imagePath;
    }
    public Recipe(long id,String title, int calories,int prepTime, int cookTime, String ingredients, String steps, int category, int type,String imagePath){
        this.id=id;
        this.title = title;
        this.calories=calories;
        this.cookTime = cookTime;
        this.prepTime=prepTime;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category=categories.get(category);
        this.type=types.get(type);
        this.imagePath=imagePath;
    }

    public String getSteps() {
        return steps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }


    public int getCookTime() {
        return cookTime;
    }

    public String getIngredients() {
        String s="";
        String[] mIngredients = ingredients.split(Constants.INGREDIANTS_DELIMITER);

        for (int i=0;i<mIngredients.length;i++) {
            s =s+mIngredients[i] + "\n";
        }
        return s;
    }

    public Long getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategory() {return category;}

    public String getType() { return type; }

    public int getPrepTime() {
        return prepTime;
    }

    public RecipeBuilder toNewBuilder() {
        //TODO implement
        return null;
    }
    private void storeIngredients(String ingredients){
        String[] ingridient=ingredients.trim().split(Constants.INGREDIANTS_DELIMITER);
        for(String ing: ingridient){
            storeIngredient(ing);
        }

    }
    private void storeIngredient(String ingredient){
        for(Ingredients ingredients1:ingredientsList){
            if(ingredients1.getIngredient().equalsIgnoreCase(ingredient)){
                ingredients1.add(this);
                return;
            }
        }ingredientsList.add(new Ingredients(ingredient,this));
    }
    public static List<Ingredients> ingredientList(){
        return ingredientsList;
    }

    public static List<String> Categories() {
        return categories;
    }
    public static List<String> Types() {
        return types;
    }

    public static int addCategory(String newCategory){
        for (String category: categories){
            if (category.equalsIgnoreCase(newCategory))
                return categories.indexOf(newCategory);
        }
        categories.add(newCategory);
        return categories.indexOf(newCategory);
    }
    public static int addType (String newType){
        for (String type: types){
            if (type.equalsIgnoreCase(newType))
                return types.indexOf(newType);
        }
        types.add(newType);
        return types.indexOf(newType);
    }
}