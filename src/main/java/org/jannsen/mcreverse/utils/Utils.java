package org.jannsen.mcreverse.utils;

import org.apache.http.entity.ContentType;
import org.overviewproject.mime_types.GetBytesException;
import org.overviewproject.mime_types.MimeTypeDetector;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class Utils {

    private static final MimeTypeDetector detector = new MimeTypeDetector();

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

    public static String detectMimeType(String base64) {
        try {
            byte[] data = Base64.getDecoder().decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            return detector.detectMimeType("", inputStream);
        } catch (GetBytesException ignored) {}
        return ContentType.APPLICATION_JSON.getMimeType();
    }
}
