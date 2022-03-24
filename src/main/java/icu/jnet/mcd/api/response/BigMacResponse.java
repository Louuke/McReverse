package icu.jnet.mcd.api.response;

public class BigMacResponse extends Response {

    private Coupon coupon;
    private boolean participated;

    public Coupon getCoupon() {
        return coupon;
    }

    public boolean hasParticipated() {
        return participated;
    }

    public static class Coupon {

        private String beforeHeadline, buttonText, buttonUrl, headline, imageUrl, subheadline;

        public String getBeforeHeadline() {
            return beforeHeadline;
        }

        public String getButtonText() {
            return buttonText;
        }

        public String getButtonUrl() {
            return buttonUrl;
        }

        public String getHeadline() {
            return headline;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getSubheadline() {
            return subheadline;
        }
    }
}
