package com.nearor.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nearor.common.api.entity.LoginResponse;
import com.nearor.framwork.network.APICall;
import com.nearor.framwork.network.APICallBack;
import com.nearor.framwork.network.APIResponse;
import com.nearor.framwork.network.APIService;
import com.nearor.framwork.network.ICallManager;
import com.nearor.framwork.preference.GlobalValue;
import com.nearor.framwork.util.Lg;

import java.io.IOException;

/**
 *
 * Created by Nearor on 16/6/6.
 */
public class PassportService extends APIService<PassportAPI> {

    private static final String TAG = Lg.makeLogTag(PassportService.class);

    public PassportService(ICallManager manager) {
        super(manager,PassportAPI.class);
    }

    public void passwordLogin(String account, String password, final APICallBack<LoginResponse> callBack) {
        login(account, password, LoginType.PASSWORD, callBack);
    }

    /**
     * 登录注册（短信验证，密码）
     */
    private void login(String phoneNum, String password, LoginType loginType, final APICallBack<LoginResponse> callBack) {
        startCall(getApi().login(phoneNum, password, loginType.value), new APICallBack<LoginResponse>() {
            @Override
            public void onSuccess(@NonNull LoginResponse object) {
                loginSuccess(object);
                callBack.onSuccess(object);
               // EventBusHelper.getSharedInstance().post(new LoginEvent());
            }

            @Override
            public void onError(Throwable throwable, @Nullable APIResponse apiResponse) {
                cleanLoginState();
                callBack.onError(throwable, apiResponse);
            }
        });
    }

    private void loginSuccess(LoginResponse loginVO) {
        if (loginVO != null) {
            GlobalValue.getInstance().setUserToken(loginVO.getToken());
            //GlobalValue.getInstance().setImToken(loginVO.getImtoken());
            GlobalValue.getInstance().setUserRole(loginVO.getUserrole());
        } else {
            Lg.e(TAG, "Login success, but response is null (token is null)");
        }
    }

    /**
     *
     * 自动登录.同步方法
     */

    public boolean autoLogin(){
        boolean success = false;

        Log.d(TAG,"start auto Login....");
        APICall<LoginResponse> call = getApi().autoLogin(GlobalValue.getInstance().getUserToken());
        try {
            LoginResponse loginVO = call.execute();
            if(loginVO != null){
               loginSuccess(loginVO);
                success = true;
            }else {
                cleanLoginState();
            }
        } catch (IOException e){
            Lg.e(TAG,"autoLogin is failed",e);
            cleanLoginState();
        }

        return success;
    }

    private void cleanLoginState() {
        GlobalValue.getInstance().cleanLoginState();
    }


    private enum LoginType {
        SMSCODE(0), PASSWORD(1);

        private int value;

        LoginType(int value) {
            this.value = value;
        }
    }

}
