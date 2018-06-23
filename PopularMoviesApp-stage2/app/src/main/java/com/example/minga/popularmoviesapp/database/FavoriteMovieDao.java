package com.example.minga.popularmoviesapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.minga.popularmoviesapp.model.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by minga on 6/12/2018.
 */
@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Query ("SELECT * FROM movies WHERE movieId =:movieId")
    LiveData<Movie> findByMovieID(String movieId);

    @Insert
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

}
