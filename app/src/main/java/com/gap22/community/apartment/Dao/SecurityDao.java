package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.SecurityGroupSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecurityDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();

    public SecurityDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public ActionResponse CreateSecurityGroupWithRights(String communityId, SecurityGroupSettings securityGroupSettings) {
        ActionResponse response = new ActionResponse();
        try {
            dbReference.child(communityId).child("Security").child(securityGroupSettings.name).setValue(securityGroupSettings);
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }
}
