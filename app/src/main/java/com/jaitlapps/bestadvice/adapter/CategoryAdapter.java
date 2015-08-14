package com.jaitlapps.bestadvice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.activity.DisplayContentActivity;
import com.jaitlapps.bestadvice.activity.TabsActivity;
import com.jaitlapps.bestadvice.domain.GroupEntry;
import com.jaitlapps.bestadvice.domain.RecordEntry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CategoryAdapter extends BaseExpandableListAdapter {
    private final List<GroupEntry> groups;
    public LayoutInflater inflater;
    public Activity activity;
    private Gson gson = new Gson();

    public CategoryAdapter(Activity act, List<GroupEntry> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildrenRecord(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final RecordEntry children = (RecordEntry) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_record, parent, false);
        }
        text = (TextView) convertView.findViewById(R.id.textViewRecord);

        String titleContent = children.getTitle();
        if (titleContent != null && titleContent.length() > 0) {
            text.setText(titleContent);
        } else {
            Resources res = activity.getResources();
            text.setText(res.getString(R.string.title_activity_display_content));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DisplayContentActivity.class);
                String jsonRecord = gson.toJson(children, RecordEntry.class);

                intent.putExtra(TabsActivity.JSON_RECORDENTRY, jsonRecord);

                activity.startActivity(intent);
            }
        });

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        try {

            InputStream ims = activity.getAssets().open("bestadvice/" + children.getPathToImage());

            Drawable drawable = Drawable.createFromStream(ims, null);

            imageView.setImageDrawable(drawable);

        }
        catch (IOException ex) {
            System.out.println("Record icon no found");

            InputStream ims = null;

            try {
                ims = activity.getAssets().open("default/standard_logo.png");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Drawable drawable = Drawable.createFromStream(ims, null);

            imageView.setImageDrawable(drawable);
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildrenSize();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_group, parent, false);
        }
        GroupEntry group = (GroupEntry) getGroup(groupPosition);

        CheckedTextView textView = (CheckedTextView) convertView.findViewById(R.id.textViewGroup);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);

        String title = group.getTitle();

        if(title != null && title.length() > 0)
            textView.setText(title);
        else
            textView.setText(activity.getResources().getString(R.string.title_group));
        textView.setChecked(isExpanded);

        try {

            InputStream ims = activity.getAssets().open("bestadvice/" + group.getPathToImage());

            Drawable drawable = Drawable.createFromStream(ims, null);

            imageView.setImageDrawable(drawable);
        }
        catch (IOException ex) {
            System.out.println("Group icon no found");

            InputStream ims = null;

            try {
                ims = activity.getAssets().open("default/standard_logo.png");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Drawable drawable = Drawable.createFromStream(ims, null);

            imageView.setImageDrawable(drawable);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
