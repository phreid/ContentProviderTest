package com.example.preid.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class RatingProvider extends ContentProvider {

    private RatingDatabaseHelper mRatingDatabaseHelper = null;

    private static final String AUTHORITY = "contentprovidertest.provider.ratings";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ratings");

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRatingDatabaseHelper = new RatingDatabaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = mRatingDatabaseHelper.getRatings(projection,
                selection, selectionArgs, sortOrder);
        //cursor.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.contentprovidertest.provider.ratings";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mRatingDatabaseHelper.addNewRating(values);
        //getContext().getContentResolver().notifyChange(CONTENT_URI, null, false);

        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
