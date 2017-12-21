package com.gap22.community.apartment.Dao;

import android.support.annotation.NonNull;

import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalUserDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();
    String userId = "";
    private GlobalUser tempGlobalUser;

    public GlobalUserDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
        fbAuthentication = FirebaseAuth.getInstance();
    }

    public ActionResponse CreateUser(GlobalUser globalUser, String userId) {
        ActionResponse response = new ActionResponse();
        tempGlobalUser = globalUser;

        try {
            dbReference.child("USER-DIRECTORY").child(userId).setValue(tempGlobalUser);
            response.success = true;
            response.value = userId;
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }
}
