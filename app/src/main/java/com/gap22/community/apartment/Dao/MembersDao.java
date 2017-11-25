package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nxd7826 on 13-09-2017.
 */

public class MembersDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();

    public MembersDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public ActionResponse CreateMembers(String communityId, String id, Members members) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Members").child(id).setValue(members);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

}
