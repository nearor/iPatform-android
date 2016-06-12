package com.nearor.common.route;

import android.content.Intent;

/**
 *
 * Created by nearor.
 */
public interface RouteCallBack {
    /**
     * Before Activity.startActivity()
     */
    void beforeStartActivity(String moduleName, Intent intent);
}
