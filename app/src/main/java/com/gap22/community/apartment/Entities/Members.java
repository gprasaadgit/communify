package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Members {
    public String email;
    public Date registered_on;
    public Date activated_on;
    public String status;
    public String first_name;
    public String last_name;
    public int adults;
    public int children;
    public int infants;
    public String title;
    public String unit;
    public String phone;
    public String twitter_url;
    public String facebook_url;
    public String about;

    public Members() {

    }

    public Members(String email, Date registered_on,
                   Date activated_on, Status status, String first_name,
                   String last_name, int adults, int children, int infants, String title,
                   String unit, String phone, String twitter_url, String facebook_url,
                   String about) {
        this.email = email;
        this.registered_on = registered_on;
        this.activated_on = activated_on;
        this.status = status.value;
        this.first_name = first_name;
        this.last_name = last_name;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.title = title;
        this.unit = unit;
        this.phone = phone;
        this.twitter_url = twitter_url;
        this.facebook_url = facebook_url;
        this.about = about;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("registered_on", registered_on);
        result.put("activated_on", activated_on);
        result.put("status", status);
        result.put("first_name", first_name);
        result.put("last_name", last_name);
        result.put("adults", adults);
        result.put("children", children);
        result.put("infants", infants);
        result.put("title", title);
        result.put("unit", unit);
        result.put("phone", phone);
        result.put("twitter_url", twitter_url);
        result.put("facebook_url", facebook_url);
        result.put("about", about);
        return result;
    }

    public enum Status {
        Active("A"), InActive("I"), Archive("X");
        public String value;

        Status(String value) {
            this.value = value;
        }
    }
}
