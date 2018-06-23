package com.example.minga.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by minga on 5/9/2018.
 */
/*
public class MainActivityFragment extends Fragment {
    private MovieAdapter movieAdapter;

    Movie[] movies = {
                new Movie("Fifty Shades Freed",6.8,R.drawable.fifty_shades_freed),
                new Movie("Avengers: Infinity War", 8, R.drawable.avengers),
                new Movie("Zootopia", 7, R.drawable.zootopia),
                new Movie("Rampage", 6.5, R.drawable.rampage)
        };

    public MainActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(movies));

        ListView listView = (ListView) rootView.findViewById(R.id.listview_movie);
        listView.setAdapter(movieAdapter);

        return rootView;
    }
}
*/