package com.jaitlapps.bestadvice;

import android.app.Application;

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
}
