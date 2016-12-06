package ca.uottawa.leagueofsmiles.cookhelper.utils;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by sba49 on 2016-12-06.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "ca.uottawa.leagueofsmiles.cookhelper";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
