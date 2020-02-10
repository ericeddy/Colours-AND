package com.example.ericeddy.colours.System;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class SQLiteHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "DESIGNS";

    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DATA = "data";
    public static final String COLOR = "color";

    // Database Information
    static final String DB_NAME = "DESIGNS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " + DATA + " TEXT, " + COLOR + " INTEGER);";


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    private static final char NEXT_ITEM = ' ';

    public static String serialize(int[][] array) {
        StringBuilder s = new StringBuilder();
        s.append(array.length).append(NEXT_ITEM);

        for(int[] row : array) {
            s.append(row.length).append(NEXT_ITEM);

            for(int item : row) {
                s.append(String.valueOf(item)).append(NEXT_ITEM);
            }
        }

        return s.toString();
    }

    public static int[][] deserialize(String str) throws IOException {
        StreamTokenizer tok = new StreamTokenizer(new StringReader(str));
        tok.resetSyntax();
        tok.wordChars('0', '9');
        tok.whitespaceChars(NEXT_ITEM, NEXT_ITEM);
        tok.parseNumbers();

        tok.nextToken();

        int     rows = (int) tok.nval;
        int[][] out  = new int[rows][];

        for(int i = 0; i < rows; i++) {
            tok.nextToken();

            int   length = (int) tok.nval;
            int[] row    = new int[length];
            out[i]       = row;

            for(int j = 0; j < length; j++) {
                tok.nextToken();
                row[j] = (int) tok.nval;
            }
        }

        return out;
    }

}
