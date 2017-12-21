package com.gap22.community.apartment.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Entities.Members;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gap22.community.apartment.Entities.GlobalUser;

public class AcceptInvite extends Fragment {

    private DatabaseReference drInvites;
    private FirebaseAuth fbAuthentication;
    ListView lvInvites;

    public AcceptInvite() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frageApproveInvite = inflater.inflate(R.layout.fragment_approve_invite_request, container, false);
        lvInvites = (ListView) frageApproveInvite.findViewById(R.id.list_Post_Reponse);
        LoadInvtesRequest();
        return frageApproveInvite;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Accept Invite");
    }

    private void LoadInvtesRequest() {
        fbAuthentication = FirebaseAuth.getInstance();
        final String CommunityId = GlobalValues.getCommunityId();
        drInvites = FirebaseDatabase.getInstance().getReference(CommunityId).child("Invites");

        FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), com.gap22.community.apartment.Entities.PostResponses.class, R.layout.viewpostresponse, drInvites) {
            @Override
            protected void populateView(View v, Object model, int position) {

                GlobalUser globalUser = (GlobalUser) model;
                final TextView name = (TextView) v.findViewById(R.id.text_User_Display_Name);
                final TextView response = (TextView) v.findViewById(R.id.text_User_Response);
                //response.setText(globalUser);
            }
        };

        lvInvites.setAdapter(adapter);
    }
}
