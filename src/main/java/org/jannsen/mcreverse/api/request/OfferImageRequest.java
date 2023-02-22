package org.jannsen.mcreverse.api.request;

import org.jannsen.mcreverse.annotation.Auth;

@Auth(type = Auth.Type.Non)
public class OfferImageRequest extends Request {

    private final String imageBaseName;

    public OfferImageRequest(String imageBaseName) {
        this.imageBaseName = imageBaseName;
    }

    @Override
    public String getUrl() {
        return "https://de-prod-us-cds-oceofferimages.s3.amazonaws.com/oce3-de-prod/offers/" + imageBaseName;
    }
}
