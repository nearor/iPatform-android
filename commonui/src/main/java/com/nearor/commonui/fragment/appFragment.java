package com.nearor.commonui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.nearor.common.route.Route;
import com.nearor.common.route.RouteUtils;
import com.nearor.framwork.network.ICallManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 *
 * Created by Nearor on 16/6/11.
 */
public class AppFragment extends Fragment implements ICallManager {

    private List<Call> mNetworkCalls;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkCalls = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        onManagerDestroy();
        super.onDestroy();
    }

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
        for(Call call : mNetworkCalls){
            call.cancel();
        }
        mNetworkCalls.clear();
    }

    public void startModule(String module){
        startModule(module,"");
    }

    public void startModule(String module,String jsonFormatParams){
        Route.getSharedInstance().startModule(module,null, RouteUtils.jsonParamsToMap(jsonFormatParams),getActivity());
    }

    public void startModule(String module, HashMap<String,String> params){
        Route.getSharedInstance().startModule(module,null,params,getActivity());
    }


}
