package icu.jnet.mcd.api.entity;

import java.lang.reflect.Field;

public class PojoEntity {

    private int _id;

    public void updateId() {
        try {
            Field id = PojoEntity.class.getDeclaredField("_id");
            id.setAccessible(true);
            id.set(this, hashCode());
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
