package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class GlobalUser {

    public String title;
    public String first_name;
    public String last_name;
    public String email;
    public String password;
    public Date created_date;
    public String default_community;

    public GlobalUser() {

    }

    public GlobalUser(String title, String first_name, String last_name, String email, String password, Date created_date, String default_community) {
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.created_date = created_date;
        this.default_community = default_community;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("first_name", first_name);
        result.put("last_name", last_name);
        result.put("email", email);
        result.put("password", password);
        result.put("created_date", created_date);
        result.put("default_community", default_community);
        return result;
    }
}
