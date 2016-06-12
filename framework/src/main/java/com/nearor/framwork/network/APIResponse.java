package com.nearor.framwork.network;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.nearor.framwork.ValueObject;


/**
 * 接口返回的数据对象，包含业务数据和状态数据
 *
 * Created by nearor .
 */
public class APIResponse extends ValueObject {
    public static final String DEFAULT_API_ERROR_MESSAGE = "服务器正在打盹，请稍后再试";

    public static final String API_RESPONSE_SUCCESS_CODE = "0";

    // 业务数据
    private String data;

    @SerializedName("rtn_code")
    private String code;

    @SerializedName("rtn_ext")
    private String ext;

    @SerializedName("rtn_ftype")
    private String ftype;

    @SerializedName("rtn_msg")
    private String msg;

    @SerializedName("rtn_tip")
    private String tip;

    protected APIResponse() {

    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getExt() {
        return ext;
    }

    public String getFtype() {
        return ftype;
    }

    public String getMsg() {
        return msg;
    }

    public String getTip() {
        return tip;
    }

    public boolean isSuccessed() {
        boolean successed = false;
        if (code != null) {
            successed = code.equalsIgnoreCase(API_RESPONSE_SUCCESS_CODE);
        }

        return successed;
    }

    @Override
    public String toString() {
        return toJsonString();
    }

    public String getLocalizedMessage() {
        String message = getTip();
        if (TextUtils.isEmpty(message)) {
            message = getMsg();
        }

        if (TextUtils.isEmpty(message)) {
            message = DEFAULT_API_ERROR_MESSAGE;
        }
        return message;
    }
}
