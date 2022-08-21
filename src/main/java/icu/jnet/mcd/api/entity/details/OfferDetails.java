package icu.jnet.mcd.api.entity.details;

import com.google.gson.annotations.SerializedName;
import icu.jnet.mcd.api.entity.offer.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OfferDetails extends Offer {

    @SerializedName("orderDiscountType") private int orderDiscountType;
    @SerializedName("isExpired") private boolean expired;
    @SerializedName("productSets") private List<ProductSet> productSets;
    @SerializedName("restaurants") private List<String> restaurants;

    public int getOrderDiscountType() {
        return orderDiscountType;
    }

    public boolean isExpired() {
        return expired;
    }

    public List<ProductSet> getProductSets() {
        return productSets != null ? productSets : new ArrayList<>();
    }

    public List<String> getRestaurants() {
        return restaurants != null ? restaurants : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof OfferDetails)) {
            return false;
        }
        OfferDetails details = (OfferDetails) o;
        return details.getOfferPropositionId() == getOfferPropositionId();
    }

    @Override
    public int hashCode() {
        return getOfferPropositionId();
    }
}
