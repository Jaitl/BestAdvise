package com.jaitlapps.bestadvice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaitlapps.bestadvice.R;
import com.jaitlapps.bestadvice.activity.DisplayContentActivity;
import com.jaitlapps.bestadvice.activity.TabsActivity;
import com.jaitlapps.bestadvice.domain.RecordEntry;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

import java.io.IOException;
import java.io.InputStream;

public class ListAdapter extends BaseAdapter {

    private final Context context;
    private final ListRecordGroup listRecordGroup;
    private final LayoutInflater inflater;
    private final Gson gson;

    public ListAdapter(Context context, ListRecordGroup listRecordGroup) {
        this.context = context;
        this.listRecordGroup = listRecordGroup;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gson = new Gson();
    }

    @Override
    public int getCount() {
        return listRecordGroup.getRecordEntryList().size();
    }

    @Override
    public Object getItem(int position) {
        return listRecordGroup.getRecordEntryList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        if(convertView == null)
            rowView = inflater.inflate(R.layout.list_record, parent, false);
        else
            rowView = convertView;

        TextView textView = (TextView) rowView.findViewById(R.id.textViewRecord);
        TextView groupTextView = (TextView) rowView.findViewById(R.id.textViewGroup);

        final RecordEntry record = listRecordGroup.getRecordEntryList().get(position);
        textView.setText(record.getTitle());

        groupTextView.setText(listRecordGroup.getGroupEntryMap().get(record.getGroupId()).getTitle());

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);

        Drawable icon = getIcon(position);

        imageView.setImageDrawable(icon);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayContentActivity.class);
                String jsonRecord = gson.toJson(record, RecordEntry.class);

                intent.putExtra(TabsActivity.JSON_RECORDENTRY, jsonRecord);

                context.startActivity(intent);
            }
        });

        return rowView;
    }

    private Drawable getIcon(int position) {
        Drawable drawable = null;

        try {

            InputStream ims = context.getAssets().open("bestadvice/" + listRecordGroup.getRecordEntryList().get(position).getPathToImage());
            drawable = Drawable.createFromStream(ims, null);
        }
        catch (IOException ex) {
            System.out.println("Icon no found");
            InputStream ims = null;

            try {
                ims = context.getAssets().open("default/standard_logo.png");
            } catch (IOException e) {
                e.printStackTrace();
            }

            drawable = Drawable.createFromStream(ims, null);
        }

        return drawable;
    }
}
