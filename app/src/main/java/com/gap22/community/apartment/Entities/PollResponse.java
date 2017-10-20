package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PollResponse {
    public String wlan_IP;
    public String eth_IP;
    public String response_value;
    public Date created_date;
    public String modified_by;
    public Date modified_date;

    public PollResponse() {
    }

    public PollResponse(String wlan_IP, String eth_IP, String response_value, Date created_date, String modified_by, Date modified_date) {
        this.wlan_IP = wlan_IP;
        this.eth_IP = eth_IP;
        this.response_value = response_value;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("wlan_IP", wlan_IP);
        result.put("eth_IP", eth_IP);
        result.put("response_value", response_value);
        result.put("created_date", created_date);
        result.put("modified_by", modified_by);
        result.put("modified_date", modified_date);
        return result;
    }

}
