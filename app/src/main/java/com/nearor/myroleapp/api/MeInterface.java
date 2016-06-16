package com.nearor.myroleapp.api;

import com.nearor.framwork.network.APICall;
import com.nearor.myroleapp.api.entity.MeCenterData;



import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Nearor on 16/6/14.
 */
public interface MeInterface {
    @GET("/designer/center")
    APICall<MeCenterData> getData();

    //Observable<MeCenterData> getData();
}
