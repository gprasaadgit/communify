package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CommunityInviteCode {

    public String invite_code;

    public CommunityInviteCode() {
    }

    public CommunityInviteCode(String invite_code) {
        this.invite_code = invite_code;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("invite_code", invite_code);
        return result;
    }
}
