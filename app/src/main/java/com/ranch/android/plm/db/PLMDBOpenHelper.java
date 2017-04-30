package com.ranch.android.plm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ranch.android.plm.MainActivity;
import com.ranch.android.plm.model.Task;

/**
 * Created by Asitha on 4/12/2017.
 */

public class PLMDBOpenHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "plm.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_CREATE_TASK =
            "CREATE TABLE " + Task.TABLE_TASK + " (" +
                    Task.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Task.COLUMN_TITLE + " TEXT, " +
                    Task.COLUMN_DESCRIPTION + " TEXT, " +
                    Task.COLUMN_SUBJECT + " TEXT, " +
                    Task.COLUMN_DEADLINE + " NUMERIC, " +
                    Task.COLUMN_PROGRESS + " REAL, " +
                    Task.COLUMN_COMPLETED + " NUMERIC, " +
                    Task.COLUMN_SEVERITY + " NUMERIC, " +
                    Task.COLUMN_PRIORITY + " NUMERIC " +
                    ")";

    public PLMDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_TASK);
        Log.i(MainActivity.LOGTAG, "Task Table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE_TASK);
        onCreate(db);
    }
}
