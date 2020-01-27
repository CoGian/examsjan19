package com.example.examsjan19;

public class Movie {

    private String originalTitle;
    private String title;
    private String description;
    private String url;
    private String release_date ;
    private String average_score ;

    public Movie(String originalTitle, String title, String description, String url, String release_date, String average_score) {
        this.originalTitle = originalTitle;
        this.title = title;
        this.description = description;
        this.url = url;
        this.release_date = release_date;
        this.average_score = average_score;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getAverage_score() {
        return average_score;
    }

    public String toString(){
        return title +" - "+ description +" - "+ url;
    }

}