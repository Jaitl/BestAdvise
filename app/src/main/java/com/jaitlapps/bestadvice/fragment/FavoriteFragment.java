package com.jaitlapps.bestadvice.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.adapter.ListAdapter;
import com.jaitlapps.bestadvice.database.FavoriteManager;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

public class FavoriteFragment extends android.support.v4.app.ListFragment {

    private Activity activity;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListRecordGroup listRecordGroup = FavoriteManager.getInstance().getList();

        ListAdapter adapter = new ListAdapter(activity, listRecordGroup);
        FavoriteManager.getInstance().setAdapter(adapter);

        setListAdapter(adapter);
        setEmptyText((activity.getResources().getString(R.string.empty_favorite_text)));
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
