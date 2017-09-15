package com.gap22.community.apartment.Dao;


import android.util.Log;
import android.widget.Toast;

import com.gap22.community.apartment.Database.Member;
import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.gap22.community.apartment.entities.Community;
import com.gap22.community.apartment.entities.ActionResponse;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
