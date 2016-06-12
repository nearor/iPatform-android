package com.nearor.framwork.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Nearor on 16/6/6.
 */
public interface APICallBack<T> {
    void onSuccess(@NonNull T object);
    void onError(Throwable throwable,@Nullable APIResponse apiResponse);
}
