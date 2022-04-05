package icu.jnet.mcd.api.request;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
        private List<Address> address;
        private List<Subscription> subscriptions;
        private Device[] devices;

        private void setZipCode(String zipCode) {
            address = new ArrayList<>();
            address.add(new Address(zipCode));
        }

        private void useMyMcDonalds(boolean b, String deviceId) {
            String time = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("Z", "");
            subscriptions = new ArrayList<>();
            subscriptions.add(new Subscription(time, b ? "N" : "Y", "21"));
            subscriptions.add(new Subscription(time, b ? "Y" : "N", "23"));
            subscriptions.add(new Subscription(time, b ? "Y" : "N", "24"));
            subscriptions.add(new Subscription(time, b ? "Y" : "N", "25"));

            if(b) {
                devices = new Device[] {new Device(deviceId)};
            }
        }
    }

    public static class Subscription {
        private final String optInDate, optInStatus, subscriptionId;

        public Subscription(String optInDate, String optInStatus, String subscriptionId) {
            this.optInDate = optInDate;
            this.optInStatus = optInStatus;
            this.subscriptionId = subscriptionId;
        }
    }

    private static class Audit {
        private final String registrationChannel = "M";
    }

    public static class Base {
        private final String firstName = "M", lastName = "M";

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    public static class Device {
        private final String deviceId, pushNotificationId = "c9ZdzkqwfgY:APA91bE910Kbyi2japDOTgwPtLM7Sl3UEMg_XdmtjSBpwRTXo4FH-_2pCtVzlbvbNPMNkRJN41B-5LZnK7ZDGH3namwOAXrjh-rhfVfSTXkh9DdFCZv99Joew7LJKokiUiUFuwdKZhnV";

        public Device(String deviceId) {
            this.deviceId = deviceId;
        }
    }

    public static class Email {
        private final String activeInd = "Y", emailAddress,  primaryInd = "Y", type = "personal", verifiedInd = "Y";

        public Email(String emailAddress) {
            this.emailAddress = emailAddress;
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
