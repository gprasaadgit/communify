package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Events {

    public String title;
    public String content;
    public String location;
    public String organised_by;
    public String type;
    public Date from;
    public Date to;
    public String created_by;
    public Date created_on;
    public String modified_by;
    public Date modified_on;

    public Events() {
    }

    public Events(String title, String content, String location, String organised_by, String type, Date from,
                  Date to, String created_by, Date created_on, String modified_by, Date modified_on) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.organised_by = organised_by;
        this.type = type;
        this.from = from;
        this.to = to;
        this.created_by = created_by;
        this.created_on = created_on;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("content", content);
        result.put("location", location);
        result.put("organised_by", organised_by);
        result.put("type", type);
        result.put("from", from);
        result.put("to", to);
        result.put("created_by", created_by);
        result.put("created_on", created_on);
        result.put("modified_by", modified_by);
        result.put("modified_on", modified_on);
        return result;
    }
}
