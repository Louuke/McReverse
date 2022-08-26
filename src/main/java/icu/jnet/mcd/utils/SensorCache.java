package icu.jnet.mcd.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SensorCache {

    private final Queue<String> sensorQueue = new ConcurrentLinkedQueue<>();
    private static SensorCache instance;

    private SensorCache() {}

    public static SensorCache getInstance() {
        if(instance == null) {
            instance = new SensorCache();
        }
        return instance;
    }

    public void saveSensorToken(String token) {
        addToQueue(token);
    }

    public String pollToken() {
        return sensorQueue.poll();
    }

    public boolean isTokenAvailable() {
        return !sensorQueue.isEmpty();
    }

    private void addToQueue(String token) {
        int usage = !token.isEmpty() ? 15 : 1;
        for(int i = 0; i < usage; i++) {
            sensorQueue.add(token);
        }
    }
}
