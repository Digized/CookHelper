package ca.uottawa.leagueofsmiles.cookhelper.models;

import com.orm.dsl.Table;

import java.io.Serializable;

import ca.uottawa.leagueofsmiles.cookhelper.Constants;

/**
 * Created by Dan on 11/8/2016.
 */

/**
 * Modified by Zuraiz on 11/9/2016
 */

public class Recipe {
    private Long id;

    private String imagePath;
    private String name;
    private int calories;
    private int timeToPrepare; //in minutes



    private int category;
    private int type;
    private String[] ingredients;
    private String steps;


    public Recipe(long recipeId, String name, int calories, int timeToPrepare, String[] ingredients, String steps, int category,int type){
        this.id = recipeId;
        this.name=name;
        this.calories=calories;
        this.timeToPrepare=timeToPrepare;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category=category;
        this.type=type;
    }
    public Recipe(long recipeId, String name, int calories, int timeToPrepare, String[] ingredients, String steps){
        this.id = recipeId;
        this.name=name;
        this.calories=calories;
        this.timeToPrepare=timeToPrepare;
        this.ingredients=ingredients;
        this.steps=steps;
        this.category= Constants.CATEGORY_AMERICAN;
        this.type=Constants.TYPE_MEAL;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(int timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
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
}