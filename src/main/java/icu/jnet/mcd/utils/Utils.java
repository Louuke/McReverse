package icu.jnet.mcd.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static icu.jnet.mcd.api.McClientSettings.ZONE_ID;

public class Utils {

    public static void waitMill(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long localToUnix(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .atZone(ZONE_ID)
                .toInstant().getEpochSecond();
    }
}
