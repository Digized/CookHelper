package ca.uottawa.leagueofsmiles.cookhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ca.uottawa.leagueofsmiles.cookhelper.data.MockRepository;
import ca.uottawa.leagueofsmiles.cookhelper.data.Repository;

public class MainActivity extends AppCompatActivity {

    Repository mRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = new MockRepository();//Update when real repository implemented
    }
}
