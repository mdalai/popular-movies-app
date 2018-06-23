package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.database.FavoriteMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minga on 6/18/2018.
 */

public class FavoriteMovieAdapter extends ArrayAdapter<FavoriteMovie> {
    private static final String LOG_TAG= FavoriteMovieAdapter.class.getSimpleName();

    public FavoriteMovieAdapter(Context context, List<FavoriteMovie> favoriteMovies){
        super(context,0,favoriteMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        FavoriteMovie favoriteMovie = getItem(position);

        if (convertView == null) {
            // Grid item Layout
            convertView = LayoutInflater.from (getContext ()).inflate (R.layout.grid_item_movie, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_poster_iv);
        Picasso.with (getContext ())
                .load (favoriteMovie.getImageUrl ())
                .placeholder (R.drawable.load_image_animation)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (iconView);
        //setTitle (favoriteMovie.getMovieTitle ());

        TextView movieTitleView = (TextView) convertView.findViewById(R.id.movie_name_tv);
        movieTitleView.setText(favoriteMovie.getMovieTitle ());

        return convertView;
    }
}
