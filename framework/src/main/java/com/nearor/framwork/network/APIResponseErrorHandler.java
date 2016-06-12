package com.nearor.framwork.network;


/**
 * 处理接口返回错误码
 */
public interface APIResponseErrorHandler {
    /**
     * 处理接口返回的业务错误码（注：实现该方法时不要包含异步处理逻辑）
     * @param response API Response
     * @return 是否处理，如果返回 true，则框架会重新发起一次请求；false 继续该次请求流程（调用者将收到接口调用失败回调）
     */
    boolean handleAPIError(APIResponse response);
}
