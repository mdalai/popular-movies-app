package com.example.minga.popularmoviesapp.model;

import java.util.List;

/**
 * Created by minga on 5/13/2018.
 */

public class MovieDetail {
    private String movieName;
    private String overview;
    private String novel;
    private String screenplay;
    private String director;
    private String image;
    //private List<String> ingredients = null;

    public MovieDetail(String movieName, String overview, String novel, String screenplay, String director, String image){
        this.movieName = movieName;
        this.overview = overview;
        this.novel = novel;
        this.screenplay = screenplay;
        this.director = director;
        this.image = image;
    }

    public String getMovieName() {return  movieName; }
    public void setMovieName(String movieName){this.movieName=movieName; }
    public String getOverview(){ return  overview; }
    public void setOverview(String overview) {this.overview=overview;}
    public String getNovel(){ return  novel; }
    public void setNovel(String novel) {this.novel=novel;}
    public String getScreenplay(){ return  screenplay; }
    public void setScreenplay(String screenplay) {this.screenplay=screenplay;}
    public String getDirector(){ return  director; }
    public void setDirector(String director) {this.director=director;}
    public String getImage(){ return  image; }
    public void setImage(String image) {this.image=image;}


}
