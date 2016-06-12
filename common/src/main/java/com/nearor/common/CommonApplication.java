package com.nearor.common;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.nearor.common.api.APIErrorhandler;
import com.nearor.framwork.app.MyApplication;
import com.nearor.framwork.network.RetrofitHelper;
import com.nearor.framwork.util.URLUtil;


/**
 *
 */
public abstract class CommonApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitHelper.getSharedInstance().init(URLUtil.getBaseUrl(), new APIErrorhandler());
    }
//
//        if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)
//                && !TextUtils.isEmpty(bugtagsAppKey())) {
//            //Bugtags.start(bugtagsAppKey(), this, Bugtags.BTGInvocationEventBubble);
//        } else {
//           // Bugtags.start(bugtagsAppKey(), this, Bugtags.BTGInvocationEventNone);
//        }
//

//
//       // IMSDKHelper.init(getApplicationContext());
//    }

//    @Override
//    public void logout(Activity originActivity) {
//        PassportService.logout();
//        if (originActivity != null) {
//            Router.getSharedInstance().startModule(AppModule.LOGIN, originActivity);
//            originActivity.overridePendingTransition(R.anim.common_logout_fade_in, R.anim.common_logout_fade_out);
//        }
//    }

//    public abstract String bugtagsAppKey();
}
