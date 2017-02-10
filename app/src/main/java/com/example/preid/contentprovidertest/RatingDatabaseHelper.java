package com.example.preid.contentprovidertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class RatingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RatingDatabase";
    private static final int DATABASE_VERSION = 1;
    static final String RATINGS_TABLE = "ratings";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_RATING = "rating";
    static final String COLUMN_COMMENT = "comment";
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s DECIMAL NOT NULL," +
                    "%s TEXT NOT NULL);",
            RATINGS_TABLE,
            COLUMN_ID,
            COLUMN_RATING,
            COLUMN_COMMENT);

    public RatingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RATINGS_TABLE);
        onCreate(db);
    }

    public Cursor getRatings(String[] projection, String selection,
                             String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(RATINGS_TABLE);

        if (sortOrder == null || sortOrder.equals("")) {
            sortOrder = COLUMN_ID;
        }

        Cursor cursor = queryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }

    public long addNewRating(ContentValues values) {
        return getWritableDatabase().insert(RATINGS_TABLE, "", values);
    }
}
