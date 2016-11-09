package ca.uottawa.leagueofsmiles.cookhelper;

import android.os.Bundle;

import javax.inject.Inject;

import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;

public class MainActivity extends BaseActivity {

    @Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getComponent().inject(this);
    }
}
