package org.jannsen.mcreverse.api.entity.register;

import com.google.gson.annotations.SerializedName;
import org.jannsen.mcreverse.api.entity.profile.Device;

public class RegisterDevice extends Device {

    private @SerializedName("isActive") String active = "Y";
    private @SerializedName("timezone") String timezone = "Europe/Berlin";

    public RegisterDevice(String deviceId) {
        super(deviceId);
        setDeviceIdType("AndroidId");
        setOs("android");
        setOsVersion("11");
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public Device setActive(String active) {
        this.active = active;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    @Override
    public String isActive() {
        return active;
    }
}
