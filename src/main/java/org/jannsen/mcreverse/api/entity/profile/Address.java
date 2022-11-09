package org.jannsen.mcreverse.api.entity.profile;

import java.util.ArrayList;
import java.util.List;

public class Address {

    private final String activeInd = "N", addressType = "home", allowPromotions = "N", primaryInd = "Y";
    private final List<Details> details = new ArrayList<>();

    public Address(String zipCode) {
        details.add(new Details(zipCode));
    }

    public List<Details> getDetails() {
        return details;
    }

    public static class Details {
        private final Details.AddressLineDetails addressLineDetails;
        private final String addressLocale = "de-DE";

        public Details(String zipCode) {
            this.addressLineDetails = new Details.AddressLineDetails(zipCode);
        }

        public Details.AddressLineDetails getAddressLineDetails() {
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
