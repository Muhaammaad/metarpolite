package com.muhaammaad.metarpolite.global.util;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeUtil {
    /**
     * Converts time to millis
     *
     * @param givenDateString the time in format to be converted
     */
    private static long getMillies(String givenDateString) {
        long timeInMilliseconds = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeInMilliseconds = Instant.parse(givenDateString).toEpochMilli();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'", Locale.GERMANY);
            try {
                Date mDate = sdf.parse(givenDateString);
                if (mDate != null) {
                    timeInMilliseconds = mDate.getTime();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return timeInMilliseconds;
    }

    /**
     * Converts time to ago time duration
     *
     * @param givenDateString the time in format to be converted
     * @param max             the highest time unit of interest
     * @param min             the lowest time unit of interest
     */
    public static String formatMillis(String givenDateString, TimeUnit max, TimeUnit min) {
        return formatMillis(new Date().getTime() - new Date(getMillies(givenDateString)).getTime(), max, min) + "ago";
    }

    /**
     * Converts time to required format time
     *
     * @param givenDateString the time in format to be converted
     */
    public static String formatMillis(String givenDateString) {
        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd MMM, hh:mm aa", Locale.GERMANY);
        //Setting the time zone
        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(dateTimeInGMT.format(new Date(getMillies(givenDateString))));
        return dateTimeInGMT.format(new Date());
    }

    /**
     * Converts time to a human readable format within the specified range
     *
     * @param duration the time in milliseconds to be converted
     * @param max      the highest time unit of interest
     * @param min      the lowest time unit of interest
     */
    private static String formatMillis(long duration, TimeUnit max, TimeUnit min) {
        StringBuilder res = new StringBuilder();

        TimeUnit current = max;

        while (duration > 0) {
            long temp = current.convert(duration, MILLISECONDS);

            if (temp > 0) {
                duration -= current.toMillis(temp);
                res.append(temp).append(" ").append(current.name().toLowerCase());
                if (temp < 2) res.deleteCharAt(res.length() - 1);
                res.append(", ");
            }

            if (current == min) break;

            current = TimeUnit.values()[current.ordinal() - 1];
        }

        // clean up our formatting....

        // we never got a hit, the time is lower than we care about
        if (res.lastIndexOf(", ") < 0) return "0 " + min.name().toLowerCase();

        // yank trailing  ", "
        res.deleteCharAt(res.length() - 2);

        //  convert last ", " to " and"
        int i = res.lastIndexOf(", ");
        if (i > 0) {
            res.deleteCharAt(i);
            res.insert(i, " and");
        }

        return res.toString();
    }
}
