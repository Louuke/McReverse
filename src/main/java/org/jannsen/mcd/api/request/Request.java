package org.jannsen.mcd.api.request;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpContent;
import com.google.gson.Gson;
import org.jannsen.mcd.annotation.Auth;
import org.jannsen.mcd.annotation.ReadTimeout;
import org.jannsen.mcd.annotation.SensorRequired;

import java.lang.annotation.Annotation;
import java.util.Objects;

@ReadTimeout
@Auth(type = Auth.Type.Bearer)
public abstract class Request {

    private static final Gson gson = new Gson();

    public abstract String getUrl();

    public HttpContent getContent() {
        return ByteArrayContent.fromString("application/json", gson.toJson(this));
    }

    public Auth.Type getAuthType() {
        return ((Auth) Objects.requireNonNull(findAnnotation(Auth.class))).type();
    }

    public int getReadTimeout() {
        return ((ReadTimeout) Objects.requireNonNull(findAnnotation(ReadTimeout.class))).value();
    }

    public boolean isTokenRequired() {
        return findAnnotation(SensorRequired.class) != null;
    }

    private Annotation findAnnotation(Class<? extends Annotation> clazz) {
        Class<?> thisClazz = getClass();
        while(thisClazz != Object.class) {
            if(thisClazz.isAnnotationPresent(clazz)) {
                return thisClazz.getAnnotation(clazz);
            } else {
                thisClazz = thisClazz.getSuperclass();
            }
        }
        return null;
    }
}
