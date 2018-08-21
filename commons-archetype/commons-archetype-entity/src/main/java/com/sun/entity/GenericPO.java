package com.sun.entity;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通用实体,提供实体常用属性
 *
 * @author : Sun
 * @date : 2018/7/16 16:15
 */
public interface GenericPO<PK> extends PO {
    String ID = "id";

    String PROPERTIES = "properties";

    PK getId();

    void setId(PK id);

    default Map<String, Object> getProperties() {
        return null;
    }

    default void setProperties(Map<String, Object> properties) {

    }

    @SuppressWarnings("unchecked")
    default <T> T getProperty(String propertyName, T defaultValue) {
        Map<String, Object> map = getProperties();
        if (map == null) return null;
        return (T) map.getOrDefault(propertyName, defaultValue);
    }

    default <T> T getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }

    default void setProperty(String propertyName, Object value) {
        Map<String, Object> map = getProperties();
        if (map == null) {
            map = new LinkedHashMap<>();
            setProperties(map);
        }
        map.put(propertyName, value);
    }

}
