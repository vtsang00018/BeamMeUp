package com.example.vincent.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    // tag for logcat
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    ArrayAdapter<String> listAdapter;
    ListView mListView;
    String zipCode;
    boolean isImperial;

    public ForecastFragment() {
    }

    private double convertToFahrenheit(double temp){
        double fTemp = temp * (9/5) + 32;
        return fTemp;
    }

    private void updateWeather(){
        FetchWeatherTask refresh = new FetchWeatherTask(getActivity(), listAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String defaultVal = getString(R.string.pref_location_default);
        String defaultTemp = getResources().getString(R.string.pref_temp_default);

        zipCode = sharedPreferences.getString(getString(R.string.pref_location_key), defaultVal);
        isImperial = sharedPreferences.getString(getString(R.string.pref_temp_key), defaultTemp)
                .equalsIgnoreCase(defaultTemp);
        refresh.execute(zipCode);
    }

    private void viewMap(Uri geoLocation){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
          Log.d(LOG_TAG, "Couldn't resolve intent");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // update weather so that the new weather is displayed even if settings are modified
        updateWeather();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // declares that a menu exists and allows for handling menu options
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            // if settings is clicked
            case R.id.action_settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            // if refresh is clicked
            case R.id.action_refresh:
                updateWeather();
                return true;

            case R.id.action_location:
                String uri = "geo:0,0?q=" + zipCode;
                Uri geoCode = Uri.parse(uri);
                viewMap(geoCode);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // declare the listview adapter for the forecast data
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // find the ListView for the forecasts
        mListView = (ListView)rootView.findViewById(R.id.listview_forecast);

        // create the list adapter for the forecasts
        listAdapter = new ArrayAdapter<String>(
                                // the current context (parent activity)
                                getActivity(),
                                // the layout ID to modify
                                R.layout.list_item_forecast,
                                // the view ID that will be accessed
                                R.id.list_item_forecast_textview,
                                // the forecast data that will be used
                                new ArrayList<String>());

        // bind the adapter to the listview
        mListView.setAdapter(listAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = listAdapter.getItem(position).toString();
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast );
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

}
