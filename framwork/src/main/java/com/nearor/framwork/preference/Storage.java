package com.nearor.framwork.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.nearor.framwork.app.MyApplication;
import com.nearor.framwork.util.Lg;


/**
 *
 */
public class Storage {

    private static final String TAG = Lg.makeLogTag(Storage.class);

    private static final String PREFERENCE_NAME = "com.zhai52.preference";

    private SharedPreferences mSharedPreferences;

    private static class Holder {
        static final Storage sStorage = new Storage();
    }

    private Storage() {
        mSharedPreferences = MyApplication.getSharedInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static Storage sharedInstance() {
        return Holder.sStorage;
    }

    public void add(String key, Object value) {
        if (TextUtils.isEmpty(key)) {
            Lg.e(TAG, "Preference key is empty");
        } else {
            if (value == null) {
                mSharedPreferences.edit().remove(key).apply();
            } else if (value instanceof String) {
                mSharedPreferences.edit().putString(key, (String) value).apply();
            } else if (value instanceof Boolean) {
                mSharedPreferences.edit().putBoolean(key, (Boolean) value).apply();
            } else if (value instanceof Float) {
                mSharedPreferences.edit().putFloat(key, (Float) value).apply();
            } else if (value instanceof Integer) {
                mSharedPreferences.edit().putInt(key, (Integer) value).apply();
            } else if (value instanceof Long) {
                mSharedPreferences.edit().putLong(key, (Long) value).apply();
            } else {
                mSharedPreferences.edit().putString(key, value.toString()).apply();
            }
        }
    }

    public Object get(String key) {
        return mSharedPreferences.getAll().get(key);
    }

}
