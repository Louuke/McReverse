package icu.jnet.mcd.api.entity.redeem;

public class Code {

    private String barCodeContent, expirationTime, randomCode;

    public String getBarCodeContent() {
        return barCodeContent;
    }

    public String getExpirationTime() {
        return expirationTime != null ? expirationTime : "1970-01-01T00:00:00Z";
    }

    public String getRandomCode() {
        return randomCode != null ? randomCode : "";
    }
}
