package com.example.minga.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minga.popularmoviesapp.adapter.ReviewAdapter;
import com.example.minga.popularmoviesapp.adapter.TrailerAdapter;
import com.example.minga.popularmoviesapp.database.AppDatabase;
import com.example.minga.popularmoviesapp.model.Movie;
import com.example.minga.popularmoviesapp.model.Review;
import com.example.minga.popularmoviesapp.model.Trailer;
import com.example.minga.popularmoviesapp.utils.JsonUtils;
import com.example.minga.popularmoviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView overviewTv;
    private TextView titleTv;
    private TextView originalTitleTV;
    private TextView voteAverageTv;
    private TextView releaseDateTv;

    URL apiUrlTrailer;
    ArrayList<Trailer> trailers;
    URL apiUrlReview;
    ArrayList<Review> reviews;

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName ();
    Button mButton;
    // Member variable for the Database
    private AppDatabase mDb;
    public String movie_id;
    public String imgUrl;
    public String imagePath;

    private String displayType;

    // ********** RecyclerView *****************************************************************
    //RecyclerView rvTrailers;
    //TrailersAdapter rvAdapter;
    //LinearLayoutManager rvTrailerLayoutManager;
    // ********** RecyclerView *****************************************************************

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_movie_detail);

        titleTv = (TextView)findViewById (R.id.title_tv) ;
        ImageView thumbnailIv = (ImageView ) findViewById (R.id.thumbnail_iv);
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
        final Movie movie = (Movie ) data.getParcelable ("movie");

        mButton = (Button ) findViewById (R.id.favorite_btn);
        mDb = AppDatabase.getInstance (getApplicationContext ());
        final LiveData<Movie> mCheckIfExistInDB = mDb.favoriteMovieDao ().findByMovieID (movie.getMovieId ());
        mCheckIfExistInDB.observe (this, new Observer<Movie> () {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if(movie!=null){
                    mButton.setText ("UN-MARK from Favorite List");
                } else {
                    mButton.setText ("MARK AS FAVORITE");
                }
            }
        });

        mButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                onFavoriteButtonClicked(movie);
            }
        });

        if (movie == null) {
            // data unavailable
            closeOnError();
            return;
        }
        populateUI(movie);

        // get the image path: URL or local path
        File file = getApplicationContext ().getFileStreamPath ( movie.getMovieId ()+".png");
        if(file.exists ()){
            imagePath = "file://"+ file.getAbsolutePath ();
        } else {
            imagePath = movie.getThumbnailUrl ();
        }
        Picasso.with(this)
                .load (imagePath)
                .placeholder (R.drawable.load_image_animation)
                .error (R.drawable.ic_android_black_24dp)
                .fit ().centerInside ()
                .into (thumbnailIv);
        setTitle (movie.getMovieTitle ());

        // ********** RecyclerView Setup ***********************************************************
        //rvTrailers = (RecyclerView) findViewById (R.id.trailer_rv);
        //rvTrailers.setLayoutManager (new LinearLayoutManager (this));
        //rvAdapter = new TrailersAdapter (getApplicationContext (),trailers);
        //rvTrailers.setAdapter (rvAdapter);
        // ********** RecyclerView *****************************************************************

        // if it is online mode, populate the trailer and review.
        if (isNetworkAvailable()){
            populateUITrailerReview(movie.getMovieId ());
        }


    }

    @Override
    protected void onResume(){
        super.onResume ();
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

    private void populateUITrailerReview(String movieId){
        //Log.d ("Trailer MovieId Print: ", movieId);
        // retrieve the trailer API json
        apiUrlTrailer = NetworkUtils.buildApiUrlTrailer (movieId) ;
        // retrieve the review API json
        apiUrlReview = NetworkUtils.buildApiUrlReview (movieId) ;
        // aSyncTask do it in the background thread. Populate the UI in the background as well
        new JsonLoadTaskTrailer().execute (apiUrlTrailer,apiUrlReview);
    }


    public void onFavoriteButtonClicked(final Movie movie){
        //Log.d ("Print::--", mButton.getText().toString ());

        if(mButton.getText().toString().equals ("UN-MARK from Favorite List")){
            AppExecutors.getsInstance ().diskIO ().execute (new Runnable () {
                @Override
                public void run() {
                    mDb.favoriteMovieDao ().deleteMovie (movie);
                    finish ();
                }
            });

            // delete the image from local storage
            File file = getApplicationContext ().getFileStreamPath ( movie.getMovieId ()+".png");
            if(file.delete ()) Log.d ("Print::", movie.getMovieId ()+".png is deleted!");
            Toast.makeText (MovieDetailActivity.this, movie.getMovieTitle () + " is un-marked from Favorite list!", Toast.LENGTH_SHORT).show ();
        } else {
            AppExecutors.getsInstance ().diskIO ().execute (new Runnable () {
                @Override
                public void run() {
                    mDb.favoriteMovieDao ().insertMovie (movie);
                    finish ();
                }
            });

            Toast.makeText (MovieDetailActivity.this, movie.getMovieTitle () + " is marked as Favorite!", Toast.LENGTH_SHORT).show ();
            // download and save the image
            movie_id = movie.getMovieId ();
            imgUrl = movie.getImageUrl ();
            new DownloadMoviePoster().execute (imgUrl);
        }
    }

    // Create a AsyncTask to run the URL loading process in the background thread
    public class JsonLoadTaskTrailer extends AsyncTask<URL, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MovieDetailActivity.this,"Movie Trailers are loading",Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(URL... urls){

            URL apiUrlTrailer = urls[0];
            URL apiUrlReview = urls[1];
            String jsonResults = null;
            String jsonReviews = null;
            try{
                jsonResults = NetworkUtils.getResponseFromHttpUrl (apiUrlTrailer);
                jsonReviews = NetworkUtils.getResponseFromHttpUrl (apiUrlReview);
                // parse the json
                trailers = JsonUtils.parseJsonToArrayListTrailer (jsonResults);
                reviews = JsonUtils.parseJsonToArrayListReviews (jsonReviews);
            } catch (IOException e){
                e.printStackTrace ();
            } catch (JSONException e) {
                e.printStackTrace ();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute (result);

            // make sure Trailer are not Null before instantiate the adapter.
            if ( !trailers.isEmpty() || trailers !=null) {
                // ********** RecyclerView *****************************************************************
                //rvAdapter = new TrailersAdapter (trailers);
                //rvAdapter.notifyDataSetChanged ();
                // ********** RecyclerView *****************************************************************

                TrailerAdapter adapter = new TrailerAdapter (MovieDetailActivity.this, trailers);
                // ListView
                ListView listView = (ListView )findViewById (R.id.trailers_listview);
                setListViewHeightBasedOnChildren(listView);

                listView.setAdapter (adapter);
                //click listener
                listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Trailer trailer = (Trailer) adapterView.getItemAtPosition(position);
                        //Log.d ("TrailerId Print: ", trailer.getYoutubeId ());
                        // context
                        Context context = MovieDetailActivity.this;
                        openTrailerVideo (context, trailer.getYoutubeId ());
                    }
                });

            }

            // make sure Reviews are not Null before instantiate the adapter.
            if ( !reviews.isEmpty() || reviews != null) {
                ReviewAdapter adapter = new ReviewAdapter (MovieDetailActivity.this, reviews);
                // ListView
                ListView listView = findViewById (R.id.reviews_listview);
                setListViewHeightBasedOnChildren(listView);
                listView.setAdapter (adapter);
                //click listener

            }
        }
    }

    public void openTrailerVideo(Context context, String youtube_id){
        Intent appIntent = new Intent (Intent.ACTION_VIEW, Uri.parse ("vnd.youtube:"+youtube_id));
        Intent webIntent = new Intent (Intent.ACTION_VIEW, Uri.parse ("http://www.youtube.com/watch?v=" + youtube_id));
        try {
            context.startActivity (appIntent);
        } catch (ActivityNotFoundException ex){
            if(webIntent.resolveActivity (getPackageManager ())!=null){
                context.startActivity (webIntent);
            }
        }
    }

    // save bitmap image
    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception, something wrong when saving bitmap images!");
            e.printStackTrace();
        }
    }

    //AsyncTask for downloading image by URL
    private class DownloadMoviePoster extends AsyncTask<String, Void, Bitmap>{
        private String LOG_TAG = DownloadMoviePoster.class.getSimpleName ();
        private Bitmap DownloadMoviePosterBitmap(String sUrl){
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL (sUrl).openStream ();
                bitmap = BitmapFactory.decodeStream (inputStream);
                inputStream.close ();
            } catch (Exception e){
                Log.d(LOG_TAG, "Exception, something wrong when downloading movie images!");
                e.printStackTrace ();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return DownloadMoviePosterBitmap (strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result){
            saveImage (getApplicationContext (), result, movie_id+".png" );
        }

    }


    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
