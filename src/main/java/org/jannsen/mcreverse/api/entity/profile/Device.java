package org.jannsen.mcreverse.api.entity.profile;

public class Device {

    private String createdAt, createdBy, deviceId, deviceIdType, id, active, language, lastModifiedAt,
            lastModifiedBy, os, osVersion, sourceId, pushNotificationId;

    public Device(String deviceId) {
        this.deviceId = deviceId;
    }

    public Device setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Device setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Device setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Device setDeviceIdType(String deviceIdType) {
        this.deviceIdType = deviceIdType;
        return this;
    }

    public Device setId(String id) {
        this.id = id;
        return this;
    }

    public Device setActive(String active) {
        this.active = active;
        return this;
    }

    public Device setLanguage(String language) {
        this.language = language;
        return this;
    }

    public Device setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
        return this;
    }

    public Device setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public Device setOs(String os) {
        this.os = os;
        return this;
    }

    public Device setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public Device setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public Device setPushNotificationId(String pushNotificationId) {
        this.pushNotificationId = pushNotificationId;
        return this;
    }

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
