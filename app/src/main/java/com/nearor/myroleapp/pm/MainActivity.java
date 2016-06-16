package com.nearor.myroleapp.pm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nearor.common.module.RoleAppModuleMap;
import com.nearor.common.route.RouteUtils;
import com.nearor.commonui.ativity.AppActivity;
import com.nearor.framwork.util.ToastUtil;
import com.nearor.myroleapp.R;

import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppActivity implements View.OnClickListener {

    private HomeFragment mHomeFragment;
    private ImFragment mImFragment;
    private MeFragment mMeFragment;

    /***
     * 保存当前正在显示的Fragment ，下次显示新的fragment之前移除
     */
    private Object currentShowFragment;


    private TextView mTitleText;
    private Button mBtnHomeTab;
    private Button mBtnImTab;
    private Button mBtnMeTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pm_activity_main);

        mHomeFragment = new HomeFragment();
        mImFragment = new ImFragment();
        mMeFragment = new MeFragment();

        mTitleText = (TextView) findViewById(R.id.pm_main_activity_title_text_view);
        mBtnHomeTab = (Button) findViewById(R.id.pm_main_activity_home_button);
        mBtnImTab = (Button) findViewById(R.id.pm_main_activity_im_button);
        mBtnMeTab = (Button) findViewById(R.id.pm_main_activity_me_button);

        mBtnHomeTab.setOnClickListener(this);
        mBtnImTab.setOnClickListener(this);
        mBtnMeTab.setOnClickListener(this);

        showModule(RoleAppModuleMap.MODULE_NAME_HOME_PM);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if(v == mBtnHomeTab){
            showModule(RoleAppModuleMap.MODULE_NAME_HOME_PM);
        }
        else if(v == mBtnImTab){
            showModule(RoleAppModuleMap.MODULE_NAME_IM_PM);
        }
        else if(v == mBtnMeTab){
            showModule(RoleAppModuleMap.MODULE_NAME_ME_PM);
        }
    }

    private void showModule(String moduleName){
        mBtnHomeTab.setSelected(false);
        mBtnImTab.setSelected(false);
        mBtnMeTab.setSelected(false);

        if(!TextUtils.isEmpty(moduleName)){
            Object fragment = null;

            if(moduleName.equalsIgnoreCase(RoleAppModuleMap.MODULE_NAME_HOME_PM)){
                mBtnHomeTab.setSelected(true);
                fragment = mHomeFragment;
                mTitleText.setText("工作台");
            }
            else if(moduleName.equalsIgnoreCase(RoleAppModuleMap.MODULE_NAME_IM_PM)){
                mBtnImTab.setSelected(true);
                fragment = mImFragment;
                mTitleText.setText("IM");
            }
            else if(moduleName.equalsIgnoreCase(RoleAppModuleMap.MODULE_NAME_ME_PM)){
                mBtnMeTab.setSelected(true);
                fragment = mMeFragment;
                mTitleText.setText("个人中心");
            }
            else {
                fragment = mHomeFragment;
            }

            showFragment(fragment,moduleName);
        }
    }

    private void showFragment(Object fragment,String moduleName) {

        if(fragment == currentShowFragment){
            return;
        }

        if(currentShowFragment instanceof Fragment){
            getFragmentManager().beginTransaction().remove((Fragment) currentShowFragment).commit();
        }
        else if(currentShowFragment instanceof android.support.v4.app.Fragment){
            getSupportFragmentManager().beginTransaction().remove((android.support.v4.app.Fragment) currentShowFragment).commit();
        }

        currentShowFragment = fragment;
        if(fragment instanceof Fragment){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.pm_main_activity_content_container, (Fragment) fragment,moduleName);
            fragmentTransaction.commit();
        }
        else if(fragment instanceof android.support.v4.app.Fragment){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.pm_main_activity_content_container, (android.support.v4.app.Fragment)fragment,moduleName);
            transaction.commit();
        }

    }

    //双击退出


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exitBy2click();
        }
        return false;
    }

    //双击函数
    private static boolean isExit = false;
    private void exitBy2click() {
        Timer tExit ;
        if(!isExit){
            isExit = true;//准备退出
            ToastUtil.showMessage("再点击一次退出");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            },2000);
        }else {
            finish();
            System.exit(0);
        }
    }
}
