package com.tarkalabs.rajiv.redditreader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajiv on 12/04/16.
 */
public class ReaderDetail {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("domain")
    @Expose
    private String domain;

    @SerializedName("subreddit")
    @Expose
    private String subreddit;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("score")
    @Expose
    private Integer score;

    @SerializedName("num_comments")
    @Expose
    private Integer numComments;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("url")
    @Expose
    private String url;


    public String getDomain() {
        return domain;
    }

    public String getSubReddit() {
        return subreddit;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
