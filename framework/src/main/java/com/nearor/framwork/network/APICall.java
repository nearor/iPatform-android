package com.nearor.framwork.network;

import java.io.IOException;

/**
 * 自定义Call
 * Created by Nearor on 16/6/6.
 */
public interface APICall<T> {
    void cancel();
    void enqueue(ICallManager iCallManager,APICallBack<T> callback);
    T execute() throws IOException;
    APICall<T> clone();

}
