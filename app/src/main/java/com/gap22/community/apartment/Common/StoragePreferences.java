package com.gap22.community.apartment.Common;

/**
 * Created by NARAYANAN-PC on 6/14/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class StoragePreferences {

    private static StoragePreferences yourPreference;
    private SharedPreferences sharedPreferences;

    public static StoragePreferences getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new StoragePreferences(context);
        }
        return yourPreference;
    }

    private StoragePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("CommunifyPreference", Context.MODE_PRIVATE);
    }

    public void savePreference(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getPreference(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void deletePreference(String key) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(key).commit();
        }
    }
}
