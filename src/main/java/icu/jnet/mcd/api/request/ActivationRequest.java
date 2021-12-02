package icu.jnet.mcd.api.request;

public class ActivationRequest {

    private final String activationCode, deviceId;
    private final Credentials credentials;

    public ActivationRequest(String email, String activationCode, String deviceId, String type) {
        this.activationCode = activationCode;
        this.credentials = new Credentials(email, type);
        this.deviceId = deviceId;
    }

    private static class Credentials {
        private final String loginUsername, type;

        public Credentials(String email, String type) {
            this.loginUsername = email;
            this.type = type;
        }
    }
}
