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
    private Long id = null;

    private String imagePath;
    private String title;
    private int calories;
    private int cookTime; //in minutes

    private int prepTime;



    private int category;
    private int type;
    private String ingredients;
    private String steps;

    //Needed for Sugar
    public Recipe() {

    }

    public Recipe(String title, int calories,int prepTime, int cookTime, String ingredients, String steps, int category, int type,String imagePath){
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

    public int getCategory() {return category;}

    public int getType() { return type; }

    public int getPrepTime() {
        return prepTime;
    }

    public RecipeBuilder toNewBuilder() {
        //TODO implement
        return null;
    }

}