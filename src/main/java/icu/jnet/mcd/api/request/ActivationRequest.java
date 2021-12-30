package icu.jnet.mcd.api.request;

public class ActivationRequest implements Request {

    private final String activationCode, deviceId;
    private final Credentials credentials;

    public ActivationRequest(String email, String activationCode, String deviceId, String type) {
        this.activationCode = activationCode;
        this.credentials = new Credentials(email, type);
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/activation";
    }

    private static class Credentials {
        private final String loginUsername, type;

        public Credentials(String email, String type) {
            this.loginUsername = email;
            this.type = type;
        }
    }
}
