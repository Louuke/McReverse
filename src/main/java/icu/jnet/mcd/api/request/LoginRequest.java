package icu.jnet.mcd.api.request;

import icu.jnet.mcd.annotation.SensorRequired;

import java.util.HashMap;
import java.util.Map;

@SensorRequired
public class LoginRequest implements Request {

    private final ClientInfo clientInfo;
    private final Credentials credentials;
    private final String deviceId;

    public LoginRequest(String email, String password, String deviceId) {
        this.clientInfo = new ClientInfo(deviceId);
        this.credentials = new Credentials(email, password);
        this.deviceId = deviceId;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/login";
    }

    public static class Credentials {
        private final String loginUsername, password, type = "email";

        public Credentials(String email, String password) {
            this.loginUsername = email;
            this.password = password;
        }
    }

    private static class ClientInfo {
        private final Map<String, String> device = new HashMap<>();

        private ClientInfo(String deviceId) {
            device.put("deviceUniqueId", deviceId);
            device.put("os", "android");
            device.put("osVersion", "11");
        }
    }
}
