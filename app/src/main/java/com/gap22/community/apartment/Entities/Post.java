package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {

    private long community_id;
    private long post_id;
    private String post_author;
    private Date post_date;
    private String post_content;
    private String post_title;
    private boolean post_status;
    private String comment_status;
    private String post_password;
    private Date post_modified;
    private String post_parent;
    private String post_type;
    private String post_responses;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(long post_id, String post_author, Date post_date, String post_content,
                String post_title, boolean post_status, String comment_status,
                String post_password, Date post_modified, String post_parent,
                String post_type, String post_responses) {
        this.post_id = post_id;
        this.post_author = post_author;
        this.post_date = post_date;
        this.post_date = post_date;
        this.post_content = post_content;
        this.post_title = post_title;
        this.post_status = post_status;
        this.comment_status = comment_status;
        this.post_password = post_password;
        this.post_modified = post_modified;
        this.post_modified = post_modified;
        this.post_parent = post_parent;
        this.post_type = post_type;
        this.post_responses = post_responses;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("community_id", community_id);
        result.put("post_id", post_id);
        result.put("post_author", post_author);
        result.put("post_date", post_date);
        result.put("post_date", post_date);
        result.put("post_content", post_content);
        result.put("post_title", post_title);
        result.put("post_status", post_status);
        result.put("comment_status", comment_status);
        result.put("post_password", post_password);
        result.put("post_modified", post_modified);
        result.put("post_modified", post_modified);
        result.put("post_parent", post_parent);
        result.put("post_type", post_type);
        result.put("post_responses", post_responses);
        return result;
    }
}
