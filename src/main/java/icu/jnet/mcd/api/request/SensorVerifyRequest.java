package icu.jnet.mcd.api.request;

import icu.jnet.mcd.api.McClient;

public class SensorVerifyRequest extends LoginRequest {

    public SensorVerifyRequest() {
        super("test@example.com", "123456", McClient.DEFAULT_DEVICE_ID);
    }

    @Override
    public int getReadTimeout() {
        return 1000;
    }
}