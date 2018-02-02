package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.Post;
import com.gap22.community.apartment.Entities.PostResponses;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class   PostDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();

    public PostDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public ActionResponse CreatePost(String communityId, String postId, Post post) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Post").child(postId).setValue(post);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse UpdatePost(String communityId, String postId, Post post) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Post").child(postId).setValue(post);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse DeletePost(String communityId,String postid)
    { ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Post").child(postid).removeValue();
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;

    }
    public ActionResponse CreatePostResponse(String communityId, String postId, String postResponseId, PostResponses postResponse) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Post").child(postId).child("post_responses").child(postResponseId).setValue(postResponse);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

}