package icu.jnet.mcd.api.request;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpContent;
import com.google.gson.Gson;
import icu.jnet.mcd.annotation.SensorRequired;

public interface Request {

    String getUrl();

    default HttpContent getContent() {
        return ByteArrayContent.fromString("application/json", new Gson().toJson(this));
    }

    default boolean isSensorRequired() {
        return getClass().isAnnotationPresent(SensorRequired.class)
                && getClass().getDeclaredAnnotation(SensorRequired.class).active();
    }
}
