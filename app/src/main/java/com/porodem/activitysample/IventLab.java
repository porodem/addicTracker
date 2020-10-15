package com.porodem.activitysample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.porodem.activitysample.database.IventBaseHelper;
import com.porodem.activitysample.database.IventCursorWrapper;
import com.porodem.activitysample.database.IventDBSchema;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.porodem.activitysample.database.IventDBSchema.*;

public class IventLab {
    private static IventLab sIventLab;

    private Context mCtx;
    private SQLiteDatabase mDB;

    private static String allQuery = "select event_id, title, bdate from event join track on track.event_id = event.uuid";

    private static String eventQuery = "select event_id, title, bdate from event join track on track.event_id = event.uuid where uuid = ?";

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

        //IventCursorWrapper cursor = queryIvents(null, null);

        IventCursorWrapper cursor = queryIvents(null);

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
        /*IventCursorWrapper cursor = queryIvents(
                TableTrack.Cols.ID + " = ?",
                new String[] {id.toString()}
        );*/
        IventCursorWrapper cursor = queryIvents(new String[] {id.toString()});

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

    public Ivent getOneIvent(UUID id) {
        /*IventCursorWrapper cursor = queryIvents(
                TableTrack.Cols.ID + " = ?",
                new String[] {id.toString()}
        );*/
        IventCursorWrapper cursor = queryOneIvent(new String[] {id.toString()});

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

        mDB.update(TableTrack.NAME, values,
                TableTrack.Cols.ID + " = ?",
                new String[] { uuidString });
    }

    private IventCursorWrapper _queryIvents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDB.query(
                TableTrack.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new IventCursorWrapper(cursor);
    }

    private IventCursorWrapper queryIvents(String[] whereArgs) {
        Cursor cursor = mDB.rawQuery(allQuery, whereArgs);
        return new IventCursorWrapper(cursor);
    }

    private IventCursorWrapper queryOneIvent(String[] whereArgs) {
        Cursor cursor = mDB.rawQuery(eventQuery, whereArgs);
        return new IventCursorWrapper(cursor);
    }

    public void addIvent(Ivent iv) {
        ContentValues values = getContentValues(iv);
        mDB.insert(TableTrack.NAME, null, values);
        ContentValues valuesEvent = getContentValuesEvent(iv);
        mDB.insert(TableEvent.NAME, null, valuesEvent);
    }

    public void addEvent(Ivent iv) {
        ContentValues values = getContentValuesEvent(iv);
        mDB.insert(TableEvent.NAME, null, values);
    }



    public void deleteIvent(Ivent ivent) {
        String uuidString = ivent.getId().toString();
        ContentValues contentValues = getContentValues(ivent);

        mDB.delete(TableTrack.NAME, TableTrack.Cols.ID + " = ?", new String[] {uuidString});
    }

    public void failTrack(UUID id, Date date) {
        String uuidString = id.toString();
        ContentValues values = new ContentValues();
        values.put(TableTrack.Cols.EDATE, date.getTime());

        mDB.update(TableTrack.NAME, values,
                TableTrack.Cols.ID + " = ?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Ivent ivent) {
        ContentValues values = new ContentValues();
        values.put(TableTrack.Cols.ID, ivent.getId().toString());
        values.put(TableTrack.Cols.BDATE, ivent.getDate().getTime());
        values.put(TableTrack.Cols.EVENT_ID, ivent.getId().toString());
        //values.put(TableTrack.Cols.TITLE, ivent.getTitle());
        //values.put(IventsTable.Cols.TOP_DURATION, ivent.getTopDuration());
        return values;
    }

    private static ContentValues getContentValuesEvent(Ivent ivent) {
        ContentValues values = new ContentValues();
        values.put(TableEvent.Cols.UUID, ivent.getId().toString());
        values.put(TableEvent.Cols.TITLE, ivent.getTitle());
        //values.put(TableTrack.Cols.TITLE, ivent.getTitle());
        //values.put(IventsTable.Cols.TOP_DURATION, ivent.getTopDuration());
        return values;
    }
}
