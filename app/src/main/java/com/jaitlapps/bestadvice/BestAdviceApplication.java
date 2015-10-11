package com.jaitlapps.bestadvice;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jaitlapps.bestadvice.database.FavoriteManager;

public class BestAdviceApplication extends Application {

    @Override
    public void onCreate() {
        // begin add
        try {
            Class.forName("android.os.AsyncTask");
        } catch(Throwable ignore) {
        }
        // end add

        FavoriteManager.getInstance(this);

        super.onCreate();
    }

    public static boolean isFree() {
        return "free".equals(BuildConfig.FLAVOR);
    }

    public static boolean isNetworkConnected(Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return netInfo != null && netInfo.isConnectedOrConnecting();

        } catch (Exception e) {
            return false;
        }
    }
}
