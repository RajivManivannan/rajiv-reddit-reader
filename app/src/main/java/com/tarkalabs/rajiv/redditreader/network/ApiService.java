package com.tarkalabs.rajiv.redditreader.network;

import com.tarkalabs.rajiv.redditreader.model.ReaderModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by vagmi on 12/04/16.
 */
public interface ApiService {

    @GET("/hot.json")
    void getReaderCollection(Callback<ReaderModel> callback);

}