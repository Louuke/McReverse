package icu.jnet.mcd.api.request;

import icu.jnet.mcd.annotation.SensorRequired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SensorRequired
public class ProfileRequest implements Request {

    private final CustomerInformation customerInformation = new CustomerInformation();

    public ProfileRequest() {}

    public ProfileRequest setZipCode(String zipCode) {
        customerInformation.setZipCode(zipCode);
        return this;
    }

    public ProfileRequest useMyMcDonalds(boolean b, String deviceId) {
        customerInformation.useMyMcDonalds(b, deviceId);
        return this;
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
    }

    public static class CustomerInformation {

        private final Base base = new Base();
        private final Audit audit = new Audit();
        private Address[] address;
        private Subscription[] subscriptions;
        private Device[] devices;
        private final Object[] phone = new Object[0];

        private void setZipCode(String zipCode) {
            address = new Address[] {new Address(zipCode)};
        }

        private void useMyMcDonalds(boolean b, String deviceId) {
            String time = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("Z", "");
            subscriptions = new Subscription[] {
                    new Subscription(time, b ? "N" : "Y", "Secondary Processing", "21"),
                    new Subscription(time, b ? "Y" : "N", "Personal Marketing", "23"),
                    new Subscription(time, b ? "Y" : "N", "Loyalty Enrollment", "24"),
                    new Subscription(time, b ? "Y" : "N", "Loyalty Communication", "25")};
        }
    }

    public static class Subscription {
        private final String optInStatus, subscriptionDesc, subscriptionId;
        private String optInDate, optOutDate;

        public Subscription(String optDate, String optInStatus, String subscriptionDesc, String subscriptionId) {
            this.optInStatus = optInStatus;
            this.subscriptionDesc = subscriptionDesc;
            this.subscriptionId = subscriptionId;

            if(optInStatus.equals("Y")) {
                this.optInDate = optDate;
            } else {
                this.optOutDate = optDate;
            }
        }

        public String getSubscriptionId() {
            return subscriptionId;
        }

        public String getOptInStatus() {
            return optInStatus;
        }

        public String getSubscriptionDesc() {
            return subscriptionDesc;
        }
    }

    private static class Audit {
        private final String registrationChannel = "M";
    }

    public static class Base {
        private final String firstName = "M", lastName = "M";
        private String username;

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

    private static class Device {
        private final String deviceId, isActive = "Y", timezone = "Europe/Berlin",
                pushNotificationId = "c9ZdzkqwfgY:APA91bE910Kbyi2japDOTgwPtLM7Sl3UEMg_XdmtjSBpwRTXo4FH-_2pCtVzlbvbNPMNkRJN41B-5LZnK7ZDGH3namwOAXrjh-rhfVfSTXkh9DdFCZv99Joew7LJKokiUiUFuwdKZhnV";

        private Device(String deviceId) {
            this.deviceId = deviceId;
        }
    }

    public static class Address {
        private final String activeInd = "N", addressType = "home", allowPromotions = "N", primaryInd = "Y";
        private final List<Address.Details> details = new ArrayList<>();

        public Address(String zipCode) {
            details.add(new Address.Details(zipCode));
        }

        public List<Address.Details> getDetails() {
            return details;
        }

        public static class Details {
            private final Address.Details.AddressLineDetails addressLineDetails;
            private final String addressLocale = "de-DE";

            public Details(String zipCode) {
                this.addressLineDetails = new Address.Details.AddressLineDetails(zipCode);
            }

            public Address.Details.AddressLineDetails getAddressLineDetails() {
                return addressLineDetails;
            }

            public static class AddressLineDetails {
                private final String country = "DE", isAmberZone = "N", isRedZone = "N", zipCode;

                public AddressLineDetails(String zipCode) {
                    this.zipCode = zipCode;
                }

                public String getZipCode() {
                    return zipCode;
                }
            }
        }
    }
}