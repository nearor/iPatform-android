package com.nearor.commonui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.nearor.framwork.network.ICallManager;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

/**
 * Created by Nearor on 16/6/11.
 */
public class appFragment extends Fragment implements ICallManager {

    private List<Call> mNetworkCalls;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkCalls = new ArrayList<>();
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

    }
}
