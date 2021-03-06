package com.nearor.framwork.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.nearor.framwork.util.JSONUtil;



import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


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
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeToken typeToken = TypeToken.get(type);
        TypeAdapter<?> adapter = gson.getAdapter(typeToken);
        if ((UploadResponse.class).isAssignableFrom(typeToken.getRawType())) {
            return new UploadResponseConverter<>(adapter);
        } else {
            return new APIResponseConverter<>(adapter, type);
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit)
    {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APIRequestConverter<>(gson, adapter);
    }


}
