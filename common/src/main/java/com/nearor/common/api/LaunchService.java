package com.nearor.common.api;


import com.nearor.framwork.network.APICall;
import com.nearor.framwork.network.APIService;
import com.nearor.framwork.network.ICallManager;
import com.nearor.framwork.network.SignatureKey;
import com.nearor.framwork.util.Lg;

/**
 */
public class LaunchService extends APIService<LaunchAPI> {

    private static final String TAG = Lg.makeLogTag(LaunchService.class);

    public LaunchService(ICallManager manager) {
        super(manager, LaunchAPI.class);
    }

    public SignatureKey getKey() {
        SignatureKey key = null;
        try {
            APICall<SignatureKey> call = getApi().getkey();
            key = call.execute();
            SignatureKey.currentKey = new SignatureKey(key.getCkey(), key.getStime());
        } catch (Exception e) {
            Lg.e(TAG, e);
        }

        return key;
    }

}
