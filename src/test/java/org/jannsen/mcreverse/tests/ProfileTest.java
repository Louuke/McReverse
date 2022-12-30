package org.jannsen.mcreverse.tests;

import org.jannsen.mcreverse.TestClient;
import org.jannsen.mcreverse.api.McClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {

    private final McClient client = new TestClient();

    @Test
    public void getProfile() {
        assertTrue(client.getProfile().success());
    }

    @Test
    public void changeZipCode() {
        String oldCold = client.getProfile().getResponse().getZipCode();
        String newCode = !oldCold.equals("11111") ? "11111" : "11112";
        assertTrue(client.changeZipCode(newCode).success());
        assertEquals(newCode, client.getProfile().getResponse().getZipCode());
    }

    @Test
    public void changeFirstName() {
        String firstName = client.getProfile().getResponse().getBase().getFirstName();
        String newName = !firstName.equals("Max") ? "Max" : "Mustermann";
        assertTrue(client.changeName(newName, null).success());
        assertEquals(newName, client.getProfile().getResponse().getBase().getFirstName());
    }

    @Test
    public void changeLastName() {
        String lastName = client.getProfile().getResponse().getBase().getLastName();
        String newName = !lastName.equals("Mustermann") ? "Mustermann" : "Max";
        assertTrue(client.changeName(null, newName).success());
        assertEquals(newName, client.getProfile().getResponse().getBase().getLastName());
    }
}
