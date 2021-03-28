package com.example.newsapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsItem {

    private String sectionName;
    private String publicationDate;
    private String title;
    private String url;
    private String author;

    public NewsItem( String sectionName, String title, String url, String publicationDate, String author){

        this.sectionName = sectionName;
        this.title = title;
        this.url = url;
        this.publicationDate = publicationDate;
        this.author = author;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }

    public String getSectionName(){
        return  sectionName;
    }

    public String getAuthor() { return author; }

    public String getPublicationDate(){
        return publicationDate;
    }
}
