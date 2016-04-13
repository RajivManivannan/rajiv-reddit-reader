package com.tarkalabs.rajiv.redditreader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajiv on 12/04/16.
 */
public class Data {

    @SerializedName("children")
    @Expose
    private List<Children> children = new ArrayList<>();

    /**
     * @return The children
     */
    public List<Children> getChildren() {
        return children;
    }

}