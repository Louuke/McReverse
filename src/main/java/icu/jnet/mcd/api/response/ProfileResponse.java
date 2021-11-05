package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class ProfileResponse extends Response {

    private HashMap<String, CustomerInformation> response;

    public CustomerInformation getInfo() {
        return response.get("customerInformation");
    }

    public static class CustomerInformation {

        private String hashedDcsId, languageCode, marketCode;
        private Base base;

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
}
