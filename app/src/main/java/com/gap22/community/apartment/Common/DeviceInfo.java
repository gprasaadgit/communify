package com.gap22.community.apartment.Common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.gap22.community.apartment.R;

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

    public static String GetPhoneNumber(Context context, Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, GlobalValues.WantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return GetCountryDialUpCode(telephonyManager.getSimCountryIso().toUpperCase(), activity) + telephonyManager.getLine1Number();
    }

    public static void RequestPermission(String permission, Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            Toast.makeText(activity, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission}, GlobalValues.PERMISSION_REQUEST_CODE);
    }

    public static boolean CheckPermission(String permission, Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static String GetCountryDialUpCode(String countryCode, Activity activity) {
        String countryNumber = "";
        String[] rl = activity.getResources().getStringArray(R.array.CountryCodes);

        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(countryCode.trim())) {
                countryNumber = g[0];
                break;
            }
        }
        return countryNumber;
    }
}
