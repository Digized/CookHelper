package ca.uottawa.leagueofsmiles.cookhelper.adapters;

import ca.uottawa.leagueofsmiles.cookhelper.adapters.viewholders.RecipeViewHolder;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/9/2016.
 */

public interface RecipeAdapterClickListener {
    void onRecipeClicked(Recipe recipe, RecipeViewHolder viewHolder, int position);
    void onRecipeLongClicked(Recipe recipe, RecipeViewHolder viewHolder, int position);
}
