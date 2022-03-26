package icu.jnet.mcd.api.response;

import java.util.HashMap;

public class BigMacOptInResponse extends Response {

    private HashMap<String, String> response;

    public String getMessage() {
        return response.get("message");
    }
}
