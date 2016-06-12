package com.nearor.framwork.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import retrofit.Retrofit;

/**
 * @author nearor.
 */
public class RetrofitHelper {

    public static final int TIME_OUT_SECONDS = 60;

    private Retrofit retrofit;

    private Map<Class, Object> mCachedServices = new HashMap<>();

    private static final RetrofitHelper sSharedInstance = new RetrofitHelper();

    public RetrofitHelper() {}

    public void init(String baseURL, APIResponseErrorHandler apiResponseErrorHandler) {
        // 设置 HTTP client
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(new APIRequestInterceptor());

        // 设置 response 拦截器
        APIResponseInterceptor apiResponseInterceptor = new APIResponseInterceptor();
        apiResponseInterceptor.setApiResponseErrorHandler(apiResponseErrorHandler);
        okHttpClient.interceptors().add(apiResponseInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addCallAdapterFactory(new APICallAdapterFactory())
                .addConverterFactory(APIServiceConvertFactory.create())
                .build();
    }

    public static RetrofitHelper getSharedInstance() {
        return sSharedInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> apiInterfaceClass) {
        if (mCachedServices.containsKey(apiInterfaceClass)
                && mCachedServices.get(apiInterfaceClass) != null)
        {
            return (T) mCachedServices.get(apiInterfaceClass);
        } else {
            T apiImp = retrofit.create(apiInterfaceClass);
            mCachedServices.put(apiInterfaceClass, apiImp);
            return apiImp;
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static boolean isFileUploadRequest(Request request) {
        if (request == null) {
            return false;
        } else {
            return request.body() == null || request.body().contentType() == null || !request.body().contentType().type().equalsIgnoreCase("multipart");
        }
    }
}
