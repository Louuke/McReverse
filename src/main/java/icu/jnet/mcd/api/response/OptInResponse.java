package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class OptInResponse extends Response {

    private HashMap<String, String> response;

    public String getResponse() {
        return response != null && response.containsKey("message") ? response.get("message") : "";
    }
}
