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
        String uuidString = getString(getColumnIndex(TableTrack.Cols.EVENT_ID));
        String title = getString(getColumnIndex(TableEvent.Cols.TITLE));
        long bdate = getLong(getColumnIndex(TableTrack.Cols.BDATE));
        //String topDuration = getString(getColumnIndex(TableTrack.Cols.EDATE));

        Ivent ivent = new Ivent(UUID.fromString(uuidString));
        ivent.setTitle(title);
        ivent.setmDate(new Date(bdate));
        //ivent.setTopDuration(topDuration);
        return ivent;
    }
}
