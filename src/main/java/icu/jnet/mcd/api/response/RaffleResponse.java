package icu.jnet.mcd.api.response;

public class RaffleResponse extends Response {

    private boolean participated;
    private String success;

    public boolean hasParticipated() {
        return participated;
    }

    public boolean success() {
        return success != null && success.equals("ok");
    }
}
