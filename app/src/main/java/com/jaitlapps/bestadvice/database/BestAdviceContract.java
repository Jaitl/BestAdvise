package com.jaitlapps.bestadvice.database;

import android.provider.BaseColumns;

public final class BestAdviceContract {
    public BestAdviceContract() {}

    public static abstract class FavoriteContract implements BaseColumns {
        public static final String TABLE_NAME = "FavoriteList";
        public static final String COLUMN_NAME_RECORD_ID = "recordid";
    }
}
