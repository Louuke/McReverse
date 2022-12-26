package org.jannsen.mcreverse;

import org.jannsen.mcreverse.api.McClient;
import org.jannsen.mcreverse.listener.ClientListener;

public class TestClient extends McClient {

    private static final String EMAIL = "";
    private static final String PASS = "";
    private static final String DEVICE_ID = "";

    public TestClient() {
        addActionListener(new ClientListener());
        login(EMAIL, PASS, DEVICE_ID);
    }
}
