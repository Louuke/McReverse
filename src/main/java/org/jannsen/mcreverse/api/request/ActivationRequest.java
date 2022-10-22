package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.Auth;
import org.jannsen.mcreverse.annotation.SensorRequired;

@SensorRequired
@Auth(type = Auth.Type.BasicBearer)
public class ActivationRequest extends Request {

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
