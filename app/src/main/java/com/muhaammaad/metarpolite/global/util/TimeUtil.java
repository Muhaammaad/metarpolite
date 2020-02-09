package com.muhaammaad.metarpolite.global.util;

import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class TimeUtil {
    public static long getMillies(String givenDateString) {
        long timeInMilliseconds = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeInMilliseconds = Instant.parse(givenDateString).toEpochMilli();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'");
            try {
                Date mDate = sdf.parse(givenDateString);
                timeInMilliseconds = mDate.getTime();
                System.out.println("Date in milli :: " + timeInMilliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return timeInMilliseconds;
    }
}
