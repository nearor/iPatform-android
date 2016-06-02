package com.nearor.framwork.app;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.nearor.framwork.secure.DigestUtil;

import java.util.UUID;

/**
 * 设备唯一标识
 * Created by xishengquan on 8/26/15.
 */
public class DeviceId {

    private static final String KEY_DEVICE_ID = "com.thestore.deviceid";
    private static final String AES_KEY = "980897908diciud";
    private static final String EXT_FILE = ".cuid";

    /**
     * 如果不能读写系统Settings 就直接使用androidId作为deviceId 如果能读写系统设置，通过imei android id
     * UUID随机值 生成一个id， 写入系统设置，将这个id作为Deviceid
     *
     * @param paramContext
     * @return
     */
    private static String getDeviceID(Context paramContext) {
        checkPermission(paramContext, "android.permission.WRITE_SETTINGS");
        checkPermission(paramContext, "android.permission.READ_PHONE_STATE");
        checkPermission(paramContext, "android.permission.WRITE_EXTERNAL_STORAGE");

        IMEIInfo localIMEIInfo = IMEIInfo.getIMEIInfo(paramContext);

        String imei = localIMEIInfo.IMEI;
        String androidId = getAndroidId(paramContext);

        // 如果不能读写settings, 不管是否取到IMEI, 直接使用androidId和imei组合
        if (!localIMEIInfo.CAN_READ_AND_WRITE_SYSTEM_SETTINGS) {
            return DigestUtil.md5Hex(new StringBuilder().append(KEY_DEVICE_ID).append(imei).append(androidId)
                    .toString());
        }

        //
        // 如果可以读写setting
        String deviceID = Settings.System.getString(paramContext.getContentResolver(), KEY_DEVICE_ID);

        String keyDeviceIdBak = null;

        if (TextUtils.isEmpty(deviceID)) {
            // 使用imei和androidid拼接key
            keyDeviceIdBak = DigestUtil.md5Hex(new StringBuilder().append(KEY_DEVICE_ID).append(imei).append(androidId)
                    .toString());

            deviceID = Settings.System.getString(paramContext.getContentResolver(), keyDeviceIdBak);

            if (!TextUtils.isEmpty(deviceID)) {
                Settings.System.putString(paramContext.getContentResolver(), KEY_DEVICE_ID, deviceID);
            }
        }
        if (TextUtils.isEmpty(deviceID)) {
            if (!TextUtils.isEmpty(deviceID)) {
                Settings.System.putString(paramContext.getContentResolver(), keyDeviceIdBak, deviceID);
                Settings.System.putString(paramContext.getContentResolver(), KEY_DEVICE_ID, deviceID);
            }
        }
        if (TextUtils.isEmpty(deviceID)) {
            String str5 = UUID.randomUUID().toString();
            deviceID = DigestUtil.md5Hex(new StringBuilder().append(imei).append(androidId).append(str5).toString());
            Settings.System.putString(paramContext.getContentResolver(), keyDeviceIdBak, deviceID);
            Settings.System.putString(paramContext.getContentResolver(), KEY_DEVICE_ID, deviceID);
        }
        return deviceID;
    }

    private static String getAndroidId(Context paramContext) {
        String str = Settings.Secure.getString(paramContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return str;
    }

    private static void checkPermission(Context paramContext, String paramString) {
        int i = paramContext.checkCallingOrSelfPermission(paramString);
        int j = (i == 0) ? 1 : 0;
        if (j != 0)
            return;
        throw new SecurityException(new StringBuilder().append("Permission Denial: requires permission ")
                .append(paramString).toString());
    }

    static final class IMEIInfo {
        private static final String KEY_IMEI = "yihaodian_setting_id";
        public final String IMEI;
        public final boolean CAN_READ_AND_WRITE_SYSTEM_SETTINGS;
        public static final String DEFAULT_TM_DEVICEID = "";

        private IMEIInfo(String paramString, boolean paramBoolean) {
            this.IMEI = paramString;
            this.CAN_READ_AND_WRITE_SYSTEM_SETTINGS = paramBoolean;
        }

        private static String getIMEI(Context paramContext, String paramString) {
            String str = null;
            try {
                TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (localTelephonyManager != null) {
                    str = localTelephonyManager.getDeviceId();
                }
            } catch (Exception localException) {
            }
            str = imeiCheck(str);
            if (TextUtils.isEmpty(str)) {
                str = paramString;
            }
            return str;
        }

        static IMEIInfo getIMEIInfo(Context paramContext) {
            int i = 0;
            String str = "";
            try {
                str = Settings.System.getString(paramContext.getContentResolver(), KEY_IMEI);
                if (TextUtils.isEmpty(str)) {
                    str = getIMEI(paramContext, "");
                }
                Settings.System.putString(paramContext.getContentResolver(), KEY_IMEI, str);
            } catch (Exception localException) {// 这里的try catch主要是防止读取setting出错
                i = 1;
                if (TextUtils.isEmpty(str)) {
                    str = getIMEI(paramContext, "");
                }
            }
            return new IMEIInfo(str, i == 0);
        }

        private static String imeiCheck(String paramString) {
            if ((null != paramString) && (paramString.contains(":"))) {
                return "";
            }
            return paramString;
        }
    }

    /**
     * 得到UUid
     *
     * @return
     */
    public static String getUUid(Context ctx) {
        final TelephonyManager tm = (TelephonyManager) ctx.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "" + tm.getDeviceId();
        String tmSerial = "" + tm.getSimSerialNumber();
        String androidId = "" + Settings.Secure.getString(ctx.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uuid = deviceUuid.toString();
        return uuid;
    }
}
