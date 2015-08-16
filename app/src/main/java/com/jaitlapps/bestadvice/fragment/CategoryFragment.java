package com.jaitlapps.bestadvice.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.jaitlapps.bestadvice.adapter.CategoryAdapter;
import com.jaitlapps.bestadvice.MainMenuLoader;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.domain.GroupEntry;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

import java.util.List;

public class CategoryFragment extends Fragment {
    private Activity activity;

    public CategoryFragment() {

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View categoryView = inflater.inflate(R.layout.fragment_category, container, false);

        if(activity != null) {

            MainMenuLoader mainMenuLoader = new MainMenuLoader(activity.getAssets());

            List<GroupEntry> groups = mainMenuLoader.loadCategories();

            ExpandableListView listView = (ExpandableListView) categoryView.findViewById(R.id.listView);

            CategoryAdapter adapter = new CategoryAdapter(activity, groups);

            listView.setAdapter(adapter);
        }
        return categoryView;
    }

}
