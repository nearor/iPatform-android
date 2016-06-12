package com.nearor.common.route;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.nearor.common.module.Module;
import com.nearor.framwork.util.Lg;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Nearor on 6/3/16.
 */
public class Route {
    private static final String TAG = Lg.makeLogTag(Route.class);

    public static final String INTENT_EXTRA_KEY_ACTIVITY = "route.key.activity";
    public static final String INTENT_EXTRA_KEY_FRAGMENT = "route.key.fragment";

    private Application mApplication;

    private RouteCallBack mCallBack;

    private Map<String,String> mActivityModules;
    private Map<String,String> mFragmentModules;

    public Route() {
        mActivityModules = new HashMap<>();
        mFragmentModules = new HashMap<>();
    }

    private static class Holder{
        private static final Route sInstance = new Route();
    }

    public static Route getSharedInstance(){
      return Holder.sInstance;
    }

    public void init(Application application){
        if(application == null){
            throw new IllegalArgumentException("Application must not be null");
        }
        mApplication = application;
    }


    public RouteCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(RouteCallBack callBack) {
        mCallBack = callBack;
    }

    public void registerActivityModule(String host,String classPath){
        registerModule(host,classPath, Module.ModuleType.ACTIVITY);
    }

    private void registerModule(String host, String classPath, Module.ModuleType type) {
        if (TextUtils.isEmpty(host)) {
            Lg.e(TAG,"Empty host!");
            throw new IllegalArgumentException("Empty host");
        }
        host = host.toLowerCase();

        if(type.equals(Module.ModuleType.ACTIVITY)){
            if (mActivityModules.containsKey(host)){
                Lg.e(TAG,"");
            }else {
                mActivityModules.put(host,classPath);
            }

        }else if(type.equals(Module.ModuleType.FRAGMENT)){
            if(mFragmentModules.containsKey(host)){
                Lg.e(TAG,"");
            }else {
                mFragmentModules.put(host,classPath);
            }
        }
    }

    /**
     * 如果 Activity 和 Fragment 都存在，优先 Activity
     */

    public void startModule(String moduleName, String from, Map<String, String> params, Activity activity) {
        if (mActivityModules.containsKey(moduleName.toLowerCase())) {
            startActivity(moduleName.toLowerCase(), from, params, activity);
        }
    }

    public void startActivity(String moduleName, String from, Map<String, String> params, Activity activity) {
        Intent intent = createModuleIntent(moduleName, from, params);
        if (mCallBack != null) {
            mCallBack.beforeStartActivity(moduleName, intent);
        }
        activity.startActivity(intent);
    }

    private Intent createModuleIntent(String url, String from, Map<String, String> params) {
        String host = RouteUtils.getModuleURLHost(url);
        Map<String, String> urlParams = RouteUtils.parseModulURLParams(url);
        if (params == null) {
            params = urlParams;
        } else {
            params.putAll(urlParams);
        }

        Intent moduleIntent;
        if(params != null && params.size() > 0) {
            if(!TextUtils.isEmpty(from)) {
                params.put("from", from);
            }

            JSONObject paramJsonObject = new JSONObject(params);

            try {
                String forwarUrl = String.format("%s?body=%s", host, URLEncoder.encode(paramJsonObject.toString(), "utf-8"));
                moduleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(forwarUrl));
            } catch (UnsupportedEncodingException var12) {
                moduleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?body=%s", host, paramJsonObject.toString())));
                Lg.e(TAG, "URL encode error", var12);
            }
        } else if(!TextUtils.isEmpty(from)) {
            moduleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?body={\"from\":\"%s\"}", host, from)));
        } else {
            moduleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(host));
        }

        configIntentForModule(moduleIntent);

        return moduleIntent;
    }

    public void configIntentForModule(Intent intent) {
        if(intent.getComponent() == null) {
            Uri uri = intent.getData();
            if(uri != null && uri.getScheme() != null && RouteUtils.SCHEME.equalsIgnoreCase(uri.getScheme())) {
                String host = uri.getHost();

                if(!TextUtils.isEmpty(host)) {
                    host = host.toLowerCase(Locale.US);
                    if(!TextUtils.isEmpty(mActivityModules.get(host))) {
                        intent.setClassName(mApplication, mActivityModules.get(host));
                        intent.putExtra(INTENT_EXTRA_KEY_ACTIVITY, true);
                    }

                    if(!TextUtils.isEmpty(mFragmentModules.get(host))) {
                        intent.setClass(mApplication, ModuleFragmentActivity.class);
                        intent.putExtra(INTENT_EXTRA_KEY_FRAGMENT, mFragmentModules.get(host));
                    }
                }
            }
        }
    }

}
