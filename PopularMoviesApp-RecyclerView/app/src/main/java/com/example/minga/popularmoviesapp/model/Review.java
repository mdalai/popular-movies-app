package com.example.minga.popularmoviesapp.model;

/**
 * Created by minga on 6/8/2018.
 */

public class Review {

    private String author;
    private String content;
    private String id;

    public Review(String mAuthor, String mContent, String mId){
        this.author = mAuthor;
        this.content = mContent;
        this.id = mId;
    }

    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author; }
    public String getContent(){return content;}
    public void setContent(String content){this.content = content; }
    public String getId(){return id; }
    public void setId(String id){this.id = id; }
}
