package icu.jnet.mcd.api.request;

public class ActivationRequest {

    private final String activationCode;
    private final Credentials credentials;

    public ActivationRequest(String email, String activationCode) {
        this.activationCode = activationCode;
        this.credentials = new Credentials(email);
    }

    public static class Credentials {
        private final String loginUsername, type = "email";

        public Credentials(String email) {
            this.loginUsername = email;
        }
    }
}
