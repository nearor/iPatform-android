package com.nearor.framwork.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 *
 */
public class JSONUtil {

    /**
     * 不解析 static, transient, volatile 等修饰的变量
     */
    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
            .create();

    /**
     * 不解析 static, transient, volatile 等修饰的变量.
     * 同时支持 @Expose 声明
     */
    public static Gson gsonWithExpose = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
            .excludeFieldsWithoutExposeAnnotation()
            .create();
}
