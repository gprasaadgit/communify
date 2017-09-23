package com.gap22.community.apartment.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Database.Posts;
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

import java.util.concurrent.TimeUnit;


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

            if (storageUserId.equals("admin"))
            {
               fab.setVisibility(View.VISIBLE);
            }
            else
            {
                fab.setVisibility(View.GONE);
            }
        }
        String CommunityId = storagePref.getPreference("CommunityID");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostActivity.class));
                getActivity().finish();
            }
        });
        lview = (ListView) fragPostView.findViewById(R.id.listView2);
        mDatabase = FirebaseDatabase.getInstance().getReference("post").child(CommunityId);
        fireauth = FirebaseAuth.getInstance();


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                     ProgressDialog progress;
                    progress = new ProgressDialog(getActivity());
                    progress.setMessage("Loading Data");
                    progress.show();

                    final FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), Posts.class, R.layout.post_list_item, mDatabase) {
                        @Override
                        protected void populateView(View v, Object model, int position) {
                        //    progress.dismiss();

                            Posts p = (Posts) model;
                            ((TextView) v.findViewById(R.id.title)).setText(p.getTitle());
                            ((TextView) v.findViewById(R.id.short_message)).setText(p.getBody());
                            ((TextView)v.findViewById(R.id.no_of_replies)).setText(p.getResponses()+"Replies");

                            if(p.getDate()!= 0)
                            {


                                long diffInMillisec = System.currentTimeMillis() - p.getDate();
                                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
                                long seconds = diffInSec % 60;
                                diffInSec/= 60;
                                long minutes =diffInSec % 60;
                                diffInSec /= 60;
                                long  hours = diffInSec % 24;
                                diffInSec /= 24;
                                long  days = diffInSec;
                                ((TextView) v.findViewById(R.id.no_of_days)).setText(days + "Days ," + hours + "hours," + minutes + "minutes");
                            }



               /* if(p.)
                StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com/"+p.getAuthor()+".jpg");

// ImageView in your Activity
                ImageView imageView = (ImageView)v.findViewById(R.id.img);

// Load the image using Glide
                Glide.with(getActivity() )
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(imageView);*/
                        }
                    };

                    lview.setAdapter(adapter);

                    lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            {
                                Posts p = (Posts) adapter.getItem(position);
                                String postId= adapter.getRef(position).getKey();
                                Bundle bundle = new Bundle();
                                bundle.putString("Post",p.getBody());
                                bundle.putString("Title",p.getTitle());
                                bundle.putString("PostId",postId );
                                bundle.putInt("ResponseCount",p.getResponses());
                                Intent i = new Intent(getActivity(), PostResponse.class);
                                i.putExtras(bundle);

//Fire that second activity
                                startActivity(i);

                            }

                        }
                    });


                }
                else
                {
                    //progress.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");
        final FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), Posts.class, R.layout.post_list_item, mDatabase) {
            @Override
            protected void populateView(View v, Object model, int position) {
               // progress.dismiss();

                Posts p = (Posts) model;
                ((TextView) v.findViewById(R.id.title)).setText(p.getTitle());
                ((TextView) v.findViewById(R.id.short_message)).setText(p.getBody());
                ((TextView)v.findViewById(R.id.no_of_replies)).setText(p.getResponses()+"Replies");

                if(p.getDate()!= 0)
                {


                    long diffInMillisec = System.currentTimeMillis() - p.getDate();
                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
                    long seconds = diffInSec % 60;
                    diffInSec/= 60;
                    long minutes =diffInSec % 60;
                    diffInSec /= 60;
                    long  hours = diffInSec % 24;
                    diffInSec /= 24;
                    long  days = diffInSec;
                    ((TextView) v.findViewById(R.id.no_of_days)).setText(days + "Days ," + hours + "hours," + minutes + "minutes");
                }



               /* if(p.)
                StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com/"+p.getAuthor()+".jpg");

// ImageView in your Activity
                ImageView imageView = (ImageView)v.findViewById(R.id.img);

// Load the image using Glide
                Glide.with(getActivity() )
                        .using(new FirebaseImageLoader())
                        .load(storageRef)
                        .into(imageView);*/
            }
        };

        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                {
                    Posts p = (Posts) adapter.getItem(position);
                    String postId= adapter.getRef(position).getKey();
                    Bundle bundle = new Bundle();
                    bundle.putString("Post",p.getBody());
                    bundle.putString("Title",p.getTitle());
                    bundle.putString("PostId",postId );
                    bundle.putInt("ResponseCount",p.getResponses());
                    Intent i = new Intent(getActivity(), PostResponse.class);
                    i.putExtras(bundle);

//Fire that second activity
                    startActivity(i);

                }

            }
        });
        return fragPostView;

    }
}
