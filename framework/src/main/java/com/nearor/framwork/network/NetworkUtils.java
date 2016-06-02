package com.nearor.framwork.network;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class NetworkUtils {
	public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";
	public static final String WIFI = "Wi-Fi";
	public static final String TWO_OR_THREE_G = "2G/3G";
	public static final String UNKNOWN = "Unknown";

	private static String convertIntToIp(int paramInt) {
		return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "."
				+ (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
	}

	/***
	 获取当前网络类型
	 * 
	 * @param pContext
	 * @return type[0] WIFI , TWO_OR_THREE_G , UNKNOWN type[1] SubtypeName
	 */
	public static String[] getNetworkState(Context pContext) {
		String[] type = new String[2];
		type[0] = "Unknown";
		type[1] = "Unknown";

		if (pContext.getPackageManager().checkPermission(
				"android.permission.ACCESS_NETWORK_STATE",
				pContext.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
			ConnectivityManager localConnectivityManager = (ConnectivityManager)pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (localConnectivityManager == null)
				return type;

			NetworkInfo localNetworkInfo1 = localConnectivityManager
					.getNetworkInfo(1);
			if ((localNetworkInfo1 != null)
					&& (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED)) {
				type[0] = "Wi-Fi";
				type[1] = localNetworkInfo1.getSubtypeName();
				return type;
			}
			NetworkInfo localNetworkInfo2 = localConnectivityManager
					.getNetworkInfo(0);
			if ((localNetworkInfo2 == null)
					|| (localNetworkInfo2.getState() != NetworkInfo.State.CONNECTED))
				type[0] = "2G/3G";
			type[1] = localNetworkInfo2.getSubtypeName();
			return type;
		}
		return type;
	}

	/**
	 * 获取网络类型名称：2g or 3g or wifi
	 *
	 * @return
	 */
	public static String getNetTypeName(Context ctx) {
		String netType = "";

		ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isAvailable()) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				// networkInfo.getTypeName();
				netType = "wifi";
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				switch (networkInfo.getSubtype()) {
					case TelephonyManager.NETWORK_TYPE_1xRTT:
						netType = "2g";
						break; // ~ 50-100 kbps
					case TelephonyManager.NETWORK_TYPE_CDMA:
						netType = "2g";
						break; // ~ 14-64 kbps
					case TelephonyManager.NETWORK_TYPE_EDGE:
						netType = "2g";
						break; // ~ 50-100 kbps
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
						netType = "3g";
						break; // ~ 400-1000 kbps
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
						netType = "3g";
						break; // ~ 600-1400 kbps
					case TelephonyManager.NETWORK_TYPE_GPRS:
						netType = "2g";
						break; // ~ 100 kbps
					case TelephonyManager.NETWORK_TYPE_HSDPA:
						netType = "3g";
						break; // ~ 2-14 Mbps
					case TelephonyManager.NETWORK_TYPE_HSPA:
						netType = "3g";
						break; // ~ 700-1700 kbps
					case TelephonyManager.NETWORK_TYPE_HSUPA:
						netType = "3g";
						break; // ~ 1-23 Mbps
					case TelephonyManager.NETWORK_TYPE_UMTS:
						netType = "3g";
						break; // ~ 400-7000 kbps
					case TelephonyManager.NETWORK_TYPE_UNKNOWN:
						netType = "";
						break;
				}
			}
		}
		return netType;
	}

	/***
	 *获取wifi 地址
	 * 
	 * @param pContext
	 * @return
	 */

	public static String getWifiAddress(Context pContext) {
		String address = DEFAULT_WIFI_ADDRESS;
		if (pContext != null) {
			WifiInfo localWifiInfo = ((WifiManager) pContext
					.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
			if (localWifiInfo != null) {
				address = localWifiInfo.getMacAddress();
				if (address == null || address.trim().equals(""))
					address = DEFAULT_WIFI_ADDRESS;
				return address;
			}

		}
		return DEFAULT_WIFI_ADDRESS;
	}

	/***
	 *获取wifi ip地址
	 * 
	 * @param pContext
	 * @return
	 */
	public static String getWifiIpAddress(Context pContext) {
		WifiInfo localWifiInfo = null;
		if (pContext != null) {
			localWifiInfo = ((WifiManager)pContext.getSystemService(Context.WIFI_SERVICE))
					.getConnectionInfo();
			if (localWifiInfo != null) {
				String str = convertIntToIp(localWifiInfo.getIpAddress());
				return str;
			}
		}
		return "";
	}

	/**
	 * 获取WifiManager
	 * 
	 * @param pContext
	 * @return
	 */
	public static WifiManager getWifiManager(Context pContext) {
		return (WifiManager) pContext.getSystemService(Context.WIFI_SERVICE);
	}

	/**
	 * 网络可用
	 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable(Context ctx) {
	
		ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}
	
	
	/***
	 *  wifi状态
	 * @param pContext
	 * @return
	 */
	public static boolean isWifi(Context pContext) {
		if ((pContext != null)
				&& (getNetworkState(pContext)[0].equals("Wi-Fi"))) {
			return true;
		} else {
			return false;
		}
	}
}