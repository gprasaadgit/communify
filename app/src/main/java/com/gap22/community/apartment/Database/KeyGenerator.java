package com.gap22.community.apartment.Database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyGenerator {

    private static final String CommunityKeyPrefix = "CMTY-";
    private static final String MemberKeyPrefix = "MEMB-";
    private static final String PostKeyPrefix = "POST-";
    private static final String PostCommentKeyPrefix = "CMNT-";
    private static final String PollKeyPrefix = "POLL-";
    private static final String PollResponseKeyPrefix = "RESP-";
    private static final String SecurityGroupKeyPrefix = "GRP-";
    private static final String EventsKeyPrefix = "EVNT-";
    private static final String InvitesKeyPrefix = "INVI-";
    SimpleDateFormat sdf;

    public KeyGenerator() {

    }

    public String GetNewCommunityKey() {
        return CommunityKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewMemberKey() {
        return MemberKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewPostKey() {
        return PostKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewPostCommentKey() {
        return PostCommentKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewPollKey() {
        return PollKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewPollResponseKey() {
        return PollResponseKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewSecurityGroupResponseKey(String groupName) {
        return SecurityGroupKeyPrefix + groupName.toUpperCase() + "-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewEventsKey() {
        return EventsKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public String GetNewInviteKey() {
        return InvitesKeyPrefix + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
}
