package com.tarkalabs.rajiv.redditreader.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.app.AppConstants;
import com.tarkalabs.rajiv.redditreader.presenter.DetailPresenter;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private RelativeLayout noInternetLayout;
    private View progressView;
    private WebView webView;

    private String url;

    private DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String title = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_TITLE);
        url = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressView = findViewById(R.id.detail_loading_progress);
        webView = (WebView) findViewById(R.id.detail_web_view);
        noInternetLayout = (RelativeLayout) findViewById(R.id.no_internet_layout);
        TextView retry = (TextView) findViewById(R.id.no_internet_try_again_tv);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPresenter.loadUrl();
            }
        });

        detailPresenter = new DetailPresenter(this, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        detailPresenter.loadUrl();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean isDestroyed() {
        detailPresenter.onDestroy();
        return super.isDestroyed();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            webView.setVisibility(show ? View.GONE : View.VISIBLE);
            webView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    webView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            webView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void loadUrl() {
        noInternetLayout.setVisibility(View.GONE);
        if (webView != null) {
            //set the web view client
            webView.setWebViewClient(new WebViewerWebViewClient());
            //load url.
            webView.loadUrl(url);
            showProgress(true);
        }
    }

    @Override
    public void noInternet() {
        webView.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }


    /**
     * WebViewerWebViewClient.java
     * <p/>
     * To listen the loading process of the content in the web view.
     */
    private class WebViewerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            showProgress(true);
            // load the url in the web view
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // hide the progress indicator
            showProgress(false);
            super.onPageFinished(view, url);
        }
    }
}
