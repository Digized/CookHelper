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
    private static Long ids=0L;
    private static List<String> categories=new ArrayList<String>(){
        {
            add("New Category");
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
            add("New Meal Type");
            add("Appetizer");
            add("Dessert");
            add("Meal");
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
            s = s +(i+1)+") "+mIngredients[i] + "\n";
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

    public static int AddCategory(String category){
        for(String cat:categories){
            if(cat.compareToIgnoreCase(category)==0){
                return categories.indexOf(category);
            }
        }
        categories.add(category);
        return categories.indexOf(category);
    }
    public static int AddType(String type){
        for(String ty:types){
            if(ty.compareToIgnoreCase(type)==0){
                return types.indexOf(type);
            }
        }
        types.add(type);
        return types.indexOf(type);
    }

    public static List<String> categories() {
        return categories;
    }

    public static List<String> types() {
        return types;
    }

    public static Long Ids() {
        return ids;
    }
}