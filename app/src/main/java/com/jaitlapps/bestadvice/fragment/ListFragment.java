package com.jaitlapps.bestadvice.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jaitlapps.bestadvice.DataLoader;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.adapter.ListAdapter;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;


public class ListFragment extends android.support.v4.app.ListFragment {

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataLoader dataLoader = new DataLoader(activity.getAssets());
        ListRecordGroup listRecordGroup = dataLoader.loadListRecords();

        ListAdapter listAdapter = new ListAdapter(activity, listRecordGroup);

        setListAdapter(listAdapter);
    }
}
