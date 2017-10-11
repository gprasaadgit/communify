package com.gap22.community.apartment.Common;

import com.gap22.community.apartment.Entities.SecurityGroupSettings;

public class GlobalValues {
    private static String EncriptionPass = "C0MmUn!F$";
    private static String EncriptionSalt = "S@L7";
    private static SecurityGroupSettings SecurityGroupSettings;
    private static String CommunityId = "";

    public static String getEncriptionPass() {
        return EncriptionPass;
    }

    public static String getEncriptionSalt() {
        return EncriptionSalt;
    }

    public static void setCommunityId(String communityId) {
        CommunityId = communityId;
    }

    public static String getCommunityId() {
        return CommunityId;
    }

    public static void StoreUserRights(SecurityGroupSettings securityGroupSettings) {
        SecurityGroupSettings = securityGroupSettings;
    }

    public static SecurityGroupSettings StoreUserRights() {
        return SecurityGroupSettings;
    }
}
