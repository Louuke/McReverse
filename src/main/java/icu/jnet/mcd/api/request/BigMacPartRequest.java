package icu.jnet.mcd.api.request;

public class BigMacPartRequest extends BigMacStatusRequest {

    public BigMacPartRequest(String token, String userName, String userId) {
        super(token, userName, userId);
    }

    @Override
    public String getUrl() {
        return "https://mcd-gma-prod.mcdonalds.de/mcd-gmarestservice/service/bigmac2022/participate";
    }
}
