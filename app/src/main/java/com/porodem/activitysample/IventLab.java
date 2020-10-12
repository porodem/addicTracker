package com.porodem.activitysample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.porodem.activitysample.database.IventBaseHelper;
import com.porodem.activitysample.database.IventCursorWrapper;
import com.porodem.activitysample.database.IventDBSchema;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.porodem.activitysample.database.IventDBSchema.*;

public class IventLab {
    private static IventLab sIventLab;

    private Context mCtx;
    private SQLiteDatabase mDB;

    public static IventLab get(Context context) {
        if (sIventLab == null) {
            sIventLab = new IventLab(context);
        }
        return sIventLab;
    }

    private IventLab(Context context) {
        mCtx = context.getApplicationContext();
        mDB = new IventBaseHelper(mCtx).getWritableDatabase();

        /*for (int i = 0; i<5; i++) {
            Ivent crime = new Ivent();
            crime.setTitle("Crime # " + i);
            addIvent(crime);
        }*/
    }

    public List<Ivent> getIvents() {
        List<Ivent> ivents = new ArrayList<>();

        IventCursorWrapper cursor = queryIvents(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ivents.add(cursor.getIvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return ivents;
    }

    public Ivent getIvent(UUID id) {
        IventCursorWrapper cursor = queryIvents(
                IventsTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getIvent();
        } finally {
            cursor.close();
        }
    }

    public void updateIvent(Ivent ivent) {
        String uuidString = ivent.getId().toString();
        ContentValues values = getContentValues(ivent);

        mDB.update(IventsTable.NAME, values,
                IventsTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private IventCursorWrapper queryIvents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDB.query(
                IventsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new IventCursorWrapper(cursor);
    }

    public void addIvent(Ivent iv) {
        ContentValues values = getContentValues(iv);
        mDB.insert(IventsTable.NAME, null, values);
    }

    public void deleteIvent(Ivent ivent) {
        String uuidString = ivent.getId().toString();
        ContentValues contentValues = getContentValues(ivent);

        mDB.delete(IventsTable.NAME, IventsTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    private static ContentValues getContentValues(Ivent ivent) {
        ContentValues values = new ContentValues();
        values.put(IventsTable.Cols.UUID, ivent.getId().toString());
        values.put(IventsTable.Cols.DATE, ivent.getDate().getTime());
        values.put(IventsTable.Cols.TITLE, ivent.getTitle());
        //values.put(IventsTable.Cols.TOP_DURATION, ivent.getTopDuration());
        return values;
    }
}
