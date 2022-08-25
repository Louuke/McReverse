package icu.jnet.mcd.api.request;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpContent;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;

public interface Request {

    String getUrl();

    default HttpContent getContent() {
        return ByteArrayContent.fromString("application/json", new Gson().toJson(this));
    }

    default boolean hasAnnotation(Class<? extends Annotation> clazz) {
        return getClass().isAnnotationPresent(clazz) || getClass().getSuperclass().isAnnotationPresent(clazz);
    }

    default int getReadTimeout() {
        return 6000;
    }
}
