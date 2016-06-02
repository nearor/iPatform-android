package com.nearor.framwork.preference;

import android.text.TextUtils;

import com.nearor.framwork.ValueObject;

/**
 * Created by Nearor on 6/1/16.
 */
public class GlobalValue extends ValueObject{

    private static final String USER_TOKEN_PREFERENCE_KEY = "com.nearor.preference.token";
    private static final String USER_ROLE_PREFERENCE_KET = "com.nearor.preference.user.role";

    private String userToken;
    private String userRole;

    private boolean isRoleApp;

    private String orderId;

    private static class Holder {
        static GlobalValue INSTANCE = new GlobalValue();
    }

    public static GlobalValue getInstance(){
        return Holder.INSTANCE;
    }

    public boolean isLogin(){
        return !TextUtils.isEmpty(getUserRole());
    }

    public String getUserToken() {
        if(userToken == null){
            userToken = (String) Storage.sharedInstance().get(USER_TOKEN_PREFERENCE_KEY);
        }
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
        Storage.sharedInstance().add(USER_TOKEN_PREFERENCE_KEY,userToken);
    }

    public String getUserRole() {
        if(userRole == null){
            userRole = (String) Storage.sharedInstance().get(USER_ROLE_PREFERENCE_KET);
        }
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
        Storage.sharedInstance().add(USER_ROLE_PREFERENCE_KET,userRole);
    }
}
