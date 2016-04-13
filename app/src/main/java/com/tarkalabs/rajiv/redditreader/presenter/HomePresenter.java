package com.tarkalabs.rajiv.redditreader.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.tarkalabs.rajiv.redditreader.database.DatabaseHandler;
import com.tarkalabs.rajiv.redditreader.model.Children;
import com.tarkalabs.rajiv.redditreader.model.ReaderModel;
import com.tarkalabs.rajiv.redditreader.network.ApiManager;
import com.tarkalabs.rajiv.redditreader.network.ApiService;
import com.tarkalabs.rajiv.redditreader.view.activity.HomeView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rajiv M on 13/04/16.
 */
public class HomePresenter implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private HomeView homeView;

    public HomePresenter(Context context, HomeView homeView) {
        this.context = context;
        this.homeView = homeView;
        new DatabaseHandler(context);
        homeView.init(this);
    }

    public void fetchData() {
        if (homeView != null)
            homeView.showProgress();
        ApiManager apiManager = new ApiManager();
        ApiService apiService = apiManager.getApiManager();
        apiService.getReaderCollection(new Callback<ReaderModel>() {
            @Override
            public void success(ReaderModel readerModel, Response response) {
                if (homeView != null) {
                    homeView.hideProgress();
                    insertData(readerModel.getData().getChildren());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (homeView != null)
                    homeView.hideProgress();
            }
        });
    }

    public void onDestroy() {
        homeView = null;
    }


    private void insertData(List<Children> childrenList) {
        //Clear existing data
        DatabaseHandler.getInstance().clearTable();
        for (Children children : childrenList) {
            DatabaseHandler.getInstance().insertReaderData(children.getData());
        }
        homeView.refreshLoader(this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context) {
            @Override
            public Cursor loadInBackground() {
                return DatabaseHandler.getInstance().getReaderData();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getColumnCount() != 0 && homeView != null)
            homeView.updateData(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (homeView != null)
            homeView.updateData(null);
    }
}
