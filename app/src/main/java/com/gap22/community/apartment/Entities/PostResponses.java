package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PostResponses {

    public String author;
    public String author_Wlan_IP;
    public String author_eth_IP;
    public Date date;
    public String content;
    public String status;
    public String type;
    public String parent;

    public PostResponses() {

    }

    public PostResponses(String author, String author_Wlan_IP, String author_eth_IP, Date date, String content,
                        Status status, String type, String parent) {
        this.author = author;
        this.author_Wlan_IP = author_Wlan_IP;
        this.author_eth_IP = author_eth_IP;
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
        result.put("author_Wlan_IP", author_Wlan_IP);
        result.put("author_Wlan_IP", author_Wlan_IP);
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
