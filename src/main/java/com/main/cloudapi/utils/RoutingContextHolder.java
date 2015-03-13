package com.main.cloudapi.utils;

import org.springframework.util.Assert;

/**
 * Created by mirxak on 23.01.15.
 */
public class RoutingContextHolder {

    private static final ThreadLocal<String> contextHolder =  new ThreadLocal<>();

    public static void setKey(String key) {
        Assert.notNull(key, "key can`t be null");
        contextHolder.set(key);
    }

    public static String getKey() {
        return contextHolder.get();
    }

    public static void clearKeys() {
        contextHolder.remove();
    }

}