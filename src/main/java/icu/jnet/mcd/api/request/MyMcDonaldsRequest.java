package icu.jnet.mcd.api.request;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MyMcDonaldsRequest implements Request {

    private final CustomerInformation customerInformation;

    public MyMcDonaldsRequest(String userId, String emailAddress) {
        this.customerInformation = new CustomerInformation(userId, emailAddress);
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

        public CustomerInformation(String userId, String emailAddress) {
            this.hashedDcsId = userId;

            String time = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("Z", "");
            subscriptions.add(new Subscription(time, "Y", "24"));
            subscriptions.add(new Subscription(time, "Y", "25"));
            subscriptions.add(new Subscription(time, "N", "21"));
            subscriptions.add(new Subscription(time, "Y", "23"));
            email.add(new Email(emailAddress));
            address.add(new Address());
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
        private String firstName = "M", lastName = "M";

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
        private final List<Details> details = new ArrayList<>();

        public Address() {
            details.add(new Details());
        }

        public static class Details {
            private final AddressLineDetails addressLineDetails = new AddressLineDetails();
            private final String addressLocale = "de-DE";

            public static class AddressLineDetails {
                private final String country = "DE", isAmberZone = "N", isRedZone = "N", zipCode = "24116";
            }
        }
    }
}
