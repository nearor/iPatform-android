package com.nearor.common.api;


import com.nearor.common.api.entity.LaunchData;
import com.nearor.framwork.network.APICall;
import com.nearor.framwork.network.SignatureKey;

import retrofit.http.GET;

/**
 * @author vectxi@gmail.com on 2/17/16.
 */
public interface LaunchAPI {
    @GET("/mobile/launch")
    APICall<LaunchData> getLaunchData();

    @GET("/mobile/getkey")
    APICall<SignatureKey> getkey();
}
