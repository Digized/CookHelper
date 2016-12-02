package ca.uottawa.leagueofsmiles.cookhelper.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sba49 on 2016-12-01.
 */

public class Ingredients {
    List<Recipe> recipes;
    String ingredient;

    public Ingredients(String ingridentname,Recipe recipe){
        recipes=new ArrayList<Recipe>();
        this.ingredient=ingridentname;
    }
    public void add(Recipe recipe){
        recipes.add(recipe);
    }
    public List<Recipe> getRecipes(){
        return recipes;
    }
    public String getIngredient(){
        return ingredient;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (obj==this) return true;
        if (!(obj instanceof Ingredients)) return false;
        Ingredients otherIng=(Ingredients) obj;
        if (ingredient.equalsIgnoreCase(otherIng.getIngredient())){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s="";
        for (Recipe rec:recipes ) {
            s=s+rec.getTitle();
        }
        return s;
    }
}
