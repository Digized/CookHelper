package ca.uottawa.leagueofsmiles.cookhelper.models;

import java.io.Serializable;

/**
 * Created by Dan on 11/8/2016.
 */

/**
 * Modified by Zuraiz on 11/9/2016
 */
//TODO add all the properties
public class Recipe implements Serializable {
    int recipeId;


    private String name;
    private int calories;
    private int timeToPrepare; //in minutes
   private String[] ingredients;
   private String steps;


    public Recipe( String name, int calories, int timeToPrepare, String[] ingredients, String steps){
        this.name=name;
        this.calories=calories;
        this.timeToPrepare=timeToPrepare;
        this.ingredients=ingredients;
        this.steps=steps;
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
        for (String ingredient : ingredients) {
            s = s + ingredient + "\n";
        }
        return s;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }



}