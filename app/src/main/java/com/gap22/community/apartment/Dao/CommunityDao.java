package com.gap22.community.apartment.Dao;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.gap22.community.apartment.Entities.Community;
import com.gap22.community.apartment.Entities.ActionResponse;
import com.google.firebase.database.FirebaseDatabase;
import com.gap22.community.apartment.Database.KeyGenerator;

import java.util.List;
import java.util.ArrayList;

public class CommunityDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();

    public CommunityDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public List<Community> GetAllCommunity() {
        final List<Community> response = new ArrayList<Community>();
        return response;
    }

    public Community GetCommunityById(String community_id) {
        Community response = new Community();

        return response;
    }

    public ActionResponse CreateCommunity(Community community) {
        ActionResponse response = new ActionResponse();
        String newKey = keyGen.GetNewCommunityKey();

        try {
            dbReference.child("community").child(newKey).setValue(community);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse UpdateCommunity(String community_id, Community community) {
        ActionResponse response = new ActionResponse();

        try {
            dbReference.child("/community/").child(community_id).updateChildren(community.toMap());
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse DeleteCommunity(String community_id) {
        ActionResponse response = new ActionResponse();

        try {
            dbReference.child("/community/").child(community_id).removeValue();
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }
}
