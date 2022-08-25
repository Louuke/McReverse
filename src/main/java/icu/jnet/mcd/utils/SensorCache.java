package icu.jnet.mcd.utils;

import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.utils.listener.ClientStateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SensorCache {

    private final Queue<String> sensorQueue = new ConcurrentLinkedQueue<>();
    private final List<ClientStateListener> stateListener = new ArrayList<>();
    private static SensorCache instance;

    private SensorCache() {}

    public static SensorCache getInstance() {
        if(instance == null) {
            instance = new SensorCache();
        }
        return instance;
    }

    public synchronized String getSensorToken() {
        if(sensorQueue.isEmpty()) {
            String token = queryToken();
            addToQueue(token);
            verifyToken(token);
        }
        return sensorQueue.poll();
    }

    private String queryToken() {
        return stateListener.stream().map(ClientStateListener::tokenRequired).filter(Objects::nonNull).findAny().orElse("");
    }

    private void addToQueue(String token) {
        int usage = !token.isEmpty() ? 20 : 1;
        for(int i = 0; i < usage; i++) {
            sensorQueue.add(token);
        }
    }

    private void verifyToken(String token) {
        if(token.isEmpty()) {
            return;
        }
        McClient client = new McClient();
        stateListener.forEach(client::addStateListener);
        client.verifyNextToken();
    }

    public void addStateListener(ClientStateListener listener) {
        stateListener.add(listener);
    }
}
