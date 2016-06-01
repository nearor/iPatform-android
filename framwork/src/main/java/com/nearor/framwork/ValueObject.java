package com.nearor.framwork;

import com.google.gson.Gson;
import com.nearor.framwork.util.Lg;

/**
 * Created by Nearor on 6/1/16.
 */
public class ValueObject {

    private static final String TAG = Lg.makeLogTag(ValueObject.class);

    public String toJsonString(){
        String jsonString = "";
        try {
            jsonString = new Gson().toJson(this);
        } catch (Exception e) {
            Lg.e(TAG,e.getMessage());
        }

        return jsonString;
    }


}
