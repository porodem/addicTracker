package com.porodem.activitysample.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.porodem.activitysample.Ivent;

import java.util.Date;
import java.util.UUID;

import static com.porodem.activitysample.database.IventDBSchema.*;

public class IventCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public IventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Ivent getIvent() {
        String uuidString = getString(getColumnIndex(IventsTable.Cols.UUID));
        String title = getString(getColumnIndex(IventsTable.Cols.TITLE));
        long date = getLong(getColumnIndex(IventsTable.Cols.DATE));
        String topDuration = getString(getColumnIndex(IventsTable.Cols.TOP_DURATION));

        Ivent ivent = new Ivent(UUID.fromString(uuidString));
        ivent.setTitle(title);
        ivent.setmDate(new Date(date));
        ivent.setTopDuration(topDuration);
        return ivent;
    }
}
