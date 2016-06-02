package com.nearor.common;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.nearor.framwork.app.MyApplication;


/**
 *
 */
public abstract class CommonApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)
                && !TextUtils.isEmpty(bugtagsAppKey())) {
            //Bugtags.start(bugtagsAppKey(), this, Bugtags.BTGInvocationEventBubble);
        } else {
           // Bugtags.start(bugtagsAppKey(), this, Bugtags.BTGInvocationEventNone);
        }

        //RetrofitHelper.getSharedInstance().init(URLUtil.getBaseUrl(), new APIErrorhandler());

       // IMSDKHelper.init(getApplicationContext());
    }

//    @Override
//    public void logout(Activity originActivity) {
//        PassportService.logout();
//        if (originActivity != null) {
//            Router.getSharedInstance().startModule(AppModule.LOGIN, originActivity);
//            originActivity.overridePendingTransition(R.anim.common_logout_fade_in, R.anim.common_logout_fade_out);
//        }
//    }

    public abstract String bugtagsAppKey();
}
