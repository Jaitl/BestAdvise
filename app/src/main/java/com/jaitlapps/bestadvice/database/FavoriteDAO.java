package com.jaitlapps.bestadvice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jaitlapps.bestadvice.MainMenuLoader;
import com.jaitlapps.bestadvice.domain.RecordEntry;
import com.jaitlapps.bestadvice.domain.list.ListRecordGroup;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    Context context;
    SQLiteDatabase writableDatabase;
    SQLiteDatabase readableDatabase;

    public FavoriteDAO(Context context) {
        this.context = context;
        BestAdviceDbHelper helper = new BestAdviceDbHelper(context);
        writableDatabase = helper.getWritableDatabase();
        readableDatabase = helper.getReadableDatabase();
    }

    public void addToFavorite(RecordEntry entry) {
        ContentValues values = new ContentValues();
        values.put(BestAdviceContract.FavoriteContract.COLUMN_NAME_RECORD_ID, entry.getId());

        writableDatabase.insert(
                BestAdviceContract.FavoriteContract.TABLE_NAME,
                null,
                values);
    }

    public void deleteFromFavorite(RecordEntry entry) {
        String selection = BestAdviceContract.FavoriteContract.COLUMN_NAME_RECORD_ID + " LIKE ?";
        String[] selectionArgs = { entry.getId() };

        writableDatabase.delete(BestAdviceContract.FavoriteContract.TABLE_NAME, selection, selectionArgs);
    }

    public ListRecordGroup getListFavorites() {
        MainMenuLoader mainMenuLoader = new MainMenuLoader(context.getAssets());
        ListRecordGroup listRecordGroup = mainMenuLoader.loadListRecords();

        List<RecordEntry> entryList = listRecordGroup.getRecordEntryList();

        String[] projection = {BestAdviceContract.FavoriteContract.COLUMN_NAME_RECORD_ID };


        String sortOrder =
                BestAdviceContract.FavoriteContract.COLUMN_NAME_RECORD_ID  + " DESC";

        Cursor c = readableDatabase.query(
                BestAdviceContract.FavoriteContract.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<RecordEntry> favoriteList = new ArrayList<>();

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String idRecord = c.getString(0);

                    for(RecordEntry record: entryList) {
                        if(record.getId().equals(idRecord)) {
                            favoriteList.add(record);
                            break;
                        }
                    }

                }while (c.moveToNext());
            }
        }
        c.close();

        listRecordGroup.setRecordEntryList(favoriteList);

        return listRecordGroup;
    }
}
