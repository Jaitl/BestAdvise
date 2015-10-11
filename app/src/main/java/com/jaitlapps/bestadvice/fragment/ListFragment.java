package com.jaitlapps.bestadvice.fragment;

import android.os.Bundle;

import com.jaitlapps.bestadvice.DataLoader;
import com.jaitlapps.bestadvice.adapter.ListAdapter;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;


public class ListFragment extends android.support.v4.app.ListFragment {

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataLoader dataLoader = new DataLoader(getActivity().getAssets());
        ListRecordGroup listRecordGroup = dataLoader.loadListRecords();

        ListAdapter listAdapter = new ListAdapter(getActivity(), listRecordGroup);

        setListAdapter(listAdapter);
    }
}
