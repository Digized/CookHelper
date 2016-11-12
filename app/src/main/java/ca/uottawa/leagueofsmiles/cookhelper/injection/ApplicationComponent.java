package ca.uottawa.leagueofsmiles.cookhelper.injection;

import javax.inject.Singleton;

import ca.uottawa.leagueofsmiles.cookhelper.AddRecipeActivity;
import ca.uottawa.leagueofsmiles.cookhelper.MainActivity;
import ca.uottawa.leagueofsmiles.cookhelper.ViewRecipeActivity;
import dagger.Component;

/**
 * Created by Dan on 11/9/2016.
 */
@Singleton
@Component(modules = { NetworkModule.class })
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(ViewRecipeActivity viewRecipeActivity);
    void inject(AddRecipeActivity addRecipeActivity);
}
