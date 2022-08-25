package icu.jnet.mcd.utils;

public class Utils {

    public static void waitMill(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
