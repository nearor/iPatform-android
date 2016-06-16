package com.nearor.framwork.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;



import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * 接口返回的数据流转换成业务对象（不包含 rtn_code, rtn_msg 等信息）。
 * 如果接口返回业务错误则抛出 {@link APIResponseException}
 */
class APIResponseConverter<T> implements Converter<ResponseBody, T> {

    private final TypeAdapter<T> adapter;
    private final Type mType;

    APIResponseConverter(TypeAdapter<T> adapter, Type type) {
        this.adapter = adapter;
        mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        
        try {
            APIResponse apiResponse = APIResponseConverter.parseAPIResponse(jsonString);
            if (apiResponse == null) {
                throw new APIResponseException(jsonString);
            } else if (!apiResponse.isSuccessed()) {
                throw new APIResponseException(apiResponse.getLocalizedMessage(), apiResponse);
            } else {
                String data = apiResponse.getData();
                if (data != null) {
                    if (String.class.equals(mType) && apiResponse.getData() instanceof String) {
                        return (T)(data);
                    }
                } else {
                    data = "{}";
                }
                return adapter.fromJson(data);
            }
        } catch (JsonSyntaxException e) {
            throw new APIResponseException(e);
        }
    }

    public static APIResponse parseAPIResponse(String apiResponseJsonString) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(APIResponse.class, new APIResponseGSONParseAdapter())
                .create();
        return gson.fromJson(apiResponseJsonString, APIResponse.class);
    }

    private static class APIResponseGSONParseAdapter implements JsonDeserializer<APIResponse> {

        @Override
        public APIResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            APIResponse response = new APIResponse();
            JsonObject responseJsonObject = json.getAsJsonObject();

            JsonElement jsonElement = responseJsonObject.get("rtn_code");
            response.setCode(jsonElement == null ? null : jsonElement.getAsString());

            jsonElement = responseJsonObject.get("data");
            response.setData(jsonElement == null ? null : jsonElement.toString());

            jsonElement = responseJsonObject.get("rtn_ext");
            response.setExt(jsonElement == null ? null : jsonElement.getAsString());

            jsonElement = responseJsonObject.get("rtn_ftype");
            response.setFtype(jsonElement == null ? null : jsonElement.getAsString());

            jsonElement = responseJsonObject.get("rtn_msg");
            response.setMsg(jsonElement == null ? null : jsonElement.getAsString());

            jsonElement = responseJsonObject.get("rtn_tip");
            response.setTip(jsonElement == null ? null : jsonElement.getAsString());
            return response;
        }
    }
}
