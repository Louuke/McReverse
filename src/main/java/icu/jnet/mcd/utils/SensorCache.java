package icu.jnet.mcd.utils;

import icu.jnet.mcd.constants.Action;
import icu.jnet.mcd.utils.listener.ClientActionModel;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SensorCache {

    private static final Queue<String> sensorQueue = new ConcurrentLinkedQueue<>();
    private final ClientActionModel actionModel;

    public SensorCache(ClientActionModel actionModel) {
        this.actionModel = actionModel;
    }

    public String getSensorToken() {
        if(sensorQueue.isEmpty()) {
            addToQueue(actionModel.notifyListener(Action.TOKEN_REQUIRED, String.class));
        }
        return sensorQueue.poll();
    }

    private void addToQueue(String token) {
        int usage = 2;
        for(int i = 0; i < usage; i++) {
            sensorQueue.add(token);
        }
    }
}
