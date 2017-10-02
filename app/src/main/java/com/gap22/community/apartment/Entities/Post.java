package com.gap22.community.apartment.Entities;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {

    public String author;
    public Date created_date;
    public String content;
    public String title;
    public String status;
    public String comment_status;
    public String password;
    public Date modified;
    public String parent;
    public String type;
    public List<PostResponse> responses;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(String author, Date created_date, String content,
                String title, Status status, CommentStatus comment_status,
                String password, Date modified, String parent,
                String type, List<PostResponse> responses) {
        this.author = author;
        this.created_date = created_date;
        this.content = content;
        this.title = title;
        this.status = status.value;
        this.comment_status = comment_status.value;
        this.password = password;
        this.modified = modified;
        this.modified = modified;
        this.parent = parent;
        this.type = type;
        this.responses = responses;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("author", author);
        result.put("created_date", created_date);
        result.put("content", content);
        result.put("title", title);
        result.put("status", status);
        result.put("comment_status", comment_status);
        result.put("password", password);
        result.put("modified", modified);
        result.put("modified", modified);
        result.put("parent", parent);
        result.put("type", type);
        result.put("responses", responses);
        return result;
    }

    public enum Status {
        Active("A"), InActive("I"), Archive("X");
        public String value;

        Status(String value) {
            this.value = value;
        }
    }

    public enum CommentStatus {
        EveryOne("E"), OnlyAdmin("A"), NoComments("N");
        public String value;

        CommentStatus(String value) {
            this.value = value;
        }
    }
}
