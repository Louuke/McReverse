package icu.jnet.mcd.api.request;

public class ProfileRequest implements Request {

    @Override
    public String getUrl() {
        return "https://eu-prod.api.mcd.com/exp/v1/customer/profile";
    }
}