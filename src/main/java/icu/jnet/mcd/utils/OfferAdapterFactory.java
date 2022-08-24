package icu.jnet.mcd.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import icu.jnet.mcd.api.entity.offer.Offer;

import java.io.IOException;
import java.lang.reflect.Field;

public class OfferAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if(!isOffer(type.getRawType())) {
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
                setPriceAndName((Offer) pojo);
                return pojo;
            }
        };
    }

    private String getName(Offer offer) {
        String fullName = offer.getFullName();
        return fullName.contains("\n") ? fullName.split("\n")[0].strip() : fullName;
    }

    private String getPrice(Offer offer) {
        String fullName = offer.getFullName();
        return fullName.contains("\n") ? fullName.split("\n")[1].strip() : "0";
    }

    private void setPriceAndName(Offer offer) {
        try {
            Field name = Offer.class.getDeclaredField("shortName");
            Field price = Offer.class.getDeclaredField("price");
            name.setAccessible(true);
            price.setAccessible(true);
            name.set(offer, getName(offer));
            price.set(offer, getPrice(offer));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isOffer(Class<?> clazz) {
        if(Offer.class.isAssignableFrom(clazz)) {
            return true;
        }
        if(clazz.getSuperclass() != null) {
            return isOffer(clazz.getSuperclass());
        }
        return false;
    }
}
