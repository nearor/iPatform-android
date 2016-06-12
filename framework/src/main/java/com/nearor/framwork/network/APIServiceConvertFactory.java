package com.nearor.framwork.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.nearor.framwork.util.JSONUtil;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;


import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;
import retrofit.Retrofit;


/**
 *
 */
class APIServiceConvertFactory extends Converter.Factory {

    public static APIServiceConvertFactory create() {
        return new APIServiceConvertFactory(JSONUtil.gson);
    }

    private final Gson gson;

    private APIServiceConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations
                                                          ) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APIRequestConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        TypeToken typeToken = TypeToken.get(type);
        TypeAdapter<?> adapter = gson.getAdapter(typeToken);
        if ((UploadResponse.class).isAssignableFrom(typeToken.getRawType())) {
            return new UploadResponseConverter<>(adapter);
        } else {
            return new APIResponseConverter<>(adapter, type);
        }
    }
}
