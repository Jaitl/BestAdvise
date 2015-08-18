package com.jaitlapps.bestadvice.adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.database.FavoriteManager;
import com.jaitlapps.bestadvice.fragment.CategoryFragment;
import com.jaitlapps.bestadvice.fragment.FavoriteFragment;
import com.jaitlapps.bestadvice.fragment.ListFragment;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabsAdapter extends FragmentStatePagerAdapter {
    private List<Map.Entry<String, Fragment>> tabEntries;

    public TabsAdapter(FragmentManager fm, final Activity  activity) {
        super(fm);

        AsyncTask loadFavorites = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                FavoriteManager.getInstance().createDAO(activity);
                return null;
            }
        };

        loadFavorites.execute();

        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setActivity(activity);

        ListFragment listFragment = new ListFragment();
        listFragment.setActivity(activity);

        FavoriteFragment favoriteFragment = new FavoriteFragment();
        favoriteFragment.setActivity(activity);

        tabEntries = new ArrayList<>();

        tabEntries.add(new AbstractMap.SimpleEntry<String, Fragment>(activity.getResources().getString(R.string.category_name),
                categoryFragment));

        tabEntries.add(new AbstractMap.SimpleEntry<String, Fragment>(activity.getResources().getString(R.string.list_name),
                listFragment));

        tabEntries.add(new AbstractMap.SimpleEntry<String, Fragment>(activity.getResources().getString(R.string.favorite_name),
                favoriteFragment));
    }

    @Override
    public int getCount() {
        return tabEntries.size();
    }

    @Override
    public Fragment getItem(int position) {
        return tabEntries.get(position).getValue();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabEntries.get(position).getKey();
    }
}
