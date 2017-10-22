package com.gap22.community.apartment.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Entities.Post;
import com.gap22.community.apartment.PostActivity;
import com.gap22.community.apartment.PostResponse;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;


public class PostFragment extends Fragment {

    ListView lview;
    private DatabaseReference mDatabase,mpostresponse;
    private FirebaseAuth fireauth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StoragePreferences storagePref;
    private ProgressDialog progress;
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




            if (GlobalValues.getSecurityGroupSettings().CanCreatePost==true) {
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
            }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostActivity.class));
                getActivity().finish();
            }
        });
        lview = (ListView) fragPostView.findViewById(R.id.listView2);
String CommunityId = GlobalValues.getCommunityId();
        mDatabase = FirebaseDatabase.getInstance().getReference(CommunityId.trim()).child("Post");
        mpostresponse = FirebaseDatabase.getInstance().getReference(CommunityId.trim()).child("Post");

        fireauth = FirebaseAuth.getInstance();






                            /*long postCount = dataSnapshot.getChildrenCount();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Posts post = postSnapshot.getValue(Posts.class);
                                Toast.makeText(getActivity(), post.getTitle(), Toast.LENGTH_SHORT).show();
                            }*/



        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading Data");
        progress.show();

        final FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), Post.class, R.layout.post_list_item, mDatabase) {
            @Override
            protected void populateView(View v, Object model, int position) {
                    progress.dismiss();
                String postId = getRef(position).getKey();
                Post p = (Post) model;

                ((TextView) v.findViewById(R.id.title)).setText(p.title);
                ((TextView) v.findViewById(R.id.short_message)).setText(p.content);
                final TextView responsecount = ((TextView) v.findViewById(R.id.no_of_replies));

                mpostresponse.child(postId).child("post_responses").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            responsecount.setText(dataSnapshot.getChildrenCount() + " - Replies");
                            responsecount.setTextColor(Color.YELLOW);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                Date today = new Date();
                long diff = today.getTime() - p.created_date.getTime();
                int days = (int) (diff / (1000 * 60 * 60 * 24));
                int hours = (int) (diff / (1000 * 60 * 60));
                int minutes = (int) (diff / (1000 * 60));
                int seconds = (int) (diff / (1000));
                ((TextView) v.findViewById(R.id.no_of_days)).setText(days + "Days ," + hours + "hours," + minutes + "minutes");


                StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com/" + p.author + ".jpg");

// ImageView in your Activity
                ImageView imageView = (ImageView) v.findViewById(R.id.img_user_image);

// Load the image using Glide
                Glide.with(getActivity())
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(imageView);
            }
        };




        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                {
                    Post p = (Post) adapter.getItem(position);
                    String postId= adapter.getRef(position).getKey();
                    Bundle bundle = new Bundle();
                    bundle.putString("Post",p.content);
                    bundle.putString("Title",p.title);
                    bundle.putString("PostId",postId );
                    // bundle.putInt("ResponseCount",p.getResponses());
                    Intent i = new Intent(getActivity(), PostResponse.class);
                    i.putExtras(bundle);

//Fire that second activity
                    startActivity(i);

                }

            }
        });




        // Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");

        return fragPostView;

    }
}