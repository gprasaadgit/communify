package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PostComments {

    private long comment_Id;
    private long comment_post_Id;
    private String comment_author;
    private String comment_author_IP;
    private Date comment_date;
    private String comment_content;
    private boolean comment_approved;
    private String comment_type;
    private String comment_parent;
    private String user_id;

    public PostComments() {

    }

    public PostComments(long comment_Id, long comment_post_Id, String comment_author,
                        String comment_author_IP, Date comment_date, String comment_content,
                        boolean comment_approved, String comment_type, String comment_parent,
                        String user_id) {
        this.comment_Id = comment_Id;
        this.comment_post_Id = comment_post_Id;
        this.comment_author = comment_author;
        this.comment_author_IP = comment_author_IP;
        this.comment_date = comment_date;
        this.comment_content = comment_content;
        this.comment_approved = comment_approved;
        this.comment_type = comment_type;
        this.comment_parent = comment_parent;
        this.user_id = user_id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("comment_Id", comment_Id);
        result.put("comment_post_ID", comment_post_Id);
        result.put("comment_author", comment_author);
        result.put("comment_author_IP", comment_author_IP);
        result.put("comment_date", comment_date);
        result.put("comment_content", comment_content);
        result.put("comment_approved", comment_approved);
        result.put("comment_type", comment_type);
        result.put("comment_parent", comment_parent);
        result.put("user_id", user_id);
        return result;
    }
}
