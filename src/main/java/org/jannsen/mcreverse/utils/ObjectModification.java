package org.jannsen.mcreverse.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectModification {

    public static void setField(Object object, String fieldName, Object value) {
        try {
            Field field = getDeclaredField(object, fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
        Class<?> clazz = object.getClass();
        while(clazz != null) {
            if(Arrays.stream(clazz.getDeclaredFields()).anyMatch(f -> f.getName().equals(fieldName))) {
                return clazz.getDeclaredField(fieldName);
            }
            clazz = clazz.getSuperclass();
        }
        throw new NoSuchFieldException();
    }
}
