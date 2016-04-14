package com.tarkalabs.rajiv.redditreader.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.app.AppConstants;
import com.tarkalabs.rajiv.redditreader.database.ReaderContract;
import com.tarkalabs.rajiv.redditreader.presenter.HomePresenter;
import com.tarkalabs.rajiv.redditreader.view.adapter.ReaderListAdapter;

public class HomeActivity extends AppCompatActivity implements HomeView, AdapterView.OnItemClickListener {

    //UI
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout noInternetLayout;
    //Other class
    private ReaderListAdapter readerListAdapter;
    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.home_root_layout);
        noInternetLayout = (RelativeLayout) findViewById(R.id.no_internet_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresenter.fetchData();
            }
        });

        ListView listView = (ListView) findViewById(R.id.home_reader_list_view);
        readerListAdapter = new ReaderListAdapter(HomeActivity.this, null, 0);
        listView.setAdapter(readerListAdapter);
        listView.setOnItemClickListener(this);
        TextView retry = (TextView) findViewById(R.id.no_internet_try_again_tv);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.fetchData();
            }
        });
        // home presenter instance
        homePresenter = new HomePresenter(this, this);
        homePresenter.fetchData();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showMessage(int resId) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, resId, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        homePresenter.fetchData();
                    }
                });
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.show();
    }

    @Override
    public void noInternet() {
        swipeRefreshLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListView() {
        noInternetLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateData(Cursor cursor) {
        noInternetLayout.setVisibility(View.GONE);
        readerListAdapter.swapCursor(cursor);
    }

    @Override
    public void init(LoaderManager.LoaderCallbacks callback) {
        getSupportLoaderManager().initLoader(0, null, callback);
    }

    @Override
    protected void onDestroy() {
        homePresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void refreshLoader(LoaderManager.LoaderCallbacks callback) {
        getSupportLoaderManager().restartLoader(0, null, callback);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        if (cursor.moveToPosition(position)) {
            String title = cursor.getString(cursor.getColumnIndex(ReaderContract.ReaderTable.COLUMN_TITLE));
            String url = cursor.getString(cursor.getColumnIndex(ReaderContract.ReaderTable.COLUMN_URL));
            Intent webViewIntent = new Intent(HomeActivity.this, DetailActivity.class);
            webViewIntent.putExtra(AppConstants.INTENT_EXTRA_TITLE, title);
            webViewIntent.putExtra(AppConstants.INTENT_EXTRA_URL, url);
            startActivity(webViewIntent);
        }
    }
}

