package ca.uottawa.leagueofsmiles.cookhelper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.leagueofsmiles.cookhelper.R;
import ca.uottawa.leagueofsmiles.cookhelper.adapters.viewholders.RecipeViewHolder;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

/**
 * Created by Dan on 11/9/2016.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    List<Recipe> mData;
    private Context mContext;
    private RecipeAdapterClickListener listener;

    public RecipeAdapter(Context context, RecipeAdapterClickListener listener) {
        mContext = context;
        this.listener = listener;
        mData = new ArrayList<>();
    }
    public void setRecipes(List<Recipe> recipes) {
        this.mData = recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, final int position) {
        final Recipe mRecipe = mData.get(position);
        holder.bind(mRecipe);

        //Handle Click and long click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener != null) {
                    listener.onRecipeLongClicked(mRecipe, holder, position);
                }
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onRecipeClicked(mRecipe, holder, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
