package com.jaitlapps.bestadvice;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.jaitlapps.bestadvice.adapter.GroupMenuAdapter;
import com.jaitlapps.bestadvice.domain.GroupEntry;
import com.jaitlapps.bestadvice.loader.MainMenuLoader;

import java.util.List;


public class MainActivity extends BaseAdActivity {

    public final static String JSON_RECORDENTRY = "com.jaitlapps.bestadvice.JSON_RECORDENTRY";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        enablingAdvertisingFeatures();

        MainMenuLoader mainMenuLoader = new MainMenuLoader(this);


        List<GroupEntry> groups = mainMenuLoader.loadMainMenu();

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);

        GroupMenuAdapter adapter = new GroupMenuAdapter(this, groups);

        listView.setAdapter(adapter);

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
