package com.gap22.community.apartment.Dao;

import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.gap22.community.apartment.Entities.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

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

    public ActionResponse AcceptInviteCommunityInvite(String communityId, String userUid, GlobalUser globalUser) {
        ActionResponse response = new ActionResponse();
        MembersDao membersDao = new MembersDao();
        Date createdDate = new Date();

        Members members = new Members(globalUser.email, createdDate, null, Members.Status.Active, globalUser.first_name, globalUser.last_name,
                0, 0, 0, globalUser.title, "", globalUser.phone, "", "", "", "NORMAL-USER");

        membersDao.CreateMembers(communityId, userUid, members);
        dbReference.child(communityId).child("Invites").child(userUid).setValue(null);
        return response;
    }

    public ActionResponse RejectInviteCommunityInvite(String communityId, String userUid) {
        ActionResponse response = new ActionResponse();
        dbReference.child(communityId).child("Invites").child(userUid).setValue(null);
        return response;
    }
}
