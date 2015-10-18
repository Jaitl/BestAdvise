package com.jaitlapps.bestadvice.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jaitlapps.bestadvice.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDisplayActivity extends BaseAdActivity {

    public static final String PATH_TO_IMAGE = "path-to-image";
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_image_display);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String url = intent.getStringExtra(PATH_TO_IMAGE);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        try {
            Drawable image = getImage(url);
            imageView.setImageDrawable(image);
        } catch (IOException e) {

        }

        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setZoomable(true);

        displayAd();
    }

    private Drawable getImage(String url) throws IOException {
        url = url.replace("file:///android_asset/", "");

        InputStream ims = getAssets().open(url);
        return Drawable.createFromStream(ims, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Need to call clean-up
        mAttacher.cleanup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}











