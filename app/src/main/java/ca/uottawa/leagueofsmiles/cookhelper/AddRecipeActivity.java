package ca.uottawa.leagueofsmiles.cookhelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import ca.uottawa.leagueofsmiles.cookhelper.models.Recipe;

public class AddRecipeActivity extends BaseActivity {

    @Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ButterKnife.bind(this);



    }

}
