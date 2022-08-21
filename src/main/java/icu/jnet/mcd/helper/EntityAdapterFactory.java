package icu.jnet.mcd.helper;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import icu.jnet.mcd.api.entity.PojoEntity;

import java.io.IOException;
import java.lang.reflect.Field;

public class EntityAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if(!isEntity(type.getRawType())) {
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
                updateId((PojoEntity) pojo);
                return pojo;
            }
        };
    }

    private void updateId(PojoEntity entity) {
        try {
            Field id = PojoEntity.class.getDeclaredField("_id");
            id.setAccessible(true);
            id.set(entity, entity.hashCode());
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEntity(Class<?> clazz) {
        if(clazz.getSuperclass() != null) {
            if(!PojoEntity.class.isAssignableFrom(clazz.getSuperclass())) {
                return isEntity(clazz.getSuperclass());
            }
            return true;
        }
        return false;
    }
}
