package com.jaitlapps.bestadvice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.jaitlapps.bestadvice.ContentRender;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.database.FavoriteManager;
import com.jaitlapps.bestadvice.domain.RecordEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class DisplayContentActivity extends BaseAdActivity {

    private RecordEntry currentRecord;
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_content);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        WebView web = (WebView) findViewById(R.id.webview);
        addImageClickHandler(web);

        String jsonRecord = intent.getStringExtra(TabsActivity.JSON_RECORDENTRY);
        Gson gson = new Gson();
        RecordEntry recordEntry = gson.fromJson(jsonRecord, RecordEntry.class);
        currentRecord = recordEntry;

        String content = readContent(recordEntry.getPathToContent());

        ContentRender contentRender = new ContentRender();
        String html = contentRender.render(recordEntry, content);

        String mime = "text/html";
        String encoding = "utf-8";

        web.loadDataWithBaseURL(null, html, mime, encoding, null);

        saveDisplayStatistic(recordEntry);

        if (recordEntry.getTitle() != null && recordEntry.getTitle().length() > 0) {
            actionBar.setTitle(recordEntry.getTitle());
        }

        displayAd();
    }

    private String readContent(String path) {
        InputStream inputStream;
        byte[] contentBytes = null;

        try {
            inputStream = getAssets().open("bestadvice/" + path);
            contentBytes = new byte[inputStream.available()];
            inputStream.read(contentBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String content = null;
        try {
            content = new String(contentBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return content;
    }

    private void addImageClickHandler(WebView wv) {
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        final Context context = this;
        wv.addJavascriptInterface(new Object()
        {
            @JavascriptInterface
            public void openImage(String url)
            {
                Intent imageIntent = new Intent(context, ImageDisplayActivity.class);
                imageIntent.putExtra(ImageDisplayActivity.PATH_TO_IMAGE, url);
                startActivity(imageIntent);
            }
        }, "img");
    }

    @Override
    protected void onResume() {
        super.onResume();

        resumeAd();

        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(this);

        String sizeText = sharedPref.getString("fontsSize",
                getResources().getString(R.string.defaultFontsSize));

        WebView web = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.valueOf(sizeText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_content, menu);

        FavoriteManager favoriteManager = FavoriteManager.getInstance(this);

        if(currentRecord != null) {
            if(favoriteManager.isFavorite(currentRecord)) {
                isFavorite = true;
                menu.findItem(R.id.action_favorites).setIcon(R.drawable.ic_favorites_enable);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        FavoriteManager favoriteManager = FavoriteManager.getInstance(this);

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, PreferenceActivity.class));
            return true;
        } else if(id == R.id.action_favorites) {
            if(isFavorite) {
                item.setIcon(R.drawable.ic_favorites_disable);
                isFavorite = false;
                favoriteManager.deleteRecord(currentRecord);
            }
            else {
                item.setIcon(R.drawable.ic_favorites_enable);
                isFavorite = true;
                favoriteManager.addRecord(currentRecord);
                saveFavoriteStatistic(currentRecord);
            }

            return true;
        }

        finish();
        return true;
    }

    private void saveDisplayStatistic(RecordEntry recordEntry) {
        saveStatistic(recordEntry, "display-article");
    }

    private void saveFavoriteStatistic(RecordEntry recordEntry) {
        saveStatistic(recordEntry, "favorite-article");
    }

    private void saveStatistic(RecordEntry recordEntry, String category) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker analyticsTracker = analytics.newTracker(R.xml.global_tracker);

        analyticsTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category).setAction(recordEntry.getTitle()).build());
    }
}
