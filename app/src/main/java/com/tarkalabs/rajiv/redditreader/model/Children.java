package com.tarkalabs.rajiv.redditreader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajiv on 12/04/16.
 */
public class Children {

    @SerializedName("data")
    @Expose
    private ReaderDetail data;

    /**
     * @return The data
     */
    public ReaderDetail getData() {
        return data;
    }


}
