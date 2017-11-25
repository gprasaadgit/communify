package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.CommunityInviteCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommunityInviteCodeDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();


    public CommunityInviteCodeDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public ActionResponse CreateInviteCode(String communityId, CommunityInviteCode communityInviteCode) {
        ActionResponse response = new ActionResponse();
        dbReference.child("INVITES").child(communityId).setValue(communityInviteCode);
        return response;
    }
}
