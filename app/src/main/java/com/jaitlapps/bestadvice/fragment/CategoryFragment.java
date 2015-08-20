package com.jaitlapps.bestadvice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.jaitlapps.bestadvice.DataLoader;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.adapter.CategoryAdapter;
import com.jaitlapps.bestadvice.domain.GroupEntry;

import java.util.List;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View categoryView = inflater.inflate(R.layout.fragment_category, container, false);

        DataLoader dataLoader = new DataLoader(getActivity().getAssets());

        List<GroupEntry> groups = dataLoader.loadCategories();

        ExpandableListView listView = (ExpandableListView) categoryView.findViewById(R.id.listView);

        CategoryAdapter adapter = new CategoryAdapter(getActivity(), groups);

        listView.setAdapter(adapter);

        return categoryView;
    }

}
