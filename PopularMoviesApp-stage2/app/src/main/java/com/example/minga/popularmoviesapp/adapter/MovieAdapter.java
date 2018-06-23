package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by minga on 5/7/2018.
 */



public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG= MovieAdapter.class.getSimpleName();
    public String imagePath;

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

        // get the image path: URL or local path
        File file = getContext ().getFileStreamPath ( movie.getMovieId ()+".png");
        if(file.exists ()){
            imagePath = "file://"+ file.getAbsolutePath ();
        } else {
            imagePath = movie.getImageUrl ();
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_poster_iv);
        Picasso.with (getContext ())
                .load (imagePath)
                .placeholder (R.drawable.load_image_animation)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (iconView);
        //setTitle (movie.getMovieTitle ());

        TextView movieTitleView = (TextView) convertView.findViewById(R.id.movie_name_tv);
        //movieTitleView.setText(movie.getMovieTitle ());



        return convertView;
    }
}
