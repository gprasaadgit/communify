package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.Community;
import com.gap22.community.apartment.Entities.Events;
import com.gap22.community.apartment.Entities.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by NARAYANAN on 10/2/2017.
 */

public class EventsDao {

    private DatabaseReference dbReference;
    private FirebaseAuth fbAuthentication;
    private KeyGenerator keyGen = new KeyGenerator();


    public EventsDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public ActionResponse CreateEvents(String communityId, String eventId, Events events) {
        ActionResponse response = new ActionResponse();
        dbReference.child(communityId).child("Events").child(eventId).setValue(events);
        return response;
    }

    public ActionResponse UpdateEvents(String communityId, Community community, String memberId, Members members) {
        ActionResponse response = new ActionResponse();

        return response;
    }

    public ActionResponse DeleteEvents(String communityId, Community community, String memberId, Members members) {
        ActionResponse response = new ActionResponse();

        return response;
    }
}
