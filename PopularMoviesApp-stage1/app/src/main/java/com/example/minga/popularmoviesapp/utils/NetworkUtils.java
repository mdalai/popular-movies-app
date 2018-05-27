package com.example.minga.popularmoviesapp.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by minga on 5/18/2018.
 */

public class NetworkUtils {
    final static String KEY = "API_KEY";
    final static String API_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key="+KEY;
    final static String API_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key="+KEY;
    private static String API_URL;

    // build URL
    public static URL buildApiUrl(int api_type){
        if (api_type == 1) {  // popular api
            API_URL = API_URL_POPULAR;
        } else {
            API_URL = API_URL_TOP_RATED;
        }

        Uri builtApiUri = Uri.parse (API_URL).buildUpon ().build();
        URL apiUrl = null;
        try{
            apiUrl = new URL (builtApiUri.toString ());
        } catch (MalformedURLException e){
            e.printStackTrace ();
        }
        return  apiUrl;
    }

    // http response
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
        try{
            InputStream inputStream = urlConnection.getInputStream ();
            Scanner scanner = new Scanner (inputStream);
            scanner.useDelimiter ("\\A");
            boolean hasInput = scanner.hasNext ();
            if(hasInput){
                return scanner.next ();
            } else{
                return null;
            }
        } finally {
            urlConnection.disconnect ();
        }
    }

}
