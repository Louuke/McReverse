package org.jannsen.mcd.api.entity.profile;

public class Devices {

    private String createdAt, createdBy, deviceId, deviceIdType, id, active, language, lastModifiedAt,
            lastModifiedBy, os, osVersion, sourceId, pushNotificationId;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceIdType() {
        return deviceIdType;
    }

    public String getId() {
        return id;
    }

    public String isActive() {
        return active;
    }

    public String getLanguage() {
        return language;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public String getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getPushNotificationId() {
        return pushNotificationId;
    }
}
