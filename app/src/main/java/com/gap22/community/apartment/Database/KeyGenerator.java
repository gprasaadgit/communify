package com.gap22.community.apartment.Database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyGenerator {

    private static final String CommunityKeyPrefix = "comu-";
    private static final String MemberKeyPrefix = "memb-";
    private static final String PostKeyPrefix = "post";
    private static final String PostCommentKeyPrefix = "post-comt-";
    private static final String PollKeyPrefix = "poll-";
    private static final String PollResponseKeyPrefix = "poll-resp-";
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
}
