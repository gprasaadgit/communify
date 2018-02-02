package com.gap22.community.apartment.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gap22.community.apartment.Common.CommonFunctions;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PostDao;
import com.gap22.community.apartment.Entities.Post;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PostFragment extends Fragment {

    ListView lview;
    private DatabaseReference mDatabase, mpostresponse;
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


        if (GlobalValues.getSecurityGroupSettings().CanCreatePost == true) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment CreatePost = new CreatePost();


                               /* Intent i = new Intent(getActivity(), PollResultActivity.class);
                                i.putExtras(bundle);*/

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, CreatePost);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        lview = (ListView) fragPostView.findViewById(R.id.listView2);
        final String CommunityId = GlobalValues.getCommunityId();
        mDatabase = FirebaseDatabase.getInstance().getReference(CommunityId.trim()).child("Post");
        mpostresponse = FirebaseDatabase.getInstance().getReference(CommunityId.trim()).child("Post");

        fireauth = FirebaseAuth.getInstance();


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
               final ImageView del = ((ImageView)v.findViewById(R.id.del));
                if(GlobalValues.getSecurityGroupSettings().CanDeletePost==true)
                {
                   del.setVisibility(View.VISIBLE);
                }
                else
                {
                    del.setVisibility(View.INVISIBLE);
                }
del.setTag(postId);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());





                        dialogBuilder.setTitle("Delete Post");
                        dialogBuilder.setMessage("Are you sure to delete the Post ");
                        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //do something with edt.getText().toString();


                                PostDao postDao = new PostDao();
                           String id = del.getTag().toString();
                                postDao.DeletePost(CommunityId, id);

                                Toast.makeText(getActivity(), "Response Submitted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //pass
                                dialog.cancel();
                            }
                        });
                        AlertDialog b = dialogBuilder.create();
                        b.show();

                    }
                });
                final TextView responsecount = ((TextView) v.findViewById(R.id.no_of_replies));

                mpostresponse.child(postId).child("post_responses").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            responsecount.setText(dataSnapshot.getChildrenCount() + " - Replies");
                            // responsecount.setTextColor(Color.YELLOW);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*Date today = new Date();
                long diff = today.getTime() - p.created_date.getTime();
                int days = (int) (diff / (1000 * 60 * 60 * 24));
                int hours = (int) (diff / (1000 * 60 * 60));
                int minutes = (int) (diff / (1000 * 60));
                int seconds = (int) (diff / (1000));*/
                ((TextView) v.findViewById(R.id.no_of_days)).setText(CommonFunctions.GetDaysByWords(p.created_date.getTime()));
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
                    String postId = adapter.getRef(position).getKey();
                    Bundle bundle = new Bundle();
                    bundle.putString("Post", p.content);
                    bundle.putString("Title", p.title);
                    bundle.putString("PostId", postId);
                    // bundle.putInt("ResponseCount",p.getResponses());
                    Fragment PostResponse = new PostResponses();

                    PostResponse.setArguments(bundle);
                               /* Intent i = new Intent(getActivity(), PollResultActivity.class);
                                i.putExtras(bundle);*/

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, PostResponse);
                    ft.addToBackStack(null);
                    ft.commit();

                }

            }
        });


        // Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");

        return fragPostView;

    }
}