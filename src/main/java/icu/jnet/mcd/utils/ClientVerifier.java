package icu.jnet.mcd.utils;

import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.utils.listener.ClientStateListener;

public class ClientVerifier implements ClientStateListener{

    private final McClient client;

    public ClientVerifier(McClient client) {
        client.addStateListener(this);
        this.client = client;
    }

    @Override
    public void authChanged() {
        client.getPoints();
    }

    @Override
    public void newSensorToken(String token) {
        if(token == null || token.isEmpty()) {
            return;
        }
        McClient client = new McClient();
        client.verifyNextToken();
    }
}
