package com.gap22.community.apartment.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Entities.Post;
import com.gap22.community.apartment.PostActivity;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


public class PostFragment extends Fragment {

    ListView lview;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StoragePreferences storagePref;

    public PostFragment() {
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
        View fragPostView = inflater.inflate(R.layout.fragment_post, container, false);


        FloatingActionButton fab = (FloatingActionButton) fragPostView.findViewById(R.id.fab_);
        storagePref = StoragePreferences.getInstance(getActivity());


        String storageUserId = storagePref.getPreference("type");
        if (storageUserId != "") {

            if (storageUserId.equals("admin")) {
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostActivity.class));
                getActivity().finish();
            }
        });
        lview = (ListView) fragPostView.findViewById(R.id.listView2);

        String communityId = GlobalValues.getCommunityId();
        mDatabase = FirebaseDatabase.getInstance().getReference(communityId).child("Post");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //long postCount = snapshot.getChildrenCount();
                    //for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Posts post = postSnapshot.getValue(Posts.class);

                    final FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), Post.class, R.layout.post_list_item, mDatabase) {
                        @Override
                        protected void populateView(View v, Object model, int position) {
                            //    progress.dismiss();

                            Post post = (Post) model;
                            if (post != null) {
                                ((TextView) v.findViewById(R.id.title)).setText(post.title);
                                ((TextView) v.findViewById(R.id.short_message)).setText(post.content);
                                ((TextView) v.findViewById(R.id.no_of_replies)).setText("3 Replies");
                            }
                        }
                    };
                    //}
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return fragPostView;
    }
}
