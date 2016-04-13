package com.tarkalabs.rajiv.redditreader.view.activity;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

/**
 * Created by Rajiv M on 13/04/16.
 */
public interface HomeView {

    void showProgress();

    void hideProgress();

    void updateData(Cursor cursor);

    void init(LoaderManager.LoaderCallbacks callback);

    void refreshLoader(LoaderManager.LoaderCallbacks callback);

}
