package icu.jnet.mcd.api.response;

import icu.jnet.mcd.api.response.status.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BonusPointsResponse extends Response {

    private HashMap<String, List<BonusPoints>> response;

    public BonusPointsResponse(Status status) {
        super(status);
    }

    public List<BonusPoints> getBonuses() {
        return response != null ? response.get("bonusPoints") : new ArrayList<>();
    }

    public static class BonusPoints {

        private String bonusType, campaignEndDate, campaignStartDate, imageURL, offerValue, offerValueType,
                termsAndConditions, title;
        private int campaignId;
        private boolean optInRequired, optedIn;

        public String getBonusType() {
            return bonusType;
        }

        public String getCampaignEndDate() {
            return campaignEndDate;
        }

        public String getCampaignStartDate() {
            return campaignStartDate;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getOfferValue() {
            return offerValue;
        }

        public String getOfferValueType() {
            return offerValueType;
        }

        public String getTermsAndConditions() {
            return termsAndConditions;
        }

        public String getTitle() {
            return title;
        }

        public int getCampaignId() {
            return campaignId;
        }

        public boolean isOptInRequired() {
            return optInRequired;
        }

        public boolean isOptedIn() {
            return optedIn;
        }
    }
}
