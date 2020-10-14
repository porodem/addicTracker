package com.porodem.activitysample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.porodem.activitysample.database.IventDBSchema.*;

public class IventBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";


    public IventBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableEvent.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TableEvent.Cols.UUID + ", " +
                TableEvent.Cols.TITLE + ", " +
                TableEvent.Cols.NOTE + ", " +
                TableEvent.Cols.TARGET_DURATION + ", " +
                TableEvent.Cols.TOP_DURATION + ", " +
                TableEvent.Cols.ICON_PATH +
                ")"
        );

        db.execSQL("CREATE TABLE " + TableTrack.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TableTrack.Cols.ID + ", " +
                TableTrack.Cols.BDATE + ", " +
                TableTrack.Cols.EDATE + ", " +
                TableTrack.Cols.EVENT_ID + ", " +
                TableTrack.Cols.FAIL_ID +
                ")"
        );

        db.execSQL("CREATE TABLE " + TableFail.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TableFail.Cols.ID + ", " +
                TableFail.Cols.TRIGGER_NAME +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
