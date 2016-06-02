package com.nearor.commonui.ativity;


import android.support.v7.app.AppCompatActivity;

import com.nearor.framwork.util.Lg;

/**
 * Created by Nearor on 6/1/16.
 */
public class AppActivity extends AppCompatActivity{

    private static final String TAG = Lg.makeLogTag(AppActivity.class);

    public void startModule(String module){
        startModule(module);
    }

    public boolean needLogin(){
        return true;
    }

}
