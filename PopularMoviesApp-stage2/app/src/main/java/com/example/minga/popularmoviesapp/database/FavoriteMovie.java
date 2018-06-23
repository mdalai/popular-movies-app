package com.example.minga.popularmoviesapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by minga on 6/12/2018.
 */

@Entity(tableName="Movies")
public class FavoriteMovie implements Parcelable{
    @PrimaryKey
    @NonNull
    private String movieId;
    private Integer favorite;  // 1,2,3  - 3 is most favorite.
    private String movieTitle;
    private double moviePopularity;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String releaseDate;
    private String imageUrl;
    private String thumbnailUrl;

    public FavoriteMovie(String movieTitle, double moviePopularity,
                 String originalTitle, String overview, double voteAverage, String releaseDate,
                 String movieId, Integer favorite,
                         String imageUrl, String thumbnailUrl){
        this.movieTitle = movieTitle;
        this.moviePopularity = moviePopularity;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.favorite = favorite;

        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMovieTitle(){ return  movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
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
    public String getMovieId(){return movieId;}
    public void setMovieId(String movieId){this.movieId = movieId; }
    public Integer getFavorite(){return favorite;}
    public void setFavorite(Integer favorite){this.favorite = favorite; }

    public String getImageUrl() {return  imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getThumbnailUrl(){return thumbnailUrl;}
    public void setThumbnailUrl(String thumbnailUrl){this.thumbnailUrl = thumbnailUrl; }


    // Parcelling part
    public FavoriteMovie(Parcel in){
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
        public FavoriteMovie createFromParcel(Parcel in) {
            return new FavoriteMovie (in);
        }

        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };
}
