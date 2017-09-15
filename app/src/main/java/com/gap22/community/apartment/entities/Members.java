package com.gap22.community.apartment.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Members {

    public long community_id;
    public long user_id;
    public String user_email;
    public Date user_registered_on;
    public String user_activation_key;
    public boolean user_status;
    public String user_first_name;
    public int user_adults;
    public int user_children;
    public int user_infants;
    public String user_title;
    public String user_unit;
    public String user_phone;
    public String user_twitter_url;
    public String user_facebook_url;
    public String user_about;

    public Members() {

    }

    public Members(long community_id, long user_id, String user_email, Date user_registered_on,
                   String user_activation_key, boolean user_status, String user_first_name,
                   int user_adults, int user_children, int user_infants, String user_title,
                   String user_unit, String user_phone, String user_twitter_url, String user_facebook_url,
                   String user_about) {
        this.community_id = community_id;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_registered_on = user_registered_on;
        this.user_activation_key = user_activation_key;
        this.user_status = user_status;
        this.user_first_name = user_first_name;
        this.user_adults = user_adults;
        this.user_children = user_children;
        this.user_infants = user_infants;
        this.user_title = user_title;
        this.user_unit = user_unit;
        this.user_phone = user_phone;
        this.user_twitter_url = user_twitter_url;
        this.user_facebook_url = user_facebook_url;
        this.user_about = user_about;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("community_id", community_id);
        result.put("user_id", user_id);
        result.put("user_email", user_email);
        result.put("user_registered_on", user_registered_on);
        result.put("user_activation_key", user_activation_key);
        result.put("user_status", user_status);
        result.put("user_first_name", user_first_name);
        result.put("user_adults", user_adults);
        result.put("user_children", user_children);
        result.put("user_infants", user_infants);
        result.put("user_title", user_title);
        result.put("user_unit", user_unit);
        result.put("user_phone", user_phone);
        result.put("user_twitter_url", user_twitter_url);
        result.put("user_facebook_url", user_facebook_url);
        result.put("user_about", user_about);
        return result;
    }
}
