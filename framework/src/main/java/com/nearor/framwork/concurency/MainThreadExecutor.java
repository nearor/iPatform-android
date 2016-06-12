package com.nearor.framwork.concurency;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author nearor.
 */
public class MainThreadExecutor implements Executor {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}
