package com.tarkalabs.rajiv.redditreader.database;

import android.provider.BaseColumns;

/**
 * Created by Rajiv M  on 13/04/16.
 */
public class ReaderContract {


    static final String DATABASE_NAME = "reader_db";
    static final int DATABASE_VERSION = 1;

    private ReaderContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class ReaderTable implements BaseColumns {

        public static final String TABLE_NAME = "reader_entry";
        public static final String COLUMN_NEWS_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DOMAIN = "domain";
        public static final String COLUMN_SUB_REDDIT = "subreddit";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_NO_COMMENTS = "numComments";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_URL = "url";
    }

}
