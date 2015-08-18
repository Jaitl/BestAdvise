package com.jaitlapps.bestadvice.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.adapter.ListAdapter;
import com.jaitlapps.bestadvice.database.FavoriteManager;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

public class FavoriteFragment extends Fragment {

    private Activity activity;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favoriteView = inflater.inflate(R.layout.fragment_list, container, false);

        ListRecordGroup listRecordGroup = FavoriteManager.getInstance().getList();

        ListView list = (ListView) favoriteView.findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter(activity, listRecordGroup);
        FavoriteManager.getInstance().setAdapter(adapter);

        list.setAdapter(adapter);

        return favoriteView;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
