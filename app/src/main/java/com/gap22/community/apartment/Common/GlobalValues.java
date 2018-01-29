package com.gap22.community.apartment.Common;

import android.Manifest;

import com.gap22.community.apartment.Entities.Members;
import com.gap22.community.apartment.Entities.SecurityGroupSettings;
import com.github.underscore.Predicate;
import com.google.firebase.auth.FirebaseAuth;
import com.github.underscore.$;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GlobalValues {
    private static String EncriptionPass = "C0MmUn!F$";
    private static String EncriptionSalt = "S@L7";
    private static String CommunityId = "";
    private static SimpleDateFormat eventsTimeFormat;
    private static String CurrentUserName = "";
    private static String CurrentUserEmail = "";
    private static String CurrentUserUuid = "";
    private static String CurrentUserPassword = "";
    private static SecurityGroupSettings SecurityGroupSettings;
    private static FirebaseAuth authentication;
    private static List<Members> communityMembers = new ArrayList<Members>();
    public static String WantPermission = Manifest.permission.READ_PHONE_STATE;
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static String ACCOUNT_SID = "AC7700be57cfe831a28fb5a9d96126f10b";
    public static String AUTH_TOKEN = "e6c05d19a9dc7078aa8163bf8c9924dc";


    private static void setFirebaseAuthentication(FirebaseAuth auth) {
        authentication = auth;
    }

    private static FirebaseAuth getFirebaseAuthentication() {
        return authentication;
    }

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

    public static void setCurrentUserPassword(String password) {
        CurrentUserPassword = password;
    }

    public static String getCurrentUserPassword() {
        return CurrentUserPassword;
    }

    public static void setCurrentUserName(String currentUserName) {
        CurrentUserName = currentUserName;
    }

    public static String getCurrentUserName() {
        return CurrentUserName;
    }

    public static void setCurrentUserEmail(String currentUserEmail) {
        CurrentUserEmail = currentUserEmail;
    }

    public static String getCurrentUserEmail() {
        return CurrentUserEmail;
    }

    public static void setCurrentUserUuid(String currentUserUuid) {
        CurrentUserUuid = currentUserUuid;
    }

    public static String getCurrentUserUuid() {
        return CurrentUserUuid;
    }

    public static void StoreUserRights(SecurityGroupSettings securityGroupSettings) {
        SecurityGroupSettings = securityGroupSettings;
    }

    public static void setSecurityGroupSettings(SecurityGroupSettings securityGroupSettings) {
        SecurityGroupSettings = securityGroupSettings;
    }

    public static SecurityGroupSettings getSecurityGroupSettings() {
        return SecurityGroupSettings;
    }

    public static Date FormatEventsDateTime(String dateToFormat) throws Exception {
        eventsTimeFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        return eventsTimeFormat.parse(dateToFormat);
    }

    public static SecurityGroupSettings StoreUserRights() {
        return SecurityGroupSettings;
    }

    public static void addCommunityMembers(Members members) {
        communityMembers.add(members);
    }

    public static List<Members> getCommunityMembers(Members members) {
        return communityMembers;
    }

    /*public static Members getMemberInfoByEmailId(String memberId) {
        return $.find(communityMembers, new Predicate<Members>() { public Members Find(Members item) { return item.id == memberId; } }).get();
    }*/
}
