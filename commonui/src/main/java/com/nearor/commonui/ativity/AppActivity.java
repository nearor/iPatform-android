package com.nearor.commonui.ativity;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.nearor.common.route.Route;
import com.nearor.common.route.RouteUtils;
import com.nearor.framwork.network.ICallManager;
import com.nearor.framwork.util.Lg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Nearor on 6/1/16.
 */
public class AppActivity extends AppCompatActivity implements ICallManager{

    public static final String ACTION_EXIT = "action_exit";
    private static final String TAG = Lg.makeLogTag(AppActivity.class);

    private List<Call> mNetworkCalls = new ArrayList<>();

    @Override
    public void addCall(Call call) {
        mNetworkCalls.add(call);
    }

    @Override
    public void removeCall(Call call) {
        mNetworkCalls.remove(call);
    }

    @Override
    public void onManagerDestroy() {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startModule(String module){
        startModule(module,"");
    }

    public void startModule(String module, String jsonFormatParams) {
        Route.getSharedInstance().startModule(module, null, RouteUtils.jsonParamsToMap(jsonFormatParams), this);
    }

    public void startModule(String module, Map<String, String> params) {
        Route.getSharedInstance().startModule(module, null, params, this);
    }

    public Map<String, String> getModuleParams() {
        Uri uri = getIntent().getData();
        return RouteUtils.parseModulURLParams(uri.toString());
    }

    public boolean needLogin(){
        return true;
    }



}
