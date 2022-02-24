package icu.jnet.mcd.api.request;

public class EasterStatusRequest extends EasterRequest {

    public EasterStatusRequest(String token, String userName, String userId) {
        super(token, userName, userId);
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/easter/status";
    }
}
