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
        String prev_dura = getString(getColumnIndex(TableEvent.Cols.PREV_DUARATION));
        long topDura = getLong(getColumnIndex(TableEvent.Cols.TOP_DURATION));
        long topDate = getLong(getColumnIndex(TableEvent.Cols.TOP_DURATION_DATE));
        int failsCount = getInt(getColumnIndex(TableEvent.Cols.FAILS_COUNT));

        Ivent ivent = new Ivent(UUID.fromString(uuidString));
        ivent.setTitle(title);
        ivent.setmDate(new Date(bdate));
        ivent.setPrevDura(Long.valueOf(prev_dura));
        ivent.setTopDuration(topDura);
        ivent.setTopDate(new Date(topDate));
        ivent.setFailsCount(failsCount);
        return ivent;
    }

    public String getFail() {
        String failName = getString(getColumnIndex(TableFail.Cols.TRIGGER_NAME));
        return failName;
    }
}
