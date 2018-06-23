package com.example.minga.popularmoviesapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.minga.popularmoviesapp.model.Movie;

/**
 * Created by minga on 6/13/2018.
 */
@Database(entities ={Movie.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName ();
    private static final Object LOCK = new Object (); //make sure an object instantiates only once
    private static final String DATABASE_NAME = "movielist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d (LOG_TAG, "Creating new database instance");
                sInstance= Room.databaseBuilder (context.getApplicationContext (),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        // Queries should be done in a separate thread to avoid locking the UI
                        // this is only TEMPORALLY to see our DB implementation
                        //.allowMainThreadQueries ()
                        .build ();
            }
        }
        Log.d (LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();
}
