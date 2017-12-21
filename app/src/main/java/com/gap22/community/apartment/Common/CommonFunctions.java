package com.gap22.community.apartment.Common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.security.SecureRandom;

public class CommonFunctions {

    static String characterNnumbers = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static String otpNumbers = "0123456789";
    static SecureRandom rnd = new SecureRandom();

    private static Gson gson = new Gson();

    public static void SaveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }

    public static <GenericClass> GenericClass GetSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }

    public static String GenerateRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(characterNnumbers.charAt(rnd.nextInt(characterNnumbers.length())));
        return sb.toString();
    }

    public static String GenerateOTPNumber(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(otpNumbers.charAt(rnd.nextInt(otpNumbers.length())));
        return sb.toString();
    }
}
