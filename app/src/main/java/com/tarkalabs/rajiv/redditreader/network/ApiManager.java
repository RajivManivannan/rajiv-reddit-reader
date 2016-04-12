package com.tarkalabs.rajiv.redditreader.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by vagmi on 12/04/16.
 */
public class ApiManager {

    /* Webservice URL */
    private static String API_URL = "https://www.reddit.com/";

    /**
     * ApiService with using GSON Converter
     *
     * @return ApiService
     */
    public ApiService getApiManager() {
        Gson gson = new GsonBuilder().create();
        ApiService apiService = new RestAdapter.Builder()
                .setEndpoint(API_URL) // API Endpoint url
                .setClient(new OkClient(new OkHttpClient())) // Http client -OkHttpClient
                .setConverter(new GsonConverter(gson)) // Gson response converter
//                .setLogLevel(RestAdapter.LogLevel.FULL)// To enable log.
                .build()
                .create(ApiService.class);
        return apiService;
    }

}
