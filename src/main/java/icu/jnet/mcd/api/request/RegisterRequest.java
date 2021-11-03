package icu.jnet.mcd.api.request;

import java.util.HashMap;

public class RegisterRequest {

    private Policies policies = new Policies();
    private Device device = new Device();
    private Audit audit = new Audit();
    private Address address;
    private Credentials credentials;
    private final String emailAddress, firstName = "Max", lastName = "Mustermann";
    private final boolean optInForMarketing = false;

    public RegisterRequest(String email, String password, String zipCode, String country) {
        this.emailAddress = email;
        address = new Address(zipCode, country);
        credentials = new Credentials(email, password);
    }

    private static class Address {
        private final String zipCode, country;

        private Address(String zipCode, String country) {
            this.zipCode = zipCode;
            this.country = country;
        }
    }

    private static class Audit {
        private final String registrationChannel = "M";
    }

    private static class Credentials {
        private final String loginUsername, password, type = "email";

        private Credentials(String email, String password) {
            this.loginUsername = email;
            this.password = password;
        }
    }

    private static class Device {
        private final String deviceId = "75408e58622a88c6", deviceIdType = "AndroidId", isActive = "Y", os = "android",
            osVersion = "9", timezone = "America/New_York";
    }

    private static class Policies {
        private HashMap<String, Boolean> acceptancePolicies = new HashMap<>();

        public Policies() {
            acceptancePolicies.put("1", true);
            acceptancePolicies.put("4", true);
        }
    }
}
