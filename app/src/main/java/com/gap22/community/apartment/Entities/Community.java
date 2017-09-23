package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Community {

    private String id;
    private String title;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private int pincode;
    private String status;
    private String taxid;
    private String builder;
    private String about;
    private String invitecode;
    private String owner;
    private Date created_on;
    private String modified_by;
    private Date modified_on;

    public Community() {

    }

    public Community(String id, String title, String address1, String address2,
                     String address3, String city, String state, int pincode,
                     Status status, String taxid, String builder, String about,
                     String invitecode, String owner, Date created_on,
                     String modified_by, Date modified_on) {
        this.id = id;
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
        result.put("id", id);
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
        private String value;

        Status(String value) {
            this.value = value;
        }
    }
}
