package com.porodem.activitysample;

import java.util.Date;
import java.util.UUID;

public class Ivent {
    private UUID mId;
    private String mTitle;
    private Date mDate, topDate;
    private long topDuration;
    private int mEventId, failsCount;
    private long mPrevDura;

    public long getPrevDura() {
        if(mPrevDura != 0) {
            return mPrevDura;
        } else return 0;
    }

    public void setPrevDura(long mPrevDura) {
        this.mPrevDura = mPrevDura;
    }



    public Ivent() {
        this(UUID.randomUUID());
    }

    public Ivent(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public long getTopDuration() {
        return topDuration;
    }

    public void setTopDuration(long topDuration) {
        this.topDuration = topDuration;
    }


    public int getEventId() {
        return mEventId;
    }

    public void setEventId(int mEventId) {
        this.mEventId = mEventId;
    }

    public Date getTopDate() {
        return topDate;
    }

    public void setTopDate(Date topDate) {
        this.topDate = topDate;
    }

    public int getFailsCount() {
        return failsCount;
    }

    public void setFailsCount(int failsCount) {
        this.failsCount = failsCount;
    }
}
