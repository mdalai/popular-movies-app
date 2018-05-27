package com.example.minga.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minga.popularmoviesapp.model.MovieDetail;
import com.example.minga.popularmoviesapp.model.Movie;
import com.example.minga.popularmoviesapp.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView overviewTv;
    private TextView titleTv;
    private TextView originalTitleTV;
    private TextView voteAverageTv;
    private TextView releaseDateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_movie_detail);

        titleTv = (TextView)findViewById (R.id.title_tv) ;
        ImageView thumbnailIv = findViewById (R.id.thumbnail_iv);
        overviewTv = (TextView)findViewById (R.id.overview_tv);
        originalTitleTV = (TextView)findViewById (R.id.originalTitle_tv);
        voteAverageTv = (TextView)findViewById (R.id.voteAverage_tv);
        releaseDateTv = (TextView)findViewById (R.id.releaseDate_tv);

        Intent intent = getIntent ();
        if(intent == null) {
            closeOnError();
            return;
        }

        // get the Movie parcelable
        Bundle data = intent.getExtras ();
        Movie movie = (Movie ) data.getParcelable ("movie");

        if (movie == null) {
            // data unavailable
            closeOnError();
            return;
        }

        populateUI(movie);
        Picasso.with(this)
                .load (movie.getImageUrl ())
                .placeholder (R.drawable.load_image_animation)
                .error (R.drawable.ic_android_black_24dp)
                .fit ().centerInside ()
                .into (thumbnailIv);
        setTitle (movie.getMovieTitle ());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Movie movie){
        titleTv.setText (movie.getMovieTitle ());
        originalTitleTV.setText (movie.getOriginalTitle ());
        voteAverageTv.setText (String.valueOf (movie.getVoteAverage ()));
        releaseDateTv.setText (movie.getReleaseDate ());
        overviewTv.setText (movie.getOverview ());
    }
}
