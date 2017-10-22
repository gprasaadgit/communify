package com.gap22.community.apartment.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Entities.Community;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gap22.community.apartment.R.id.tview_address_value;
import static com.gap22.community.apartment.R.id.tview_builder_value;
import static com.gap22.community.apartment.R.id.tview_owner_value;
import static com.gap22.community.apartment.R.id.tview_tax_id_value;
import static com.gap22.community.apartment.R.id.tview_title_value;


public class MyCommunity extends Fragment {
    private int mIndex;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    private StoragePreferences storagePref;
    private TextView Title,Address,TaxId,Builder,Owner;

    public MyCommunity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(getActivity(), "MONOSPACE", "fonts/avenirltstd-book.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragPollView = inflater.inflate(R.layout.fragment_my_community, container, false);
        Title = (TextView) fragPollView.findViewById(tview_title_value);
        Address = (TextView) fragPollView.findViewById(tview_address_value);
        TaxId = (TextView) fragPollView.findViewById(tview_tax_id_value);
        Builder = (TextView) fragPollView.findViewById(tview_builder_value);
        Owner = (TextView) fragPollView.findViewById(tview_owner_value);

        mDatabase = FirebaseDatabase.getInstance().getReference("community");
        storagePref = StoragePreferences.getInstance(getActivity());
        String cid = GlobalValues.getCommunityId();
        fireauth = FirebaseAuth.getInstance();

        // Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");
        mDatabase.child(cid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Community c = dataSnapshot.getValue(Community.class);
                    Title.setText(c.title);
                    Address.setText(c.address1);
                    TaxId.setText(c.taxid);
                    Builder.setText(c.builder);
                    Owner.setText(c.owner);



                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return fragPollView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Community");
    }
}
