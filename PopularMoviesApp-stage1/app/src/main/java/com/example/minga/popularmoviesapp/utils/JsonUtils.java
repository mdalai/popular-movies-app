package com.example.minga.popularmoviesapp.utils;

import android.util.Log;

import com.example.minga.popularmoviesapp.model.Movie;
import com.example.minga.popularmoviesapp.model.MovieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minga on 5/13/2018.
 */

public class JsonUtils {
    private final static String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185";
    private final static String BASE_URL_THUMBNAIL = "http://image.tmdb.org/t/p/w92";

    public static ArrayList<Movie> parseJsonToArrayList(String json) throws JSONException{
        ArrayList<Movie> movies = new ArrayList<Movie> ();

        JSONObject jsonObject = new JSONObject (json);
        JSONArray results = jsonObject.getJSONArray ("results");
        for (int i=0; i < results.length (); i++){
            JSONObject r = results.getJSONObject (i);
            String posterUrl = r.getString ("poster_path");
            String title = r.getString ("title");
            double popularity = r.getDouble ("popularity");
            String originalTitle = r.getString ("original_title");
            String overview = r.getString ("overview");
            double voteAverage = r.getDouble ("vote_average");
            String releaseDate = r.getString ("release_date");
            movies.add (new Movie (title, BASE_URL_IMG + posterUrl, popularity,
                    originalTitle,overview,voteAverage,releaseDate,BASE_URL_THUMBNAIL+posterUrl));
        }
       return movies;
    }
}
