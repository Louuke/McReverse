package icu.jnet.mcd.api.response;

import java.util.HashMap;
import java.util.List;

public class EasterResponse extends Response {

    private String date;
    private HashMap<String, String> errors;
    private HashMap<String, Object> instantFood;
    private boolean participated;
    private Prize instantPrize;
    private String success;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public boolean hasParticipated() {
        return participated;
    }

    public HashMap<String, Object> getInstantFood() {
        return instantFood;
    }

    public Prize getPrize() {
        return instantPrize;
    }

    public boolean success() {
        return success != null && success.equals("ok");
    }

    public static class Prize {
        private String description, imageUrl, name, prizeButtonText, prizeId, prizeUrl;

        public String getDescription() {
            return description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getPrizeId() {
            return prizeId;
        }

        public String getPrizeUrl() {
            return prizeUrl;
        }

        public String getName() {
            return name;
        }

        public String getPrizeButtonText() {
            return prizeButtonText;
        }
    }
}
