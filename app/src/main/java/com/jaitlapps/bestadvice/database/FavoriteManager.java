package com.jaitlapps.bestadvice.database;

import android.content.Context;

import com.jaitlapps.bestadvice.domain.RecordEntry;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

import java.util.List;

public class FavoriteManager {
    private static FavoriteManager instance;
    private List<RecordEntry> recordEntries;
    private FavoriteDAO dao;

    private ListRecordGroup listRecordGroup;

    private FavoriteManager(Context context) {
        dao = new FavoriteDAO(context);
        listRecordGroup = dao.getListFavorites();

        recordEntries = listRecordGroup.getRecordEntryList();
    }


    public synchronized static FavoriteManager getInstance(Context context) {
        if(instance == null) {
            instance = new FavoriteManager(context);
        }

        return instance;
    }

    public void addRecord(RecordEntry recordEntry) {
        recordEntries.add(0, recordEntry);
        dao.addToFavorite(recordEntry);
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
    }

    public boolean isFavorite(RecordEntry recordEntry) {
        for(RecordEntry entry: recordEntries) {
            if(entry.getId().equals(recordEntry.getId()))
                return true;
        }

        return false;
    }

    public ListRecordGroup getList() {
        return listRecordGroup;
    }

}
