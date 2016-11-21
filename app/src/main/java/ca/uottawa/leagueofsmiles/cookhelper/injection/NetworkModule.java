package ca.uottawa.leagueofsmiles.cookhelper.injection;

import javax.inject.Singleton;

import ca.uottawa.leagueofsmiles.cookhelper.data.LocalRepository;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Dan on 11/9/2016.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    Repository providesRepository() {
        return new LocalRepository();
    }

}
