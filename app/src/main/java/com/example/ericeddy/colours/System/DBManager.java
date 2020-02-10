package com.example.ericeddy.colours.System;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {


    private Context context;

    private SQLiteHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new SQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String serializedData, int colorTheme) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelper.TITLE, name);
        contentValue.put(SQLiteHelper.DATA, serializedData);
        contentValue.put(SQLiteHelper.COLOR, colorTheme);
        database.insert(SQLiteHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { SQLiteHelper._ID, SQLiteHelper.TITLE, SQLiteHelper.DATA, SQLiteHelper.COLOR };
        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String serializedData, int colorTheme) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.TITLE, name);
        contentValues.put(SQLiteHelper.DATA, serializedData);
        contentValues.put(SQLiteHelper.COLOR, colorTheme);
        int i = database.update(SQLiteHelper.TABLE_NAME, contentValues, SQLiteHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper._ID + "=" + _id, null);
    }
}
