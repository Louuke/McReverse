package org.jannsen.mcd.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static void waitMill(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long timeToUnix(String date, ZoneId zone) {
        date = date.endsWith("Z") ? date.substring(0, date.length() - 1) : date;
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .atZone(zone)
                .toInstant().getEpochSecond();
    }
}
