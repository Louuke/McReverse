package icu.jnet.mcd.api.request;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ProfileRequest implements Request {

    private CustomerInformation customerInformation = null;

    public ProfileRequest() {}

    public ProfileRequest(String userId, String emailAddress, String zipCode) {
        this.customerInformation = new CustomerInformation(userId, emailAddress, zipCode);
    }

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
    }

    public static class CustomerInformation {

        private final String hashedDcsId;
        private final Base base = new Base();
        private final Audit audit = new Audit();
        private final List<Address> address = new ArrayList<>();
        private final List<Email> email = new ArrayList<>();
        private final List<Subscription> subscriptions = new ArrayList<>();

        public CustomerInformation(String userId, String emailAddress, String zipCode) {
            this.hashedDcsId = userId;

            String time = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("Z", "");
            subscriptions.add(new Subscription(time, "Y", "24"));
            subscriptions.add(new Subscription(time, "Y", "25"));
            subscriptions.add(new Subscription(time, "N", "21"));
            subscriptions.add(new Subscription(time, "Y", "23"));
            email.add(new Email(emailAddress));
            address.add(new Address(zipCode));
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
