package com.tarkalabs.rajiv.redditreader.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.tarkalabs.rajiv.redditreader.model.Child;
import com.tarkalabs.rajiv.redditreader.model.ReaderModel;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;

/**
 * Created by vagmi on 12/04/16.
 */
public interface ApiService {

    @GET("/hot.json")
    void getReaderCollection(Callback<ReaderModel> callback);

}