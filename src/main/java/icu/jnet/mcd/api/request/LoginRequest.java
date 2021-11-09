package icu.jnet.mcd.api.request;

public class LoginRequest {

    private final Credentials credentials;
    private String deviceId;

    public LoginRequest(String email, String password, String deviceId) {
        this.credentials = new Credentials(email, password);
        this.deviceId = deviceId;
    }

    public static class Credentials {
        private final String loginUsername, password, type = "email";

        public Credentials(String email, String password) {
            this.loginUsername = email;
            this.password = password;
        }
    }
}
