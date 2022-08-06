package icu.jnet.mcd.helper;

public class Utils {

    public static void waitMill(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
