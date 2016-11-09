package ca.uottawa.leagueofsmiles.cookhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ca.uottawa.leagueofsmiles.cookhelper.app.MyApplication;
import ca.uottawa.leagueofsmiles.cookhelper.injection.ApplicationComponent;

/**
 * Created by Dan on 11/9/2016.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public ApplicationComponent getComponent() {
        return ((MyApplication) getApplicationContext()).getComponent();
    }
}
