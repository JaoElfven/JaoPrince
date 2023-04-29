package com.example.dbase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    // Define constants for the content provider URI and database table
    public static final String AUTHORITY = "com.example.database";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/my_table");

    // Define a database helper to manage the underlying SQLite database
    private MydbHelper dbHelper;

    // Implement the onCreate method to initialize the content provider
    @Override
    public boolean onCreate() {
        dbHelper = new MydbHelper(getContext());
        return true;
    }

    // Implement the query method to return data from the database
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("my_table", projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Implement the insert method to add new data to the database
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert("my_table", null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    // Implement the update method to modify existing data in the database
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.update("my_table", values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // Implement the delete method to remove data from the database
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete("my_table", selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // Implement the getType method to return the MIME type of the data
    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.example.my_table";
    }
}

