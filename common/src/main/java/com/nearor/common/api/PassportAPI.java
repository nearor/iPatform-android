package com.nearor.common.api;

import com.nearor.common.api.entity.LoginResponse;
import com.nearor.common.api.entity.UnionLoginResponse;
import com.nearor.framwork.network.APICall;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Nearor on 16/6/6.
 */
public interface PassportAPI {
    @FormUrlEncoded
    @POST("/passport/login")
    APICall<LoginResponse> login(@Field("phone") String phone,
                                 @Field("pass") String pass,
                                 @Field("logintype") int loginType);

    @FormUrlEncoded
    APICall<Object> smsCode(@Field("phone") String phone,
                                   @Field("from") String from);

    @FormUrlEncoded
    @POST("/passport/validate")
    APICall<LoginResponse> autoLogin(@Field("token") String token);

    @FormUrlEncoded
    @POST("/passport/logOut")
    APICall<Object> logout(@Field("ut") String token);

    @FormUrlEncoded
    @POST("/passport/unioncodeverify")
    APICall<UnionLoginResponse> unionCodeVerify(@Field("code") String code, @Field("from") String from);

    @FormUrlEncoded
    @POST("/passport/unionlogin")
    APICall<UnionLoginResponse> unionLogin(@Field("phone") String phone,
                                           @Field("pass") String passcode,
                                           @Field("logintype") int loginType,
                                           @Field("code") String code,
                                           @Field("from") String from);
}
