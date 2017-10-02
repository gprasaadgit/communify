package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PostResponse {

    public String author;
    public String author_IP;
    public Date date;
    public String content;
    public String status;
    public String type;
    public String parent;

    public PostResponse() {

    }

    public PostResponse(String author, String author_IP, Date date, String content,
                        Status status, String type, String parent) {
        this.author = author;
        this.author_IP = author_IP;
        this.date = date;
        this.content = content;
        this.type = type;
        this.status = status.value;
        this.parent = parent;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("author", author);
        result.put("author_IP", author_IP);
        result.put("date", date);
        result.put("content", content);
        result.put("type", type);
        result.put("parent", parent);
        result.put("status", status);
        return result;
    }

    public enum Status {
        Approved("A"), Rejected("I"), Abused("X"), Reported("R");
        public String value;

        Status(String value) {
            this.value = value;
        }
    }
}
