package icu.jnet.mcd.api.request;

public class AnniversaryRequest {

    private final String formId = "peak2021civv", token, userName, userId;

    public AnniversaryRequest(String token, String userName, String userId) {
        this.token = token;
        this.userName = userName;
        this.userId = userId;
    }

    public static class AnniversaryPartRequest extends AnniversaryRequest {

        private final String prizeId;

        public AnniversaryPartRequest(String token, String userName, String userId, String prizeId) {
            super(token, userName, userId);

            this.prizeId = prizeId;
        }
    }
}
