package com.example.preid.contentprovidertest;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity
        implements AddNewRatingDialog.OnNewRatingListener {
    SimpleCursorAdapter adapter;

    private static final String AUTHORITY = "contentprovidertest.provider.ratings";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ratings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView ratingListView = (ListView) findViewById(R.id.rating_list_view);

        adapter = new SimpleCursorAdapter(this,
                R.layout.row_layout,
                null,
                new String[] {RatingDatabaseHelper.COLUMN_RATING, RatingDatabaseHelper.COLUMN_COMMENT},
                new int[] {R.id.row_rating, R.id.row_text},
                0);

        ratingListView.setAdapter(adapter);
        refreshValuesFromContentProvider();
    }

    void refreshValuesFromContentProvider() {
        CursorLoader cursorLoader = new CursorLoader(this, CONTENT_URI, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        adapter.swapCursor(cursor);
    }

    public void onNewButtonClick(View view) {
        AddNewRatingDialog dialog = new AddNewRatingDialog();
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onNewRatingAdded() {
        refreshValuesFromContentProvider();
    }
}
