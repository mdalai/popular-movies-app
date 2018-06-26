package com.example.minga.popularmoviesapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.EOFException;
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
    final static String KEY = "3f29784737416bf3bc6362b1dda46c31";
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

    // build URL - Trailer
    public static URL buildApiUrlTrailer(String movieId){
        API_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key="+KEY;

        Uri builtApiUri = Uri.parse (API_URL).buildUpon ().build();
        URL apiUrl = null;
        try{
            apiUrl = new URL (builtApiUri.toString ());
        } catch (MalformedURLException e){
            e.printStackTrace ();
        }
        return  apiUrl;
    }

    // build URL - Review
    public static URL buildApiUrlReview(String movieId){
        API_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key="+KEY;

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

    // check if URL is available
    public static boolean isUrlAvailable() {
        boolean available = false;
        String web_url = "https://www.themoviedb.org/";
        //Uri uri = Uri.parse (web_url).buildUpon ().build ();
        URL url;
        HttpURLConnection connection = null;
        try {
            //url = new URL (uri.toString ());
            url = new URL(web_url);
            connection = (HttpURLConnection) url.openConnection ();
            int code = connection.getResponseCode ();
            Log.d ("Print 2", String.valueOf (code));
            if (code == 200) {
                available = true;
            } else {
            }
        } catch (MalformedURLException e) {
            Log.d ("Print 1","Hello");
            e.printStackTrace ();
        } catch (EOFException e){
            Log.d ("Print 3","Hello");
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return available;
    }



}
