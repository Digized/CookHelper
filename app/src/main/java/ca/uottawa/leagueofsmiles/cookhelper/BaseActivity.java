package ca.uottawa.leagueofsmiles.cookhelper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import ca.uottawa.leagueofsmiles.cookhelper.app.MyApplication;
import ca.uottawa.leagueofsmiles.cookhelper.injection.ApplicationComponent;

/**
 * Created by Dan on 11/9/2016.
 */

public class BaseActivity extends AppCompatActivity {

    public ApplicationComponent getComponent() {
        return ((MyApplication) getApplicationContext()).getComponent();
    }

    public Context getContext() {
        return this;
    }

}
