package icu.jnet.mcd.api.entity.response;

import com.google.gson.annotations.SerializedName;
import icu.jnet.mcd.api.entity.components.ProductSet;

import java.util.ArrayList;
import java.util.List;

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
        if(!(o instanceof OfferDetails details)) {
            return false;
        }
        return details.getOfferPropositionId() == getOfferPropositionId();
    }

    @Override
    public int hashCode() {
        return getOfferPropositionId();
    }
}
