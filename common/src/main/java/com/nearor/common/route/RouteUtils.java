package com.nearor.common.route;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


import com.nearor.framwork.util.Lg;

import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Created by nearor.
 */
public class RouteUtils {

    public static final String SCHEME = "hj";

    private static final String TAG = Lg.makeLogTag(RouteUtils.class);

    public static Map<String, String> parseModulURLParams(String url) {
        Map<String, String> params = new HashMap<>();
        if (url.indexOf("body") > 0) {
            try {
                String urlParam = url.substring(url.indexOf("=") + 1);
                params = jsonParamsToMap(urlParam);
            } catch (Exception e) {
                Lg.e(TAG, "Parse module params error", e);
            }
        }
        return params;
    }

    public static Map<String, String> jsonParamsToMap(String jsonString) {
        HashMap<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                String decodeString = URLDecoder.decode(jsonString, "utf-8");
                if(!TextUtils.isEmpty(decodeString)) {
                    JSONObject jsonObject = new JSONObject(decodeString);
                    Iterator objkey = jsonObject.keys();

                    while(objkey.hasNext()) {
                        String jkey = objkey.next().toString();
                        params.put(jkey, jsonObject.getString(jkey));
                    }
                }
            } catch (Exception e) {
                Lg.e(TAG, "Decode module params error", e);
            }
        }
        return params;
    }

    public static String getModuleURLHost(String urlOrName) {
        urlOrName = urlOrName.toLowerCase();
        Uri uri = Uri.parse(urlOrName);
        String host = uri.getHost();
        host = host==null ? urlOrName : host;
        return SCHEME + "://" + host;
    }

    public static void showHomeScreen(Activity activity) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(startMain);
    }
}
