package com.example.vishal.monitor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;


/**
 * Created by vishal on 28/2/17.
 */

public class Monitordb extends SQLiteOpenHelper {

    public static final String TAG = "Monitordb";
    public static final String DATABASE_NAME = "Monitor.db";
    public static final String COLUMN_PACKAGE_NAME = "package";
    private static final int DATABASE_VERSION = 2;
    public static final String INJECTEDAPPS_TABLE_NAME = "injectedapps";
    private static final String ALERTPREFS_TABLE_CREATE =
            "CREATE TABLE " + INJECTEDAPPS_TABLE_NAME + " (" +
                    COLUMN_PACKAGE_NAME + " TEXT PRIMARY KEY);";

    Monitordb(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALERTPREFS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public LinkedList<String> getInjectedApps()
    {
        SQLiteDatabase db = getReadableDatabase();
        LinkedList<String> applist = new LinkedList<String>();
        Cursor cursor = db.query(false, INJECTEDAPPS_TABLE_NAME, new String[] {COLUMN_PACKAGE_NAME}, null, null, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                applist.add(cursor.getString(0));
            }
        } finally {
            cursor.close();
        }
        Log.d(TAG, "selectStar: "+applist);
        return applist;
    }

    public void selectStar(SQLiteDatabase db)
    {
        String op = "";
        Cursor cursor = db.query(false, INJECTEDAPPS_TABLE_NAME, new String[] {COLUMN_PACKAGE_NAME}, null, null, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                op= op+" "+cursor.getString(0);
            }
        } finally {
            cursor.close();
        }
        Log.d(TAG, "selectStar: "+op);
    }
}
