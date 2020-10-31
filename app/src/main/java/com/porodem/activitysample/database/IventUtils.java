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

    public static String getDeclension(int duration) {
        String dayDeclension;
        switch ((int)duration) {
            case 1:
                dayDeclension = "день";
                break;
            case 2:
            case 3:
            case 4:
                dayDeclension = "дня";
                break;
            default: dayDeclension = "дней";
        }
        return dayDeclension;
    }
}
