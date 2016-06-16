package com.nearor.framwork.network;

import com.google.gson.JsonSyntaxException;
import com.nearor.framwork.util.Lg;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 */
class APIResponseInterceptor implements Interceptor {

    private static final String TAG = Lg.makeLogTag(APIResponseInterceptor.class);

    private APIResponseErrorHandler apiResponseErrorHandler;

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (response.code() != 200) {
            throw new IOException(response.code() + response.message());
        }

        String type = "";
        if (response.body().contentType() != null) {
            type = response.body().contentType().type();
        }

        String bodyString = response.body().string();
        Lg.d(TAG, "Response: {code:" + response.code() + ", body:" + bodyString + "}");

        ResponseBody newBody = ResponseBody.create(response.body().contentType(), bodyString);;

        if (RetrofitHelper.isFileUploadRequest(chain.request())) {
            try {
                APIResponse apiResponse = getAPIResponseFromBody(bodyString);
                if (apiResponse != null) {
                    if (!apiResponse.isSuccessed()) {
                        if (apiResponseErrorHandler != null
                                && apiResponseErrorHandler.handleAPIError(apiResponse)) {
                            // 错误处理完成后重新发起原始请求
                            Request request = chain.request().newBuilder().build();
                            Lg.d(TAG, "Try request again: " + request.toString());
                            newBody = chain.proceed(APIRequestInterceptor.signatureRequest(request)).body();

                        }
                    }
                } else {
                    throw new NullPointerException("Response body is null!");
                }
            } catch (Exception e) {
                Lg.e(TAG, e.getMessage());
                throw new IOException(e);
            }
        }

        return response.newBuilder().body(newBody).build();
    }

    private APIResponse getAPIResponseFromBody(String body) throws IOException {
        try {
            return APIResponseConverter.parseAPIResponse(body);
        } catch (JsonSyntaxException e) {
            Lg.e(TAG, "JSON format error: " + body);
            throw new IOException(e);
        }
    }

    public void setApiResponseErrorHandler(APIResponseErrorHandler apiResponseErrorHandler) {
        this.apiResponseErrorHandler = apiResponseErrorHandler;
    }
}
