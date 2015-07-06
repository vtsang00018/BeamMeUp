package com.example.vincent.sunshine;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;



public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.weather_detail_container, new DetailActivityFragment())
                    .commit();
        }
    }

}
