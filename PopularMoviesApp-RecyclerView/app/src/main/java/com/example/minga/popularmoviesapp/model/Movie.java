package com.example.minga.popularmoviesapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by minga on 5/7/2018.
 */
@Entity(tableName="Movies")
public class Movie implements Parcelable{
    private String movieTitle;
    private String imageUrl;
    private double moviePopularity;
    //movie details
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String releaseDate;
    private String thumbnailUrl ;
    //for trailer
    @PrimaryKey
    @NonNull
    private String movieId;

    public Movie(String movieTitle, String imageUrl, double moviePopularity,
                 String originalTitle, String overview, double voteAverage, String releaseDate,
                 String thumbnailUrl, String movieId){
        this.movieTitle = movieTitle;
        this.imageUrl = imageUrl;
        this.moviePopularity = moviePopularity;

        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.thumbnailUrl = thumbnailUrl;

        this.movieId = movieId;
    }

    public String getMovieTitle(){ return  movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getImageUrl() {return  imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public double getMoviePopularity() { return moviePopularity;}
    public void setMoviePopularity(double moviePopularity){ this.moviePopularity = moviePopularity; }

    public String getOriginalTitle(){return originalTitle;}
    public void setOriginalTitle(String originalTitle){this.originalTitle = originalTitle;}
    public String getOverview(){return  overview; }
    public void  setOverview(String overview){this.overview = overview;}
    public double getVoteAverage(){return voteAverage; }
    public void setVoteAverage(double voteAverage){this.voteAverage = voteAverage;}
    public String getReleaseDate(){return releaseDate; }
    public void setReleaseDate(String releaseDate){this.releaseDate = releaseDate; }
    public String getThumbnailUrl(){return thumbnailUrl;}
    public void setThumbnailUrl(String thumbnailUrl){this.thumbnailUrl = thumbnailUrl; }

    public String getMovieId(){return movieId;}
    public void setMovieId(String movieId){this.movieId = movieId; }

    // Parcelling part
    public Movie(Parcel in){
        this.movieTitle = in.readString ();
        this.moviePopularity = in.readDouble ();
        this.imageUrl = in.readString ();
        this.originalTitle = in.readString ();
        this.overview = in.readString();
        this.voteAverage =  in.readDouble ();
        this.releaseDate = in.readString ();
        this.thumbnailUrl = in.readString ();

        this.movieId = in.readString ();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString (this.movieTitle);
        parcel.writeDouble (this.moviePopularity);
        parcel.writeString (this.imageUrl);
        parcel.writeString (this.originalTitle);
        parcel.writeString (this.overview);
        parcel.writeDouble (this.voteAverage);
        parcel.writeString (this.releaseDate);
        parcel.writeString (this.thumbnailUrl);

        parcel.writeString (this.movieId);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + movieTitle + '\'' +
                ", popularity='" + moviePopularity + '\'' +
                ", poster_path='" + imageUrl + '\'' +
                ", original_title='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average='" + voteAverage + '\'' +
                ", release_date='" + releaseDate + '\'' +
                ", thumbnail_path='" + thumbnailUrl + '\'' +
                ", id='" + movieId + '\'' +
                '}';
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
