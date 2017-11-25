package com.gap22.community.apartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.IPAddress;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PostDao;
import com.gap22.community.apartment.Dao.KeyGenerator;
import com.gap22.community.apartment.Entities.Members;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostResponses extends Fragment {
    private TextView title;
    private EditText et_post_response_message;
    ListView lview;


    private DatabaseReference mDatabase,mpost,mmember;
    private FirebaseAuth fireauth;
    private String pollId;
    private String postQuestion;
    private Button Response;
    private int responsecount;
    private StoragePreferences storagePref;

    public PostResponses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View postresponses = inflater.inflate(R.layout.fragment_post_responses, container, false);
        lview = (ListView) postresponses.findViewById(R.id.list_Post_Reponse);
        //  post =(TextView) findViewById(R.id.Post);
        title =(TextView)postresponses.findViewById(R.id.tview_title);
        et_post_response_message =(EditText) postresponses.findViewById(R.id.et_post_response_message);

        fireauth = FirebaseAuth.getInstance();
        Bundle bundle = this.getArguments();
        pollId = bundle.getString("PostId");
        postQuestion = bundle.getString("Post");
        //title.setText( bundle.getString("Title"));
        responsecount = bundle.getInt("ResponseCount");
        title.setText(postQuestion);
        storagePref = StoragePreferences.getInstance(getActivity());
        Response =(Button) postresponses.findViewById(R.id.btn_send);
        final String CommunityId = GlobalValues.getCommunityId();
        mDatabase = FirebaseDatabase.getInstance().getReference(CommunityId).child("Post").child(pollId).child("post_responses");

        mmember = FirebaseDatabase.getInstance().getReference(CommunityId).child("Members");


        FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), com.gap22.community.apartment.Entities.PostResponses.class, R.layout.viewpostresponse, mDatabase)
        {

            @Override
            protected void populateView(View v, Object model, int position) {

                com.gap22.community.apartment.Entities.PostResponses ob =(com.gap22.community.apartment.Entities.PostResponses) model;




                final TextView name = (TextView) v.findViewById(R.id.text_User_Display_Name);

                final TextView response = (TextView)v.findViewById(R.id.text_User_Response);
                response.setText(ob.content);

                mmember.child(ob.author).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            Members mem = dataSnapshot.getValue(Members.class);
                            name.setText(mem.first_name);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //  name.setText();





            }
        };

        lview.setAdapter(adapter);


        Response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.

                final String srt = et_post_response_message .getText().toString();
                PostDao postDao = new PostDao();
                Date createdDate = new Date();
                KeyGenerator keyGen = new KeyGenerator();
                com.gap22.community.apartment.Entities.PostResponses postResponse = new com.gap22.community.apartment.Entities.PostResponses(fireauth.getCurrentUser().getUid(), IPAddress.getMACAddress("wlan0"), IPAddress.getMACAddress("eth0"), createdDate, srt , com.gap22.community.apartment.Entities.PostResponses.Status.Approved, "", "");
                postDao.CreatePostResponse(CommunityId, pollId, keyGen.GetNewPostCommentKey(), postResponse);

                Toast.makeText(getActivity(), "Response Submitted", Toast.LENGTH_SHORT).show();

et_post_response_message.setText("");





            }
        });


        return postresponses;
    }

}
