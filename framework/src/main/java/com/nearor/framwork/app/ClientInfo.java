package com.nearor.framwork.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nearor.framwork.network.NetworkUtils;

/**
 * Created by Nearor on 6/1/16.
 * App客户端的相关信息，会以JSON 形式放在接口请求时的 HTTP Header里面
 */
public class ClientInfo {


    public static final String TRADER = "androidphone";
    // 设计师端 TRADER_NAME = "android_common_idesigner"
    public static final String TRADER_NAME = "android_common_ihome";

    private transient String clientInfoString = null;

    private String trader;
    private String traderName;

    /**
     * 客户端版本号：x.x.x
     */
    private String clientAppVersion;

    /**
     * 客户端详细信息，例 xiaomi , 4.4
     */
    private String clientSystem;

    /**
     * Android version
     */
    private String clientVersion;

    private String phoneType;

    /**
     * 客户端唯一标示
     */

    private String deviceCode;

    /**
     * 网络标示 wifi,4G 3G 2G
     */
    private String netType;

    private static class Holder{
        static  ClientInfo INSTANCE = new ClientInfo();
    }

    public static ClientInfo getInstance(){
        return Holder.INSTANCE;
    }

    private ClientInfo(){
        this.clientSystem = getClientSystemDetail();
        this.clientVersion = Build.VERSION.RELEASE;
        this.phoneType = getClientSystemDetail();

        this.trader = ClientInfo.TRADER;
        this.traderName = ClientInfo.TRADER_NAME;
    }

    public void initWithContext(Context ctx){
        this.clientAppVersion = getClientAppVersion(ctx);
        this.deviceCode = DeviceId.getUUid(ctx);
        this.netType = NetworkUtils.getNetTypeName(ctx);

        updataJSONString();
    }



    public String getClientAppVersion(){
        return clientAppVersion;
    }
    private String getClientAppVersion(Context ctx){
        String vName = "";
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),0);
            if (!TextUtils.isEmpty(pi.versionName)) {
                vName = pi.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vName;
    }

    private String getClientSystemDetail(){
        //model手机型号，release版本号
        return "android" + "," + Build.MODEL + "," + Build.VERSION.SDK_INT + "," + Build.VERSION.RELEASE;
    }

    private String getDeviceCode(){
        return deviceCode;
    }

    public String getTrader(){
        return  trader;
    }

    public String getTraderName(){
        return  traderName;
    }

    private void updataJSONString(){
        this.clientInfoString = new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return clientInfoString;
    }

    public String getPhoneType() {
        return phoneType;
    }



}
