package org.jannsen.mcreverse.api.entity.login;

public class Credentials {

    private final String loginUsername, type;
    private String password;

    public Credentials(String email, String type) {
        this.loginUsername = email;
        this.type = type;
    }

    public Credentials(String email, String password, String type) {
        this.loginUsername = email;
        this.password = password;
        this.type = type;
    }

    public static class Type {

        public static final String EMAIL = "email";
        public static final String DEVICE = "device";
    }
}
