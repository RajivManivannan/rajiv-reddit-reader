package com.tarkalabs.rajiv.redditreader.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tarkalabs.rajiv.redditreader.R;
import com.tarkalabs.rajiv.redditreader.app.AppConstants;

public class WebViewActivity extends AppCompatActivity {

    private View progressView;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String title = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_TITLE);
        String url = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_URL);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        progressView = findViewById(R.id.loading_progress);
        webView = (WebView) findViewById(R.id.web_web_view);

        //set the web view client
        webView.setWebViewClient(new WebViewerWebViewClient());
        // enable the javascript
        webView.getSettings().setJavaScriptEnabled(true);
        showProgress(true);
        //load url.
        webView.loadUrl(url);


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
        }
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
            // load the url in the webview
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
