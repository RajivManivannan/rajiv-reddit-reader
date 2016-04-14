package com.tarkalabs.rajiv.redditreader.presenter;

import android.content.Context;

import com.tarkalabs.rajiv.redditreader.utils.NetworkUtils;
import com.tarkalabs.rajiv.redditreader.view.activity.DetailView;

/**
 * Created by Rajiv M on 14/04/16.
 */
public class DetailPresenter {

    private Context context;
    private DetailView detailView;

    public DetailPresenter(Context context, DetailView detailView) {
        this.context = context;
        this.detailView = detailView;
    }

    public void loadUrl() {
        if (NetworkUtils.isConnected(context)) {
            if (detailView != null)
                detailView.loadUrl();
        } else if (detailView != null)
            detailView.noInternet();
    }

    public void onDestroy() {
        detailView = null;
    }

}
