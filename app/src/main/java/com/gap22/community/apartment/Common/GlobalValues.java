package com.gap22.community.apartment.Common;

import com.gap22.community.apartment.Entities.SecurityGroupSettings;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalValues {
    private static String EncriptionPass = "C0MmUn!F$";
    private static String EncriptionSalt = "S@L7";
    private static SecurityGroupSettings SecurityGroupSettings;
    private static String CommunityId = "";
    private static SimpleDateFormat eventsTimeFormat;

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

    public static Date FormatEventsDateTime(String dateToFormat) throws Exception {
        eventsTimeFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        return eventsTimeFormat.parse(dateToFormat);
    }

    public static SecurityGroupSettings StoreUserRights() {
        return SecurityGroupSettings;
    }
}
