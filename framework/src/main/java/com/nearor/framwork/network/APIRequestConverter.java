package com.nearor.framwork.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;



import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;


/**
 *
 */
class APIRequestConverter<T> implements Converter<T, RequestBody> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    APIRequestConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        RequestBody body;
        if (value != null) {
            if (value instanceof RequestBody) {
                body = (RequestBody) value;
            } else {
                body = RequestBody.create(MediaType.parse("multipart/form-data"), value.toString());
            }
        } else {
            body = RequestBody.create(null, "");
        }
        return body;
    }
}
