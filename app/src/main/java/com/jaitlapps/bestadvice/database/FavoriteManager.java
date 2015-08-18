package com.jaitlapps.bestadvice.database;

import android.content.Context;
import android.widget.BaseAdapter;

import com.jaitlapps.bestadvice.domain.RecordEntry;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {
    private static final FavoriteManager instance = new FavoriteManager();
    private List<RecordEntry> recordEntries;
    private BaseAdapter adapter;
    private FavoriteDAO dao;

    private ListRecordGroup listRecordGroup;

    private FavoriteManager() {

    }

    public void createDAO(Context context) {
        dao = new FavoriteDAO(context);
        listRecordGroup = dao.getListFavorites();

        recordEntries = listRecordGroup.getRecordEntryList();
    }

    public static FavoriteManager getInstance() {
        return instance;
    }

    public void addRecord(RecordEntry recordEntry) {
        recordEntries.add(0, recordEntry);
        dao.addToFavorite(recordEntry);

        updateList();
    }

    public void deleteRecord(RecordEntry recordEntry) {

        RecordEntry findRecord = null;

        for(RecordEntry entry: recordEntries) {
            if(entry.getId().equals(recordEntry.getId())) {
                findRecord = entry;
                break;
            }
        }

        if(findRecord != null)
            recordEntries.remove(findRecord);

        dao.deleteFromFavorite(recordEntry);

        updateList();
    }

    public boolean isFavorite(RecordEntry recordEntry) {
        for(RecordEntry entry: recordEntries) {
            if(entry.getId().equals(recordEntry.getId()))
                return true;
        }

        return false;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    public ListRecordGroup getList() {
        return listRecordGroup;
    }

    private void updateList() {
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }
}
