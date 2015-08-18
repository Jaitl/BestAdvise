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


public class ListFragment extends Fragment {

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View listView = inflater.inflate(R.layout.fragment_list, container, false);

        DataLoader dataLoader = new DataLoader(activity.getAssets());
        ListRecordGroup listRecordGroup = dataLoader.loadListRecords();

        ListView listControl = (ListView) listView.findViewById(R.id.listView);

        ListAdapter listAdapter = new ListAdapter(activity, listRecordGroup);

        listControl.setAdapter(listAdapter);

        return listView;
    }
}
