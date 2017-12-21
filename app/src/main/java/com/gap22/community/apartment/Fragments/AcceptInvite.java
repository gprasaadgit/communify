package com.gap22.community.apartment.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Dao.CommunityInvitesDao;
import com.gap22.community.apartment.Entities.CommunityInviteCode;
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
    private FirebaseListAdapter flAdapter;
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
        lvInvites = (ListView) frageApproveInvite.findViewById(R.id.lvInvites);
        LoadInvtesRequest();
        return frageApproveInvite;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Accept Invite");
    }

    private void LoadInvtesRequest() {
        final String CommunityId = GlobalValues.getCommunityId();
        drInvites = FirebaseDatabase.getInstance().getReference(CommunityId).child("Invites");
        fbAuthentication = FirebaseAuth.getInstance();

        flAdapter = new FirebaseListAdapter(getActivity(), GlobalUser.class, R.layout.list_items_accept_invites, drInvites) {
            @Override
            protected void populateView(View v, Object model, final int position) {

                final GlobalUser globalUser = (GlobalUser) model;
                final CommunityInvitesDao communityInvitesDao = new CommunityInvitesDao();

                ((TextView) v.findViewById(R.id.tview_firstname_value)).setText(globalUser.first_name);
                ((TextView) v.findViewById(R.id.tview_lastname_value)).setText(globalUser.last_name);
                ((TextView) v.findViewById(R.id.tview_emailid_value)).setText(globalUser.email);
                ((TextView) v.findViewById(R.id.tview_phone_value)).setText(globalUser.phone);

                v.findViewById(R.id.btn_accept_invite).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String memberId = flAdapter.getRef(position).getKey();
                        communityInvitesDao.AcceptInviteCommunityInvite(CommunityId, memberId, globalUser);
                        Toast.makeText(getActivity(), "Member Added To Our Community", Toast.LENGTH_SHORT).show();
                        LoadInvtesRequest();
                    }
                });

                v.findViewById(R.id.btn_reject_invite).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String memberId = flAdapter.getRef(position).getKey();
                        communityInvitesDao.RejectInviteCommunityInvite(CommunityId, memberId);
                        Toast.makeText(getActivity(), "Member Request Rejected From Our Community", Toast.LENGTH_SHORT).show();
                        LoadInvtesRequest();
                    }
                });
            }
        };

        lvInvites.setAdapter(flAdapter);
    }
}
