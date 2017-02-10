package com.example.preid.contentprovidertest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddNewRatingDialog extends DialogFragment {
    private static final String AUTHORITY = "contentprovidertest.provider.ratings";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ratings");

    public interface OnNewRatingListener {
        void onNewRatingAdded();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_new, null);
        builder.setView(view)
                .setTitle("Add New Rating")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.dialog_rating_bar);
                        EditText editText = (EditText) view.findViewById(R.id.dialog_comment_text);

                        ContentValues values = new ContentValues();
                        values.put(RatingDatabaseHelper.COLUMN_RATING,
                                ratingBar.getRating());
                        values.put(RatingDatabaseHelper.COLUMN_COMMENT,
                                editText.getText().toString());

                        getActivity().getContentResolver().insert(CONTENT_URI, values);
                        ((OnNewRatingListener) getActivity()).onNewRatingAdded();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddNewRatingDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
