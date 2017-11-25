package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Entities.Poll;
import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.PollResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PollsDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();

    public PollsDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }


    public ActionResponse CreatePoll(String communityId, String pollId, Poll poll) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Poll").child(pollId).setValue(poll);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse UpdatePoll(String communityId, String pollId, Poll poll) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Poll").child(pollId).setValue(poll);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse CreatePollResponse(String communityId, String pollId, String memberId, PollResponse pollResponse) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Poll").child(pollId).child("poll_responses").child(memberId).setValue(pollResponse);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

}
