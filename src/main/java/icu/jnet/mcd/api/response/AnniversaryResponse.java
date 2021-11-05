package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class AnniversaryResponse {

    private HashMap<String, String> errors;
    private boolean participated;
    private Prize prize;
    private String success;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public boolean hasParticipated() {
        return participated;
    }

    public Prize getPrize() {
        return prize;
    }

    public String getSuccess() {
        return success;
    }

    public static class Prize {
        private String description, headline, imageUrl, prizeId, prizeUrl, revealVideoUrl, subheadline, type;
        private boolean isAddressFromSent;

        public String getDescription() {
            return description;
        }

        public String getHeadline() {
            return headline;
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

        public String getRevealVideoUrl() {
            return revealVideoUrl;
        }

        public String getSubHeadline() {
            return subheadline;
        }

        public String getType() {
            return type;
        }

        public boolean isAddressFromSent() {
            return isAddressFromSent;
        }
    }
}
