package com.example.minga.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.minga.popularmoviesapp.adapter.MovieAdapter;
import com.example.minga.popularmoviesapp.database.AppDatabase;
import com.example.minga.popularmoviesapp.model.Movie;
import com.example.minga.popularmoviesapp.utils.JsonUtils;
import com.example.minga.popularmoviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.minga.popularmoviesapp.R.*;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    ArrayList<Movie> movies;
    String apiType =   "1";
    URL apiUrl;

    private AppDatabase mDb;

    private static final String LOG_TAG = MainActivity.class.getSimpleName ();
    public static final String KEY_MOVIE = "movie";

    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // used SharedPreference utilized settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences (this);
        // get SharedPreference value
        apiType = sharedPreferences.getString (getString (string.display_type_key),getString (string.display_type_popularity_value));
        // initialize DB
        mDb = AppDatabase.getInstance ((getApplicationContext ()));
        createMoviePosters(apiType);
        // register the Listener
        sharedPreferences.registerOnSharedPreferenceChangeListener (this);

    }

    public void createMoviePosters(String apiType){
        if (Integer.valueOf (apiType) == 1  || Integer.valueOf (apiType)==2) {
            // retrieve the API json
            apiUrl = NetworkUtils.buildApiUrl (Integer.parseInt (apiType));
            // aSyncTask do it in the background thread. Populate the UI in the background as well
            new JsonLoadTask ().execute (apiUrl);
        } else {
            populateUiFavoriteMovie();
        }
    }

    @Override
    protected void onResume(){
        super.onResume ();
        //createMoviePosters (apiType);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy ();
        PreferenceManager.getDefaultSharedPreferences (this).
                unregisterOnSharedPreferenceChangeListener (this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId ();
        if(id == R.id.action_settings){
            Intent startSettingsActivity = new Intent (this, SettingsActivity.class);
            startActivity (startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals (getString (R.string.display_type_key))){
            apiType = sharedPreferences.getString (key, getString (R.string.display_type_popularity_value));
            createMoviePosters(apiType);
        }
    }

    // Create a AsyncTask to run the URL loading process in the background thread
    public class JsonLoadTask extends AsyncTask<URL, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Movies are loading",Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(URL... urls){
            URL apiUrl = urls[0];
            String jsonResults = null;
            try{
                jsonResults = NetworkUtils.getResponseFromHttpUrl (apiUrl);
                // parse the json
                movies = JsonUtils.parseJsonToArrayList (jsonResults);
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
            // make sure Movies are not Null before instantiate the adapter.
            if (movies != null ){
                MovieAdapter adapter = new MovieAdapter (MainActivity.this, movies);
                // GridView
                gridView = findViewById (id.movies_gridview);
                gridView.setAdapter (adapter);

                //click listener
                gridView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // make a parcelables and pass it
                        Movie movie = (Movie) adapterView.getItemAtPosition(position);
                        launchMovieDetailActivity(movie);
                    }
                });

            }
        }
    }

    private  void launchMovieDetailActivity(Movie movie) {
        // context
        Context context = MainActivity.this;
        //  child activity class
        Class destinationActivity = MovieDetailActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        // passing data into child activity.
        intent.putExtra (KEY_MOVIE, movie);
        startActivity (intent);
    }

    public void populateUiFavoriteMovie(){
        Log.d (LOG_TAG,"Retrieving the movies from Database");
        final LiveData<List<Movie>>  favoriteMovies = mDb.favoriteMovieDao().loadAllMovies ();
        favoriteMovies.observe (this, new Observer<List<Movie>> () {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d (LOG_TAG,"Receiving Database update");
                // to keep the popular moves and top rated movies when it is marked as favorite
                if (apiType.equals ("3")){
                    MovieAdapter adapter = new MovieAdapter (MainActivity.this, ( ArrayList<Movie> ) movies);
                    // GridView
                    GridView gridView = findViewById (id.movies_gridview);
                    gridView.setAdapter (adapter);
                    //click listener
                    gridView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            // make a parcelables and pass it
                            Movie favoriteMovie = ( Movie ) adapterView.getItemAtPosition (position);
                            launchMovieDetailActivity (favoriteMovie);
                        }
                    });
                }
            }
        });


    }

}
