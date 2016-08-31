package com.nearor.myroleapp.api;

import com.nearor.framwork.network.APICallBack;
import com.nearor.framwork.network.APIService;
import com.nearor.framwork.network.ICallManager;
import com.nearor.myroleapp.api.entity.MeCenterData;

/**
 *
 * Created by Nearor on 16/6/14.
 */
public class MeService extends APIService<MeInterface>{
    public MeService(ICallManager manager) {
        super(manager, MeInterface.class);
    }

    public void getData(APICallBack<MeCenterData> callBack){
        startCall(getApi().getData(),callBack);
    }


}
