package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.Auth;
import org.jannsen.mcreverse.annotation.SensorRequired;
import org.jannsen.mcreverse.api.entity.login.*;

@SensorRequired
@Auth(type = Auth.Type.BasicBearer)
public class LoginRequest extends Request {

    private final ClientInfo clientInfo;
    private final Credentials credentials;
    private final String deviceId;

    public LoginRequest(String email, String password, String deviceId) {
        this.clientInfo = new ClientInfo(deviceId);
        this.credentials = new Credentials(email, password, Credentials.Type.EMAIL);
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/login";
    }
}
