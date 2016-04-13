package com.tarkalabs.rajiv.redditreader.view.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.app.AppConstants;
import com.tarkalabs.rajiv.redditreader.database.ReaderContract;
import com.tarkalabs.rajiv.redditreader.presenter.HomePresenter;
import com.tarkalabs.rajiv.redditreader.view.adapter.ReaderListAdapter;

public class HomeActivity extends AppCompatActivity implements HomeView, AdapterView.OnItemClickListener {

    //UI
    private SwipeRefreshLayout swipeRefreshLayout;
    //Other class
    private ReaderListAdapter readerListAdapter;
    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.home_reader_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresenter.fetchData();
            }
        });
        readerListAdapter = new ReaderListAdapter(HomeActivity.this, null, 0);
        listView.setAdapter(readerListAdapter);
        listView.setOnItemClickListener(this);
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
    public void updateData(Cursor cursor) {
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
            Intent webViewIntent = new Intent(HomeActivity.this, WebViewActivity.class);
            webViewIntent.putExtra(AppConstants.INTENT_EXTRA_TITLE, title);
            webViewIntent.putExtra(AppConstants.INTENT_EXTRA_URL, url);
            startActivity(webViewIntent);
        }
    }
}

