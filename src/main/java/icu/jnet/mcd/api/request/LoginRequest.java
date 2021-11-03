package icu.jnet.mcd.api.request;

public class LoginRequest {

    private final Credentials credentials;
    private final String deviceId = "75408e58622a88c6";

    public LoginRequest(String email, String password) {
        this.credentials = new Credentials(email, password);
    }

    public static class Credentials {
        private final String loginUsername, password, type = "email";

        public Credentials(String email, String password) {
            this.loginUsername = email;
            this.password = password;
        }
    }
}
