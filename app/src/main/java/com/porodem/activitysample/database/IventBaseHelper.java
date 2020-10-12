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
        db.execSQL("CREATE TABLE " + IventsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                IventsTable.Cols.UUID + ", " +
                IventsTable.Cols.TITLE + ", " +
                IventsTable.Cols.DATE + ", " +
                IventsTable.Cols.TOP_DURATION +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
