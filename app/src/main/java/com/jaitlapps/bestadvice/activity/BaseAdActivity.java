package com.jaitlapps.bestadvice.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.jaitlapps.bestadvice.BuildConfig;
import com.jaitlapps.bestadvice.R;

public class BaseAdActivity extends ActionBarActivity {

    protected AdView mAdView;

    protected void displayAd() {

        mAdView = (AdView) findViewById(R.id.adView);

        if(isFree()) {
            mAdView.loadAd(new AdRequest.Builder().build());
        } else {
            mAdView.setVisibility(View.GONE);
            mAdView = null;
        }
    }

    @Override
    protected void onPause() {
        if(mAdView != null) {
            mAdView.pause();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        resumeAd();
    }

    protected void resumeAd() {
        if(mAdView != null) {

            if(isNetworkConnected()) {
                mAdView.setVisibility(View.VISIBLE);
            } else {
                mAdView.setVisibility(View.GONE);
            }

            if(mAdView.getVisibility() == View.VISIBLE)
                mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if(mAdView != null) {
            mAdView.removeAllViews();
            mAdView.destroy();
        }

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    protected boolean isNetworkConnected() {

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isFree() {
        return BuildConfig.FLAVOR == "free";
    }
}
