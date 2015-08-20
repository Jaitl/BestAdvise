package com.jaitlapps.bestadvice;

import android.app.Application;

public class BestAdviceApplication extends Application {

    @Override
    public void onCreate() {
        // begin add
        try {
            Class.forName("android.os.AsyncTask");
        } catch(Throwable ignore) {
        }
        // end add

        super.onCreate();
    }
}
