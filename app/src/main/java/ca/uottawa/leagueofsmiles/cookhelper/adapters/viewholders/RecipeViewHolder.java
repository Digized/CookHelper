package ca.uottawa.leagueofsmiles.cookhelper.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.R;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/9/2016.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgRecipe)
    ImageView imgRecipe;

    @BindView(R.id.txtRecipeName)
    TextView txtRecipeName;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Recipe recipe) {
        txtRecipeName.setText(recipe.getName());
    }
}
