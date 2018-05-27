package com.example.minga.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by minga on 5/7/2018.
 */



public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG= MovieAdapter.class.getSimpleName();

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        super(context,0,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Movie movie = getItem(position);

        if (convertView == null) {
            // List item layout
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
            // Grid item Layout
            convertView = LayoutInflater.from (getContext ()).inflate (R.layout.grid_item_movie, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_poster_iv);
        //iconView.setImageResource(movie.movieImage);
        Picasso.with (getContext ())
                .load (movie.getImageUrl ())
                .placeholder (R.drawable.load_image_animation)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (iconView);
        //setTitle (movie.getMovieTitle ());

        TextView movieTitleView = (TextView) convertView.findViewById(R.id.movie_name_tv);
        //movieTitleView.setText(movie.getMovieTitle ());
        //movieTitleView.setText(String.valueOf(movie.getVoteAverage ()));

        return convertView;
    }
}
