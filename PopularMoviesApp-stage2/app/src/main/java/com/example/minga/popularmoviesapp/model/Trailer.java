package com.example.minga.popularmoviesapp.model;

import java.util.ArrayList;

/**
 * Created by minga on 6/6/2018.
 */

public class Trailer {
    private String youtubeId;
    private String name;

    public Trailer(String mYoutubeId, String mName){
        this.youtubeId = mYoutubeId;
        this.name = mName;
    }

    public String getYoutubeId(){return youtubeId;}
    public void setYoutubeId(String youtubeId){this.youtubeId = youtubeId; }
    public String getName(){return name;}
    public void setName(String name){this.name = name; }

    /*
    public static ArrayList<Trailer> createTrailersList(int numTrailers){
        ArrayList<Trailer> trailers = new ArrayList<Trailer>();
        for(int i=1; i<=numTrailers; i++){
            trailers.add(new Trailer());
        }
        return trailers;
    }*/
}
