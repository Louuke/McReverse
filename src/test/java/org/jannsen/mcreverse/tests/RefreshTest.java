package org.jannsen.mcreverse.tests;

import org.jannsen.mcreverse.TestClient;
import org.jannsen.mcreverse.api.McClient;
import org.jannsen.mcreverse.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefreshTest {

    private final McClient client = new TestClient();

    @Test
    public void firstRefresh() {
        assertTrue(client.getPoints().success());
        Utils.waitMill(900000);
        assertTrue(client.getPoints().success());
    }
}
