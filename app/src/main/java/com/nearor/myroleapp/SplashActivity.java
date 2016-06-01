package com.nearor.myroleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nearor.framwork.preference.GlobalValue;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!GlobalValue.getInstance().isLogin()){

        }




    }


}
