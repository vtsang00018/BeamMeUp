package com.example.vincent.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> listAdapter;
    ListView mListView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // declare the listview adapter for the forecast data

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // find the ListView for the forecasts
        mListView = (ListView)rootView.findViewById(R.id.listview_forecast);

        // string array of the forecast
        String[] forecastArray = {"Today - Sunny - 88/63",
                "Tomorrow - Sunny - 91/63",
                "Wednesday - Sunny - 92/63",
                "Thursday - Sunny - 93/63",
                "Friday - Sunny - 86/63",
                "Saturday - ZOMBIE APOCALYPSE - 86/63",
                "Tomorrow - Sunny - 91/63",
                "Wednesday - Sunny - 92/63",
                "Thursday - Sunny - 93/63",
                "Friday - Sunny - 86/63",
                "Saturday - ZOMBIE APOCALYPSE - 86/63"};

        // put the string array into an ArrayList to pass to the adapter
        ArrayList<String> forecastList = new ArrayList<String>(Arrays.asList(forecastArray));

        // create the list adapter for the forecasts
        listAdapter = new ArrayAdapter<String>(
                                // the current context (parent activity)
                                getActivity(),
                                // the layout ID to modify
                                R.layout.list_item_forecast,
                                // the view ID that will be accessed
                                R.id.list_item_forecast_textview,
                                // the forecast data that will be used
                                forecastList);

        // bind the adapter to the listview
        mListView.setAdapter(listAdapter);

        return rootView;
    }
}
