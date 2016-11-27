package ca.uottawa.leagueofsmiles.cookhelper.models;

import com.orm.dsl.Table;

import ca.uottawa.leagueofsmiles.cookhelper.Constants;

/**
 * Created by Dan on 11/8/2016.
 */

/**
 * Modified by Zuraiz on 11/9/2016
 */
@Table
public class Recipe {
    private Long id;

    private String imagePath;
    private String title;
    private int calories;
    private int cookTime; //in minutes



    private int prepTime;



    private int category;
    private int type;
    private String[] ingredients;
    private String steps;


    public Recipe(long recipeId, String title, int calories,int prepTime, int cookTime, String[] ingredients, String steps, int category, int type){
        this.id = recipeId;
        this.title = title;
        this.calories=calories;
        this.cookTime = cookTime;
        this.prepTime=prepTime;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category=category;
        this.type=type;
    }
    public Recipe(long recipeId, String title, int calories, int cookTime, String[] ingredients, String steps){
        this.id = recipeId;
        this.title = title;
        this.calories=calories;
        this.cookTime = cookTime;
        this.prepTime=0;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category= Constants.CATEGORY_AMERICAN;
        this.type=Constants.TYPE_MEAL;
    }

    public Recipe(long recipeID){
        this.id = recipeID;
    }
    public void Update(String title, int calories,int prepTime, int cookTime, String[] ingredients, String steps, int category, int type){
        this.title = title;
        this.calories=calories;
        this.cookTime = cookTime;
        this.prepTime=prepTime;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category=category;
        this.type=type;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
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

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getIngredients() {
        String s="";
        for (int i=0;i<ingredients.length;i++) {
            s = s +(i+1)+") "+ingredients[i] + "\n";
        }
        return s;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setRecipeId(long recipeId) {
        this.id = recipeId;
    }

    public int getCategory() {return category;}

    public void setCategory(int category) {this.category = category; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }
}