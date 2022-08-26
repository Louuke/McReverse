package icu.jnet.mcd.utils;

import icu.jnet.mcd.api.McClient;
import icu.jnet.mcd.api.entity.login.Authorization;
import icu.jnet.mcd.utils.listener.ClientActionModel;
import icu.jnet.mcd.utils.listener.ClientStateListener;

public class ClientVerifier implements ClientStateListener{


    public ClientVerifier(ClientActionModel clientAction) {
        clientAction.addStateListener(this);
    }

    @Override
    public void authChanged(Authorization authorization) {
        McClient client = new McClient();
        client.setAuthorization(authorization);
        client.getPoints();
    }

    @Override
    public void newSensorToken(String token) {
        if(token != null && !token.isEmpty()) {
            McClient client = new McClient();
            client.verifyNextToken();
        }
    }
}
