package com.nearor.framwork.network;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;



import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * 接口返回的数据流转换成业务对象（不包含 rtn_code, rtn_msg 等信息）。
 * 如果接口返回业务错误则抛出 {@link APIResponseException}
 */
class UploadResponseConverter<T> implements Converter<ResponseBody, T> {

    private final TypeAdapter<T> adapter;

    UploadResponseConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            return adapter.fromJson(value.charStream());
        }  catch (JsonSyntaxException e) {
            throw new IOException(e);
        }
    }
}
