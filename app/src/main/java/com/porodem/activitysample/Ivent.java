package com.porodem.activitysample;

import java.util.Date;
import java.util.UUID;

public class Ivent {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String topDuration;

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

    public String getTopDuration() {
        return topDuration;
    }

    public void setTopDuration(String topDuration) {
        this.topDuration = topDuration;
    }


}
