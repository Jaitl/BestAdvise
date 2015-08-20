package com.jaitlapps.bestadvice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.adapter.TabsAdapter;

import io.karim.MaterialTabs;

public class TabsActivity extends BaseAdActivity {
    TabsAdapter tabsAdapter;
    ViewPager mViewPager;

    public final static String JSON_RECORDENTRY = "com.jaitlapps.bestadvice.JSON_RECORDENTRY";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabs);
        enablingAdvertisingFeatures();

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(tabsAdapter);

        MaterialTabs tabs = (MaterialTabs) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);

        displayAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            startActivity(new Intent(this, PreferenceActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enablingAdvertisingFeatures() {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker analyticsTracker = analytics.newTracker(R.xml.global_tracker);
        analyticsTracker.enableAdvertisingIdCollection(true);
    }
}
