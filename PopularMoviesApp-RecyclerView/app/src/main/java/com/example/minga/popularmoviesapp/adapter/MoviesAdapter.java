package com.example.minga.popularmoviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minga.popularmoviesapp.MovieDetailActivity;
import com.example.minga.popularmoviesapp.R;
import com.example.minga.popularmoviesapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by minga on 6/24/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>
{
    private Context context;
    private String imagePath;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterIv;
        //public TextView titleTv;
        public ViewHolder(View itemView){
            super(itemView);
            //titleTv=(TextView)itemView.findViewById (R.id.movie_name_tv);
            posterIv=(ImageView)itemView.findViewById (R.id.movie_poster_iv);
        }
    }

    private ArrayList<Movie> mMovies;
    public MoviesAdapter(Context c, ArrayList<Movie> movies){
        this.context = c;
        mMovies = movies;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext ();
        LayoutInflater inflater = LayoutInflater.from (context);

        View movieView = inflater.inflate (R.layout.grid_item_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder (movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        final Movie movie = mMovies.get (position);

        //TextView textView = holder.titleTv;
        //textView.setText (movie.getMovieTitle ());

        // get the image path: URL or local path
        File file = context.getFileStreamPath ( movie.getMovieId ()+".png");
        if(file.exists ()){
            imagePath = "file://"+ file.getAbsolutePath ();
        } else {
            imagePath = movie.getImageUrl ();
        }

        ImageView imageView = holder.posterIv;
        Picasso.with (holder.itemView.getContext ())
                .load (imagePath)
                .placeholder (R.drawable.load_image_animation)
                .error (R.drawable.ic_android_black_24dp)
                .fit ()
                .into (imageView);

        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                launchMovieDetailActivity(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void updateRvAdapter(ArrayList<Movie> jMovies){
        if(mMovies != null){
            mMovies.clear ();
            mMovies.addAll(jMovies);
        } else{
            mMovies = jMovies;
        }
        notifyDataSetChanged ();
    }

    private  void launchMovieDetailActivity(Movie movie) {
        //  child activity class
        Class destinationActivity = MovieDetailActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        // passing data into child activity.
        intent.putExtra ("movie", movie);
        context.startActivity (intent);
    }

}
