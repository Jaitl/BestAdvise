package com.jaitlapps.bestadvice.fragment;

import android.os.Bundle;

import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.adapter.ListAdapter;
import com.jaitlapps.bestadvice.database.FavoriteManager;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

public class FavoriteFragment extends android.support.v4.app.ListFragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListRecordGroup listRecordGroup = FavoriteManager.getInstance(getActivity()).getList();

        ListAdapter adapter = new ListAdapter(getActivity(), listRecordGroup);

        setListAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText((getActivity().getResources().getString(R.string.empty_favorite_text)));
    }

    @Override
    public void onResume() {
        super.onResume();

        ((ListAdapter)getListAdapter()).notifyDataSetChanged();
    }
}
