package com.gap22.community.apartment.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Database.Member;
import com.gap22.community.apartment.R;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class CommunityFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    FirebaseListAdapter adapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StoragePreferences storagePref;
    private ProgressDialog progress;

    ListView lview;
    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragCommView =inflater.inflate(R.layout.fragment_community, container, false);
        progress = new ProgressDialog(getActivity());
        FloatingActionButton fab = (FloatingActionButton) fragCommView.findViewById(R.id.fab_);
        storagePref = StoragePreferences.getInstance(getActivity());
        String storageUserId = storagePref.getPreference("type");
        if (storageUserId != "") {

            if (storageUserId.equals("admin"))
            {
                fab.setVisibility(View.VISIBLE);
            }
            else
            {
                fab.setVisibility(View.GONE);
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new AppInviteInvitation.IntentBuilder("Communify App")
                        .setMessage("please install the app")
                        .setDeepLink(Uri.parse("https://play.google.com/store/apps/details?id=com.vazhagavalamudhan.vethathiri&hl=en"))
                        //.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))

                        .setEmailSubject("App Invite")
                        .setEmailHtmlContent("<html><body>https://play.google.com/store/apps/details?id=com.vazhagavalamudhan.vethathiri&hl=en<br>Install</body></html>")
                        .build();
                startActivityForResult(intent, 59);
            }
        });
        lview = (ListView) fragCommView.findViewById(R.id.listView2);

        mDatabase = FirebaseDatabase.getInstance().getReference("member");
        fireauth = FirebaseAuth.getInstance();

        // Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");
        progress.setMessage("Loading Data");
        progress.show();
        adapter = new FirebaseListAdapter(getActivity(), Member.class, R.layout.community_list_item, mDatabase) {
            @Override
            protected void populateView(View v, Object model, int position) {
                progress.dismiss();
                Member m = (Member) model;
                //((TextView) v.findViewById(R.id.unit)).setText(m.getUnit());
                ((TextView) v.findViewById(R.id.title)).setText(m.getFirstname());
                ((TextView) v.findViewById(R.id.role)).setText("Active");
                String obj = this.getRef(position).getKey();

                StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com/"+obj+".jpg");

// ImageView in your Activity
                ImageView imageView = (ImageView)v.findViewById(R.id.img_user_image);

// Load the image using Glide
                Glide.with(getActivity() )
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(imageView);

            }
        };
        lview.setAdapter(adapter);
        return fragCommView;
    }
}
