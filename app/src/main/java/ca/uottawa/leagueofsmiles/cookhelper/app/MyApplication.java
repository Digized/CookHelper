package ca.uottawa.leagueofsmiles.cookhelper.app;

import android.app.Application;

import com.orm.SugarContext;

import ca.uottawa.leagueofsmiles.cookhelper.injection.ApplicationComponent;
import ca.uottawa.leagueofsmiles.cookhelper.injection.DaggerApplicationComponent;

/**
 * Created by Dan on 11/9/2016.
 */

public class MyApplication extends Application {
    private ApplicationComponent daggerApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        daggerApplicationComponent = DaggerApplicationComponent.create();
        SugarContext.init(this);
    }

    public ApplicationComponent getComponent() {
        return daggerApplicationComponent;
    }
}
