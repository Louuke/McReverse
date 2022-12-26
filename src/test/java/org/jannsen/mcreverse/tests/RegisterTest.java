package org.jannsen.mcreverse.tests;

import org.jannsen.mcreverse.api.McClient;
import org.jannsen.mcreverse.api.response.RegisterResponse;
import org.jannsen.mcreverse.listener.ClientListener;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    private final Random rand = new Random();
    private final McClient client = new McClient();

    public RegisterTest() {
        client.addActionListener(new ClientListener());
    }

    @Test
    public void registerNoArgTest() {
        RegisterResponse response = client.register(createEmail(), "pass123!");
        assertTrue(response.success(), response.getStatus().getMessage());
    }

    private String createEmail() {
        return rand.ints(48, 123).filter(i -> !(i >= 58 && i <= 96)).limit(12)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString()
                + "@t-online.de";
    }
}
