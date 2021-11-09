package icu.jnet.mcd.api.request;

public class ActivationRequest {

    private final String activationCode;
    private final Credentials credentials;
    private String deviceId;

    public ActivationRequest(String email, String activationCode, String deviceId) {
        this.activationCode = activationCode;
        this.credentials = new Credentials(email);
        this.deviceId = deviceId;
    }

    public static class Credentials {
        private final String loginUsername, type = "email";

        public Credentials(String email) {
            this.loginUsername = email;
        }
    }
}
