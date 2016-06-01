package com.nearor.framwork.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import android.util.Log;

import com.nearor.framwork.bitmap.SimpleImageLoader;
import com.nearor.framwork.util.Lg;


/**
 * 初始化 ClientInfo，初始化页面路由
 * @see ClientInfo
 */
public abstract class MyApplication extends Application {

    private static final String TAG = Lg.makeLogTag(MyApplication.class);

    private static MyApplication sharedInstance;

    @Override
    public void onCreate() {
        super.onCreate();

     //   SimpleImageLoader.init(this);
        ClientInfo.getInstance().initWithContext(this);

        sharedInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       // MultiDex.install(this);
    }

    public static MyApplication getSharedInstance() {
        if(sharedInstance == null) {
            throw new IllegalStateException("Application has not been created");
        } else {
            return sharedInstance;
        }
    }

    public int logLevel() {
        return Log.VERBOSE;
    }

    public abstract void logout(Activity originActivity);

}
