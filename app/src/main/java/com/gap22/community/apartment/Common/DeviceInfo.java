package com.gap22.community.apartment.Common;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceInfo {

    private String serviceName = Context.TELEPHONY_SERVICE;
    private static TelephonyManager telephonyManager;

    public DeviceInfo(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(serviceName);
    }

    public static String GetIMEINumber() {
        return telephonyManager.getDeviceId();
    }

    public static String GetOSVersion() {
        return System.getProperty("os.version");
    }

    public static String GetSDKVersion() {
        return android.os.Build.VERSION.SDK;
    }

    public static String GetDevice() {
        return android.os.Build.DEVICE;
    }

    public static String GetModel() {
        return android.os.Build.MODEL;
    }

    public static String GetProduct() {
        return android.os.Build.PRODUCT;
    }

}
