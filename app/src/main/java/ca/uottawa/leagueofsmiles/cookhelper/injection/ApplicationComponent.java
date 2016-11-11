package ca.uottawa.leagueofsmiles.cookhelper.injection;

import ca.uottawa.leagueofsmiles.cookhelper.MainActivity;
import ca.uottawa.leagueofsmiles.cookhelper.ViewRecipeActivity;
import dagger.Component;

/**
 * Created by Dan on 11/9/2016.
 */
@Component(modules = { NetworkModule.class })
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(ViewRecipeActivity viewRecipeActivity);
}
