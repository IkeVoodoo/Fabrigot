package me.ikevoodoo.fabrigot.mappers;

import java.lang.reflect.Field;
import java.util.HashMap;

public class NameMapper {

    private static final HashMap<Class<?>, HashMap<String, Field>> MAPPED_FIELDS = new HashMap<>();

    public static void map(Class<?> clazz) {
        if (MAPPED_FIELDS.containsKey(clazz)) return;

    }

    public static <T> T byName(Class<?> clazz, String name) {
        NameMapper.map(clazz);
        return (T)MAPPED_FIELDS.get(clazz).get(name);
    }
}
