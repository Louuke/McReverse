package icu.jnet.mcd.api.request;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public interface Request {

    String getUrl();

    default HttpEntity getContent() {
        return new StringEntity(new Gson().toJson(this), ContentType.APPLICATION_JSON);
    }
}
