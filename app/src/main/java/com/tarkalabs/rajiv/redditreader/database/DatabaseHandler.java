package com.tarkalabs.rajiv.redditreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tarkalabs.rajiv.redditreader.database.ReaderContract.ReaderTable;
import com.tarkalabs.rajiv.redditreader.model.ReaderDetail;

/**
 * Created by Rajiv M on 13/04/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "reader_db";
    static final int DATABASE_VERSION = 1;

    private static DatabaseHandler databaseHandler;
    private static SQLiteDatabase readableDatabase;
    private static SQLiteDatabase writableDatabase;

    private static Context context;

    /**
     * Instantiates a new DB helper.
     *
     * @param context the context
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DatabaseHandler.context = context;
    }

    /**
     * Returns an instance of DBHelper class.
     *
     * @return Returns DatabaseHelper instance.
     */
    public static synchronized DatabaseHandler getInstance() {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        return databaseHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + ReaderTable.TABLE_NAME + "(" +
                ReaderContract.ReaderTable._ID + " INTEGER PRIMARY KEY," +
                ReaderTable.COLUMN_NEWS_ID + " TEXT," +
                ReaderTable.COLUMN_TITLE + " TEXT," +
                ReaderTable.COLUMN_DOMAIN + " TEXT," +
                ReaderTable.COLUMN_SUB_REDDIT + " TEXT," +
                ReaderTable.COLUMN_AUTHOR + " TEXT," +
                ReaderTable.COLUMN_SCORE + " TEXT," +
                ReaderTable.COLUMN_NO_COMMENTS + " TEXT," +
                ReaderTable.COLUMN_THUMBNAIL + " TEXT," +
                ReaderTable.COLUMN_URL + " TEXT" + ")";
        db.execSQL(createQuery);
    }

    //------------------------------------------------------------- Required methods----------------------------------------------------------------//

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Returns an instance of readable database.
     *
     * @return Returns readable SQLiteDatabase instance.
     */
    private synchronized SQLiteDatabase getReadableDb() {
        if (readableDatabase == null) {
            readableDatabase = this.getReadableDatabase();
        }
        return readableDatabase;
    }

    /**
     * Returns an instance of writable database.
     *
     * @return Returns writable SQLiteDatabase instance.
     */
    private synchronized SQLiteDatabase getWritableDb() {
        if (writableDatabase == null) {
            writableDatabase = this.getWritableDatabase();
        }
        return writableDatabase;
    }

    //-------------------------------------------------------------Insert----------------------------------------------------------------//


    public long insertReaderData(ReaderDetail readerDetail) {
        SQLiteDatabase database = getWritableDb();
        ContentValues values = new ContentValues();
        values.put(ReaderTable.COLUMN_NEWS_ID, readerDetail.getId());
        values.put(ReaderTable.COLUMN_TITLE, readerDetail.getTitle());
        values.put(ReaderTable.COLUMN_DOMAIN, readerDetail.getDomain());
        values.put(ReaderTable.COLUMN_SUB_REDDIT, readerDetail.getSubReddit());
        values.put(ReaderTable.COLUMN_AUTHOR, readerDetail.getAuthor());
        values.put(ReaderTable.COLUMN_SCORE, readerDetail.getScore());
        values.put(ReaderTable.COLUMN_NO_COMMENTS, readerDetail.getNumComments());
        values.put(ReaderTable.COLUMN_THUMBNAIL, readerDetail.getThumbnail());
        values.put(ReaderTable.COLUMN_URL, readerDetail.getUrl());
        return database.insert(ReaderTable.TABLE_NAME, null, values);
    }

    //-------------------------------------------------------------Select----------------------------------------------------------------//

    /**
     * To select all the user from database.
     */
    public Cursor getReaderData() {
        SQLiteDatabase database = getReadableDb();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ReaderTable._ID,
                ReaderTable.COLUMN_NEWS_ID,
                ReaderTable.COLUMN_TITLE,
                ReaderTable.COLUMN_DOMAIN,
                ReaderTable.COLUMN_SUB_REDDIT,
                ReaderTable.COLUMN_AUTHOR,
                ReaderTable.COLUMN_SCORE,
                ReaderTable.COLUMN_NO_COMMENTS,
                ReaderTable.COLUMN_THUMBNAIL,
                ReaderTable.COLUMN_URL
        };

        return database.query(
                ReaderTable.TABLE_NAME,  // The table to query
                projection,    // The columns to return
                null,   // The columns for the WHERE clause (selection)
                null,   // The values for the WHERE clause (selectionArgs)
                null,  // don't group the rows
                null,  // don't filter by row groups
                null  // The sort order
        );
    }

    //-------------------------------------------------------------Delete----------------------------------------------------------------//

    public int clearTable() {
        SQLiteDatabase db = getWritableDb();
        return db.delete(ReaderTable.TABLE_NAME, null, null);
    }

}
