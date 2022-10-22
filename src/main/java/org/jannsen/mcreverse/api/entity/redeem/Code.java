package org.jannsen.mcreverse.api.entity.redeem;

import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("expirationTime") private String expirationTimeUTC;
    @SerializedName("barCodeContent") private String barCodeContent;
    @SerializedName("randomCode") private String randomCode;
    private long expirationTimeUnix;

    public String getBarCodeContent() {
        return barCodeContent;
    }

    public String getExpirationTimeUTC() {
        return expirationTimeUTC;
    }

    public String getRandomCode() {
        return randomCode != null ? randomCode : "";
    }

    public long getExpirationTimeUnix() {
        return expirationTimeUnix;
    }
}
