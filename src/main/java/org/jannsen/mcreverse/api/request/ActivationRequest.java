package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.Auth;
import org.jannsen.mcreverse.annotation.SensorRequired;
import org.jannsen.mcreverse.api.entity.login.Credentials;

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
}
