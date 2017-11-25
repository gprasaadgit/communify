package com.gap22.community.apartment.Dao;


import com.gap22.community.apartment.Entities.Members;
import com.gap22.community.apartment.Entities.SecurityGroupSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.gap22.community.apartment.Entities.Community;
import com.gap22.community.apartment.Entities.ActionResponse;
import com.google.firebase.database.FirebaseDatabase;

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

    public ActionResponse CreateCommunity(String communityId, Community community, String memberId, Members members) {
        ActionResponse response = new ActionResponse();
        SecurityDao securityDao = new SecurityDao();
        MembersDao membersDao = new MembersDao();
        SecurityGroupSettings securityGroupSettings;

        //try {
        dbReference.child(communityId).child("Profile").setValue(community);
        securityGroupSettings = new SecurityGroupSettings(true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true);
        securityDao.CreateSecurityGroupWithRights(communityId, "ADMIN", securityGroupSettings);
        membersDao.CreateMembers(communityId, memberId, members);
        //} catch (Exception e) {
        //    response.error_message = e.getMessage();
        //    response.success = false;
        //}

        return response;
    }

    public ActionResponse UpdateCommunity(String community_id, Community community) {
        ActionResponse response = new ActionResponse();

        try {
            dbReference.child(community_id).updateChildren(community.toMap());
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }

    public ActionResponse DeleteCommunity(String community_id) {
        ActionResponse response = new ActionResponse();

        try {
            dbReference.child(community_id).removeValue();
        } catch (Exception e) {
            response.error_message = e.getMessage();
            response.success = false;
        }

        return response;
    }
}
