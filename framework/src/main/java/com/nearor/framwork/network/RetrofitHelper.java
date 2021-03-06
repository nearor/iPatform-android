package com.nearor.framwork.network;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author nearor.
 */
public class RetrofitHelper {

    public static final int TIME_OUT_SECONDS = 60;

    private Retrofit retrofit;

    private Map<Class, Object> mCachedServices = new HashMap<>();

    private static  RetrofitHelper sSharedInstance ;

    public RetrofitHelper() {}

    public void init(String baseURL, APIResponseErrorHandler apiResponseErrorHandler) {
        // 设置 HTTP client
        // 设置 response 拦截器

        APIResponseInterceptor apiResponseInterceptor = new APIResponseInterceptor();
        apiResponseInterceptor.setApiResponseErrorHandler(apiResponseErrorHandler);
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new APIRequestInterceptor())
                .addInterceptor(apiResponseInterceptor)
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(new APICallAdapterFactory())
                .addConverterFactory(APIServiceConvertFactory.create())
                .build();
    }

    //单例线程锁封装
    public static RetrofitHelper getSharedInstance() {
        if(sSharedInstance == null){
            synchronized (RetrofitHelper.class){
                if(sSharedInstance == null)
                    sSharedInstance = new RetrofitHelper();
            }
        }
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
