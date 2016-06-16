package com.nearor.myroleapp;

import android.os.Bundle;

import com.nearor.common.module.RoleAppModuleMap;
import com.nearor.commonui.ativity.AppActivity;
import com.nearor.framwork.preference.GlobalValue;


public class SplashActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        if(!GlobalValue.getInstance().isLogin()){
            startModule(RoleAppModuleMap.MODULE_NAME_LOGIN);
        }else {
            if(GlobalValue.isPM()){
                startModule(RoleAppModuleMap.MODULE_NAME_HOME_PM);
            }else {
                startModule(RoleAppModuleMap.MODULE_NAME_HOME_PM);
            }
        }

        finish();
    }


}
