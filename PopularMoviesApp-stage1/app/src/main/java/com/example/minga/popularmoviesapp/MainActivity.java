package com.example.minga.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.minga.popularmoviesapp.model.Movie;
import com.example.minga.popularmoviesapp.utils.JsonUtils;
import com.example.minga.popularmoviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.minga.popularmoviesapp.R.*;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    ArrayList<Movie> movies;
    String apiType = "1";
    URL apiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        // used SharedPreference utilized settings
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences (this);
        // get SharedPreference value
        apiType = sharedPreferences.getString (getString (string.display_type_key),getString (string.display_type_popularity_value));
        createMoviePosters(apiType);
        // register the Listener
        sharedPreferences.registerOnSharedPreferenceChangeListener (this);
    }

    public void createMoviePosters(String apiType){
        //Log.d ("SharePreference Print: ", apiType);
        // retrieve the API json
        apiUrl = NetworkUtils.buildApiUrl (Integer.parseInt (apiType)) ;
        // aSyncTask do it in the background thread. Populate the UI in the background as well
        new JsonLoadTask().execute (apiUrl);
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
                GridView gridView = findViewById (id.movies_gridview);
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
        //intent.putExtra (MovieDetailActivity.EXTRA_POSITION, movie);
        intent.putExtra ("movie", movie);
        startActivity (intent);
    }
}
