package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommunityInvitesDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGenerator;

    public CommunityInvitesDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
        fbAuthentication = FirebaseAuth.getInstance();
        keyGenerator = new KeyGenerator();
    }

    public ActionResponse CreateCommunityInvites(String communityId, String userUid, GlobalUser globalUser) {
        ActionResponse response = new ActionResponse();
        dbReference.child(communityId).child("Invites").child(userUid).setValue(globalUser);
        return response;
    }
}
