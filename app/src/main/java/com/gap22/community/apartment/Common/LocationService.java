package com.gap22.community.apartment.Common;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LocationService {

    public static String GetCountryName() {
        return getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry();
    }

    public static String GetCountryCode() {
        return "";
    }

    public static JSONObject GetUserCompleteInfo() {

        JSONObject response = new JSONObject();
        /*URL url = new URL("http://ip-api.com/json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        System.out.println("Response Code: " + conn.getResponseCode());
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        JSONObject jObj = new JSONObject(response);

        jObj.getString("as");
        jObj.getString("city");
        jObj.getString("country");
        jObj.getString("countryCode");
        jObj.getString("isp");
        jObj.getString("org");
        jObj.getString("query");
        jObj.getString("region");
        jObj.getString("regionName");
        jObj.getString("timezone");*/

        return response;
    }
}
