package com.tarkalabs.rajiv.redditreader.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.adapter.ReaderListAdapter;
import com.tarkalabs.rajiv.redditreader.model.Child;
import com.tarkalabs.rajiv.redditreader.model.Data_;
import com.tarkalabs.rajiv.redditreader.model.ReaderModel;
import com.tarkalabs.rajiv.redditreader.network.ApiManager;
import com.tarkalabs.rajiv.redditreader.network.ApiService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private ApiService apiService;
    private ReaderListAdapter readerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view) ;
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        readerListAdapter = new ReaderListAdapter(new ArrayList<Child>());
        recyclerView.setAdapter(readerListAdapter);

        apiManager = new ApiManager();
        apiService = apiManager.getApiManager();

        apiService.getReaderCollection(new Callback<ReaderModel>() {
            @Override
            public void success(ReaderModel readerModel, Response response) {
                readerListAdapter.setItems(readerModel.getData().getChildren());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MAINACTIVITY", "retro fit has failed " + error);
            }
        });


    }
}
