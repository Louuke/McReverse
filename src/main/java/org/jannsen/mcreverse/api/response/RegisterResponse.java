package org.jannsen.mcreverse.api.response;

import org.jannsen.mcreverse.api.response.status.Status;

public class RegisterResponse extends Response {

    private String deviceId;

    public RegisterResponse(Status status) {
        super(status);
    }

    public RegisterResponse(Status status, String deviceId) {
        super(status);
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
