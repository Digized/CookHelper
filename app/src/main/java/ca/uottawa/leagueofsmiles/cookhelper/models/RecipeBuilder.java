package ca.uottawa.leagueofsmiles.cookhelper.models;

import android.text.TextUtils;

public class RecipeBuilder {
    private String title;
    private int calories;
    private int prepTime;
    private int cookTime;
    private String ingredients;
    private String steps;
    private int category;
    private int type;
    private String imagePath;

    public RecipeBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public RecipeBuilder setCalories(int calories) {
        this.calories = calories;
        return this;
    }

    public RecipeBuilder setPrepTime(int prepTime) {
        this.prepTime = prepTime;
        return this;
    }

    public RecipeBuilder setCookTime(int cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public RecipeBuilder setIngredients(String[] ingredients) {
        this.ingredients = TextUtils.join("\n\r", ingredients);
        return this;
    }

    public RecipeBuilder setSteps(String steps) {
        this.steps = steps;
        return this;
    }

    public RecipeBuilder setCategory(int category) {
        this.category = category;
        return this;
    }

    public RecipeBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public RecipeBuilder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Recipe build() {
        return new Recipe(title, calories, prepTime, cookTime, ingredients, steps, category, type, imagePath);
    }
    public Recipe update(long recipeId){
        return new Recipe(recipeId,title, calories, prepTime, cookTime, ingredients, steps, category, type, imagePath);
    }
}