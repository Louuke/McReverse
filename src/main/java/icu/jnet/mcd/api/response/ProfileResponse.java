package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.request.ProfileRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileResponse extends Response {

    private HashMap<String, CustomerInformation> response;

    public CustomerInformation getInfo() {
        return response.get("customerInformation");
    }

    public static class CustomerInformation {

        private final List<ProfileRequest.Address> address = new ArrayList<>();

        private String hashedDcsId, languageCode, marketCode;
        private Base base;
        private Devices[] devices;

        public String getHashedDcsId() {
            return hashedDcsId;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public String getMarketCode() {
            return marketCode;
        }

        public Base getBase() {
            return base;
        }

        public String getZipCode() {
            return address.get(0).getDetails().get(0).getAddressLineDetails().getZipCode();
        }

        public Devices[] getDevices() {
            return devices;
        }
    }

    public static class Base {
        private String firstName, lastName, username;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class Devices {
        private String createdAt, createdBy, deviceId, deviceIdType, id, isActive, language, lastModifiedAt,
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

        public String getIsActive() {
            return isActive;
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
}
