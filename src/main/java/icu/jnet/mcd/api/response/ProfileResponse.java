package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.request.ProfileRequest;
import icu.jnet.mcd.api.response.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileResponse extends Response {

    private HashMap<String, CustomerInformation> response;

    public ProfileResponse(Status status) {
        super(status);
    }

    public CustomerInformation getInfo() {
        return response != null ? response.get("customerInformation") : new CustomerInformation();
    }

    public static class CustomerInformation {

        private ProfileRequest.Address[] address;

        private String hashedDcsId, languageCode, marketCode;
        private ProfileRequest.Base base;
        private Devices[] devices;

        private List<ProfileRequest.Subscription> subscriptions;

        public String getHashedDcsId() {
            return hashedDcsId;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public String getMarketCode() {
            return marketCode;
        }

        public ProfileRequest.Base getBase() {
            return base;
        }

        public String getZipCode() {
            return address != null ? address[0].getDetails().get(0).getAddressLineDetails().getZipCode() : "00000";
        }

        public Devices[] getDevices() {
            return devices;
        }

        public List<ProfileRequest.Subscription> getSubscriptions() {
            return subscriptions != null ? subscriptions : new ArrayList<>();
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
