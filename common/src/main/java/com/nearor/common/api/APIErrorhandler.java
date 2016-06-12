package com.nearor.common.api;


import com.nearor.framwork.network.APIResponse;
import com.nearor.framwork.network.APIResponseErrorHandler;
import com.nearor.framwork.network.RetrofitHelper;
import com.nearor.framwork.util.Lg;

/**
 * 处理接口返回的通用错误（token 过期，秘钥失效等）
 */
public class APIErrorhandler implements APIResponseErrorHandler {

    private static final String TAG = Lg.makeLogTag(APIErrorhandler.class);

    private PassportService mPassportService = new PassportService(null);
    private LaunchService mLaunchService = new LaunchService(null);

    /**
     * token 失效，需重新登录
     */
    private static final String TOKEN_EXPIRE = "000000000002";
    /**
     * 签名验证失败，需调用获取秘钥接口
     */
    private static final String SIGNATURE_ERROR = "000000000003";
    /**
     * 时间戳验证失败（签名验证成功），需调用获取秘钥接口
     */
    private static final String TIMESTAMP_ERROR = "000000000004";

    private long mLastUpdateKeyTime = System.currentTimeMillis() - (1000 * 60);
    private boolean mLoadingKey = false;
    private final Object mLoadingKeyLock = new Object();

    private long mLastAutoLoginTime = System.currentTimeMillis() - (1000 * 60);
    private boolean mInAutoLogin = false;
    private final Object mAutologinlock = new Object();

    @Override
    public boolean handleAPIError(APIResponse response) {
        boolean success = false;

        if (response.getFtype().equalsIgnoreCase("0")) {
            switch (response.getCode()) {
                case TOKEN_EXPIRE:
                    Lg.e(TAG, "Handle token expire error: " + response.getCode());
                    handleTokenExpire();
                    success = true;
                    break;
                case SIGNATURE_ERROR:
                case TIMESTAMP_ERROR:
                    Lg.e(TAG, "Handle signature error: " + response.getCode());
                    handleSignatureError();
                    success = true;
                default:
                    break;
            }
        } else if (response.getFtype().equalsIgnoreCase("1")) {
            // TODO:
        } else if (response.getFtype().equalsIgnoreCase("2")) {
            // TODO:
        } else if (response.getFtype().equalsIgnoreCase("3")) {
            // TODO:
        }

        return success;
    }

    private void handleTokenExpire() {
        synchronized (mAutologinlock) {
            // 如果正在自动登录则等待
            while (mInAutoLogin) {
                try {
                    Lg.d(TAG, "Waiting for auto login...");
                    mAutologinlock.wait(RetrofitHelper.TIME_OUT_SECONDS * 3 * 1000, 0);
                } catch (InterruptedException e) {
                    Lg.e(TAG, "Lock failed", e);
                }
            }

            // 近期（1分钟内）没有自动登录再登录
            if ((System.currentTimeMillis() - mLastAutoLoginTime) > 60 * 1000) {
                if (!mPassportService.autoLogin()) {
                    Lg.e(TAG, "Auto login failed");
                } else {
                    mLastAutoLoginTime = System.currentTimeMillis();
                }

                mInAutoLogin = false;
                mAutologinlock.notifyAll();
            }
        }
    }

    private void handleSignatureError() {
        synchronized (mLoadingKeyLock) {
            // 如果正在更新秘钥则等待
            while (mLoadingKey) {
                try {
                    mLoadingKeyLock.wait(RetrofitHelper.TIME_OUT_SECONDS * 3 * 1000, 0);
                } catch (InterruptedException e) {
                    Lg.e(TAG, "Lock failed", e);
                }
            }

            // 近期（1分钟内）没有更新过秘钥的话再重新获取
            if ((System.currentTimeMillis() - mLastUpdateKeyTime) > 60 * 1000) {
                mLaunchService.getKey();
                mLastUpdateKeyTime = System.currentTimeMillis();
                mLoadingKey = false;
                mLoadingKeyLock.notifyAll();
            }
        }
    }
}
