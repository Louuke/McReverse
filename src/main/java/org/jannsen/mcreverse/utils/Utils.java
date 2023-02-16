package org.jannsen.mcreverse.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static List<String> loadListOfNames() {
        List<String> names = new ArrayList<>();
        try(InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("names")))) {
            BufferedReader br = new BufferedReader(reader);

            String s;
            while((s = br.readLine()) != null) {
                names.add(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }
}
