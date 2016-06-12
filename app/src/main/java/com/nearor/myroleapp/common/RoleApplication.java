package com.nearor.myroleapp.common;

import android.app.Activity;

import com.nearor.common.CommonApplication;
import com.nearor.common.api.PassportService;
import com.nearor.common.module.ModuleHelper;
import com.nearor.framwork.preference.GlobalValue;


/**
 * 角色端（设计 + 项目经理）
 * Created by nearor .
 */
public class RoleApplication extends CommonApplication {

   // private static final String BUGTAGS_APPKEY = "41fe6eb27d2dc3474372bfa528bc1588";

    private PassportService mPassportService;

    @Override
    public void onCreate() {
        super.onCreate();
      GlobalValue.getInstance().setIsRoleApp(true);
       ModuleHelper.roleAppinit(this);

     mPassportService = new PassportService(null);
    }

    @Override
    public void logout(Activity originActivity) {
//        mPassportService.logout();
//        if (originActivity != null) {
//            Route.getSharedInstance().startModule(AppModule.LOGIN.getName(), null, null, originActivity);
//            originActivity.overridePendingTransition(com.zhai52.common.R.anim.common_logout_fade_in, com.zhai52.common.R.anim.common_logout_fade_out);
//        }
    }

//    @Override
//    public String bugtagsAppKey() {
//        return BUGTAGS_APPKEY;
//    }
}
