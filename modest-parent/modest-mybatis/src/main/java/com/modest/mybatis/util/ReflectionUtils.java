package com.modest.mybatis.util;

import java.lang.reflect.Field;

/**
 * 通过反射来获取一个对象的值。
 * @author 庄濮向 Edmond Chuang
 */
public final class ReflectionUtils extends org.springframework.util.ReflectionUtils {

    public static Object getFieldValue(final Object target, final String fieldName) {
        Field field = findField(target.getClass(), fieldName);
        boolean accessible = field.isAccessible();
        makeAccessible(field);
        Object value = getField(field, target);
        field.setAccessible(accessible);
        return value;
    }
}
