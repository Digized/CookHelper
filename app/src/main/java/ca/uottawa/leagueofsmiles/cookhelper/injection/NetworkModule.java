package ca.uottawa.leagueofsmiles.cookhelper.injection;

import ca.uottawa.leagueofsmiles.cookhelper.data.LocalDatabaseRepository;
import ca.uottawa.leagueofsmiles.cookhelper.data.MockRepository;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Dan on 11/9/2016.
 */
@Module
public class NetworkModule {

    @Provides
    Repository providesRepository() {
        return new LocalDatabaseRepository();
    }

}
