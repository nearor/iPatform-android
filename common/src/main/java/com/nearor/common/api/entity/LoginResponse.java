package com.nearor.common.api.entity;

import com.nearor.framwork.ValueObject;

/**
 * Created by Nearor on 16/6/6.
 */
public class LoginResponse extends ValueObject{

    /**
     *
     * token : xxxxx
     * imtoken : xxxx
     * userrole : 0
     */

    private String token;
    private String imtoken;
    private String userrole;


    public String getToken() {
        return token;
    }

    public String getImtoken() {
        return imtoken;
    }

    public String getUserrole() {
        return userrole;
    }


}
