package org.jannsen.mcreverse.api.request;

public class OfferImageRequest extends StreamRequest {

    private final String imageUrl;

    public OfferImageRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getUrl() {
        return imageUrl;
    }
}
