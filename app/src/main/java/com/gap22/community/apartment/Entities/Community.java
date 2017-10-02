package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Community {

    public String title;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String state;
    public int pincode;
    public String status;
    public String taxid;
    public String builder;
    public String about;
    public String invitecode;
    public String owner;
    public Date created_on;
    public String modified_by;
    public Date modified_on;

    public Community() {

    }

    public Community(String title, String address1, String address2,
                     String address3, String city, String state, int pincode,
                     Status status, String taxid, String builder, String about,
                     String invitecode, String owner, Date created_on,
                     String modified_by, Date modified_on) {
        this.title = title;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.status = status.value;
        this.taxid = taxid;
        this.builder = builder;
        this.about = about;
        this.invitecode = invitecode;
        this.owner = owner;
        this.created_on = created_on;
        this.modified_by = modified_by;
        this.modified_on = modified_on;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if (title != null) {
            result.put("title", title);
        }
        if (address1 != null) {
            result.put("address1", address1);
        }
        if (address2 != null) {
            result.put("address2", address2);
        }
        if (address3 != null) {
            result.put("address3", address3);
        }
        if (city != null) {
            result.put("city", city);
        }
        if (state != null) {
            result.put("state", state);
        }
        if (pincode != 0) {
            result.put("pincode", pincode);
        }
        if (status != null) {
            result.put("status", status);
        }
        if (taxid != null) {
            result.put("taxid", taxid);
        }
        if (builder != null) {
            result.put("builder", builder);
        }
        if (about != null) {
            result.put("about", about);
        }
        if (invitecode != null) {
            result.put("invitecode", invitecode);
        }
        if (owner != null) {
            result.put("owner", owner);
        }
        if (created_on != null) {
            result.put("created_on", created_on);
        }
        if (modified_by != null) {
            result.put("modified_by", modified_by);
        }
        if (modified_on != null) {
            result.put("modified_on", modified_on);
        }

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
