package org.jannsen.mcreverse.api.response.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;

public abstract class AbstractAdapterFactory implements TypeAdapterFactory {

    private final Class<?> adapterType;

    public AbstractAdapterFactory(Class<?> adapterType) {
        this.adapterType = adapterType;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if(!isOfType(type.getRawType())) {
            return null;
        }
        TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<>() {

            @Override
            public void write(JsonWriter out, T pojo) throws IOException {
                delegateAdapter.write(out, pojo);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                T pojo = delegateAdapter.read(in);
                modifyPojo(pojo);
                return pojo;
            }
        };
    }

    private boolean isOfType(Class<?> deserialize) {
        while(deserialize != null && deserialize != Object.class) {
            if(adapterType.isAssignableFrom(deserialize)) {
                return true;
            } else {
                deserialize = deserialize.getSuperclass();
            }
        }
        return false;
    }

    public void setField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void modifyPojo(Object pojo);
}
