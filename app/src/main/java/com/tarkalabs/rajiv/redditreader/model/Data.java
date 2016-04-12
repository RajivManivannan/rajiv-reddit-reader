package com.tarkalabs.rajiv.redditreader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vagmi on 12/04/16.
 */
public class Data {

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("children")
    @Expose
    private List<Child> children = new ArrayList<>();

    /**
     *
     * @return
     * The modhash
     */
    public String getModhash() {
        return modhash;
    }

    /**
     *
     * @param modhash
     * The modhash
     */
    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    /**
     *
     * @return
     * The children
     */
    public List<Child> getChildren() {
        return children;
    }

    /**
     *
     * @param children
     * The children
     */
    public void setChildren(List<Child> children) {
        this.children = children;
    }

}