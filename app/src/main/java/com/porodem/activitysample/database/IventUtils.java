package com.porodem.activitysample.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class IventUtils {

    public static String getDateString(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String s = sdf.format(d);

        return s;
    }
}
