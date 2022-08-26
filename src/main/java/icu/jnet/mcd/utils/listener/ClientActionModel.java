package icu.jnet.mcd.utils.listener;

import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.utils.SensorCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientActionModel {

    private static final SensorCache sensorCache = SensorCache.getInstance();
    private final List<ClientStateListener> stateListener = new ArrayList<>();

    public String getSensorToken() {
        if(!sensorCache.isTokenAvailable()) {
            String token = queryToken();
            sensorCache.saveSensorToken(token);
            notifyListener(Action.NEW_SENSOR_TOKEN, token);
        }
        return sensorCache.pollToken();
    }

    public <T> void notifyListener(Action action, T payload) {
        switch (action) {
            case JWT_ERROR -> stateListener.forEach(listener -> listener.loginExpired((String) payload));
            case AUTHORIZATION_CHANGED -> stateListener.forEach(listener -> listener.authChanged((Authorization) payload));
            case NEW_SENSOR_TOKEN -> stateListener.forEach(listener -> listener.newSensorToken((String) payload));
        }
    }

    public void addStateListener(ClientStateListener listener) {
        stateListener.add(listener);
    }

    private String queryToken() {
        return stateListener.stream()
                .map(ClientStateListener::tokenRequired)
                .filter(Objects::nonNull)
                .findAny().orElse("");
    }
}
