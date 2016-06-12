package com.nearor.common.api.entity;


import com.nearor.framwork.ValueObject;

/**
 * @author nearor.
 */
public class LaunchData extends ValueObject {
    private String ckey;
    private long stime;

    public String getCkey() {
        return ckey;
    }

    public long getStime() {
        return stime;
    }
}
