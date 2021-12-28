package icu.jnet.mcd.api.response;

import java.util.HashMap;
import java.util.List;

public class CalendarStateResponse {

    private HashMap<String, String> errors;
    private List<InstantPrize> instantPrizes;
    private String success, userId;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public List<InstantPrize> getInstantPrizes() {
        return instantPrizes;
    }

    public boolean success() {
        return success != null && success.equals("ok");
    }

    public String getUserId() {
        return userId;
    }

    public static class InstantPrize {

        private String description, imageUrl, name, prizeId, prizeUrl;
        private boolean isAddressUploaded;

        public String getDescription() {
            return description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getPrizeId() {
            return prizeId;
        }

        public String getName() {
            return name;
        }

        public String getPrizeUrl() {
            return prizeUrl;
        }

        public boolean isAddressUploaded() {
            return isAddressUploaded;
        }
    }
}
