package com.nearor.common.api.entity;


/**
 * @author nearor.
 */
public class UnionLoginResponse extends LoginResponse {

    private String code;
    private int state;

    public String getCode() {
        return code;
    }

    public boolean isBinding() {
        return state == 1;
    }
}
