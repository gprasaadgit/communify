package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class GlobalUser {

    public String title;
    public String first_name;
    public String last_name;
    public String email;
    public String otp_authenticated;
    public String otp_authentication_key;
    public String email_authenticated;
    public String email_authentication_key;
    public String password;
    public String phone;
    public Date created_date;
    public String default_community;

    public GlobalUser() {

    }

    public GlobalUser(String title, String first_name, String last_name, String email, String password, String phone, String otp_authenticated,
                      String otp_authentication_key, String email_authenticated, String email_authentication_key, Date created_date, String default_community) {
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.created_date = created_date;
        this.phone = phone;
        this.email_authentication_key = email_authentication_key;
        this.email_authenticated = email_authenticated;
        this.otp_authentication_key = otp_authentication_key;
        this.otp_authenticated = otp_authenticated;
        this.default_community = default_community;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("first_name", first_name);
        result.put("last_name", last_name);
        result.put("email", email);
        result.put("phone", phone);
        result.put("email_authenticated", email_authenticated);
        result.put("email_authentication_key", email_authentication_key);
        result.put("otp_authenticated", otp_authenticated);
        result.put("otp_authentication_key", otp_authentication_key);
        result.put("created_date", created_date);
        result.put("default_community", default_community);
        return result;
    }
}
