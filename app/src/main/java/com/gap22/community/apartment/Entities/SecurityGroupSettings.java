package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SecurityGroupSettings {

    public String name;
    public boolean CanEditCommunity;
    public boolean CanArchiveCommunity;
    public boolean CanAcceptInvites;
    public boolean IsCommunityAdmin;
    public boolean CanEditPost;
    public boolean CanCreatePost;
    public boolean CanDeletePost;
    public boolean CanAbusePost;
    public boolean CanReplyPost;
    public boolean IsPostAdmin;
    public boolean CanEditPoll;
    public boolean CanCreatePoll;
    public boolean CanDeletePoll;
    public boolean CanAbusePoll;
    public boolean CanViewPollDashboard;
    public boolean IsPollAdmin;
    public boolean CanEditEvents;
    public boolean CanCreateEvents;
    public boolean CanDeleteEvents;
    public boolean CanAbuseEvents;
    public boolean IsEventsAdmin;
    public boolean CanEditNotification;
    public boolean CanCreateNotification;
    public boolean CanDeleteNotification;
    public boolean CanAbuseNotification;
    public boolean IsNotificationAdmin;
    public boolean CanEditQuestions;
    public boolean CanCreateQuestions;
    public boolean CanDeleteQuestions;
    public boolean CanAbuseQuestions;
    public boolean IsQuestionsAdmin;

    public SecurityGroupSettings() {

    }

    public SecurityGroupSettings(String id, String name) {
        this.name = name;
    }

    public SecurityGroupSettings(String name, boolean CanEditCommunity, boolean CanArchiveCommunity,
                                 boolean CanAcceptInvites, boolean IsCommunityAdmin, boolean CanEditPost, boolean CanCreatePost,
                                 boolean CanDeletePost, boolean CanAbusePost, boolean CanReplyPost, boolean IsPostAdmin,
                                 boolean CanEditPoll, boolean CanCreatePoll, boolean CanDeletePoll, boolean CanAbusePoll,
                                 boolean CanViewPollDashboard, boolean IsPollAdmin, boolean CanEditEvents, boolean CanCreateEvents,
                                 boolean CanDeleteEvents, boolean CanAbuseEvents, boolean IsEventsAdmin, boolean CanEditNotification,
                                 boolean CanCreateNotification, boolean CanDeleteNotification, boolean CanAbuseNotification,
                                 boolean IsNotificationAdmin, boolean CanEditQuestions, boolean CanCreateQuestions,
                                 boolean CanDeleteQuestions, boolean CanAbuseQuestions, boolean IsQuestionsAdmin) {
        this.name = name;
        this.CanEditCommunity = CanEditCommunity;
        this.CanArchiveCommunity = CanArchiveCommunity;
        this.CanAcceptInvites = CanAcceptInvites;
        this.IsCommunityAdmin = IsCommunityAdmin;
        this.CanEditPost = CanEditPost;
        this.CanCreatePost = CanCreatePost;
        this.CanDeletePost = CanDeletePost;
        this.CanAbusePost = CanAbusePost;
        this.CanReplyPost = CanReplyPost;
        this.IsPostAdmin = IsPostAdmin;
        this.CanEditPoll = CanEditPoll;
        this.CanCreatePoll = CanCreatePoll;
        this.CanDeletePoll = CanDeletePoll;
        this.CanAbusePoll = CanAbusePoll;
        this.CanViewPollDashboard = CanViewPollDashboard;
        this.IsPollAdmin = IsPollAdmin;
        this.CanEditEvents = CanEditEvents;
        this.CanCreateEvents = CanCreateEvents;
        this.CanDeleteEvents = CanDeleteEvents;
        this.CanAbuseEvents = CanAbuseEvents;
        this.IsEventsAdmin = IsEventsAdmin;
        this.CanEditNotification = CanEditNotification;
        this.CanCreateNotification = CanCreateNotification;
        this.CanDeleteNotification = CanDeleteNotification;
        this.CanAbuseNotification = CanAbuseNotification;
        this.IsNotificationAdmin = IsNotificationAdmin;
        this.CanEditQuestions = CanEditQuestions;
        this.CanCreateQuestions = CanCreateQuestions;
        this.CanDeleteQuestions = CanDeleteQuestions;
        this.CanAbuseQuestions = CanAbuseQuestions;
        this.IsQuestionsAdmin = IsQuestionsAdmin;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("CanEditCommunity", CanEditCommunity);
        result.put("CanArchiveCommunity", CanArchiveCommunity);
        result.put("CanAcceptInvites", CanAcceptInvites);
        result.put("IsCommunityAdmin", IsCommunityAdmin);
        result.put("CanEditPost", CanEditPost);
        result.put("CanCreatePost", CanCreatePost);
        result.put("CanDeletePost", CanDeletePost);
        result.put("CanAbusePost", CanAbusePost);
        result.put("CanReplyPost", CanReplyPost);
        result.put("IsPostAdmin", IsPostAdmin);
        result.put("CanEditPoll", CanEditPoll);
        result.put("CanCreatePoll", CanCreatePoll);
        result.put("CanDeletePoll", CanDeletePoll);
        result.put("CanAbusePoll", CanAbusePoll);
        result.put("CanViewPollDashboard", CanViewPollDashboard);
        result.put("IsPollAdmin", IsPollAdmin);
        result.put("CanEditEvents", CanEditEvents);
        result.put("CanCreateEvents", CanCreateEvents);
        result.put("CanDeleteEvents", CanDeleteEvents);
        result.put("CanAbuseEvents", CanAbuseEvents);
        result.put("IsEventsAdmin", IsEventsAdmin);
        result.put("CanEditNotification", CanEditNotification);
        result.put("CanCreateNotification", CanCreateNotification);
        result.put("CanDeleteNotification", CanDeleteNotification);
        result.put("CanAbuseNotification", CanAbuseNotification);
        result.put("IsNotificationAdmin", IsNotificationAdmin);
        result.put("CanEditQuestions", CanEditQuestions);
        result.put("CanCreateQuestions", CanCreateQuestions);
        result.put("CanDeleteQuestions", CanDeleteQuestions);
        result.put("CanAbuseQuestions", CanAbuseQuestions);
        result.put("IsQuestionsAdmin", IsQuestionsAdmin);
        return result;
    }
}
