package com.nearor.framwork.network;

import java.io.IOException;

/**
 * 接口调用异常信息，包含业务层错误
 */
public class APIResponseException extends IOException {

    private APIResponse apiResponse;

    public APIResponseException(Throwable cause) {
        this(cause, null);
    }

    public APIResponseException(Throwable cause, APIResponse apiResponse) {
        super(cause);
        if ((cause instanceof APIResponseException) && (apiResponse == null)) {
            this.apiResponse = ((APIResponseException) cause).getApiResponse();
        } else {
            this.apiResponse = apiResponse;
        }
    }

    public APIResponseException(String message) {
        this(message, null);
    }

    public APIResponseException(String message, APIResponse apiResponse) {
        super(message);
        this.apiResponse = apiResponse;
    }

    /**
     * 获取业务层错误信息
     */
    public APIResponse getApiResponse() {
        return apiResponse;
    }

    @Override
    public String getLocalizedMessage() {
        if (apiResponse != null) {
            return apiResponse.getLocalizedMessage();
        } else {
            return APIResponse.DEFAULT_API_ERROR_MESSAGE;
        }
    }

}
