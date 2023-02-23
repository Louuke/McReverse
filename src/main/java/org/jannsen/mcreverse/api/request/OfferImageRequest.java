package org.jannsen.mcreverse.api.request;

public class OfferImageRequest extends StreamRequest {

    private final String imageBaseName;

    public OfferImageRequest(String imageBaseName) {
        this.imageBaseName = imageBaseName;
    }

    @Override
    public String getUrl() {
        return "https://de-prod-us-cds-oceofferimages.s3.amazonaws.com/oce3-de-prod/offers/" + imageBaseName;
    }
}
