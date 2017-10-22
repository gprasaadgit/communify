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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Entities.Poll;
import com.gap22.community.apartment.PollActivity;
import com.gap22.community.apartment.PollResultActivity;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PollsFragment extends Fragment {
    ListView lview;

    ArrayList<Poll> poll;
    private FirebaseAuth fireauth;
    private StoragePreferences storagePref;
    private ProgressDialog progress;
    private DatabaseReference mDatabase,mpollresults;
    public PollsFragment() {
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
        View fragPollView = inflater.inflate(R.layout.fragment_polls, container, false);
        FloatingActionButton fab = (FloatingActionButton) fragPollView.findViewById(R.id.fab_);
        storagePref = StoragePreferences.getInstance(getActivity());
        progress = new ProgressDialog(getActivity());
       final String storageUserId = storagePref.getPreference("type");
        String CommunityId = GlobalValues.getCommunityId();
       {

            if (GlobalValues.getSecurityGroupSettings().CanCreatePoll==true)
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
                startActivity(new Intent(getActivity(), PollActivity.class));
                getActivity().finish();
            }
        });

        lview = (ListView) fragPollView.findViewById(R.id.listView2);
        fireauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(CommunityId).child("Poll");
        mpollresults= FirebaseDatabase.getInstance().getReference(CommunityId).child("Poll");


        progress.setMessage("Loading Data");
        progress.show();
        // Firebase ref = new Firebase("https://<yourapp>.firebaseio.com");
        final FirebaseListAdapter adapter = new FirebaseListAdapter(getActivity(), Poll.class, R.layout.poll_list_item, mDatabase) {
            @Override
            protected void populateView(View v, Object model, int position) {
                progress.dismiss();
                Poll p = (Poll) model;

                ((TextView) v.findViewById(R.id.title)).setText(p.question);
              /*  if (p.getStatus().equalsIgnoreCase("Active")) {
                    ((TextView) v.findViewById(R.id.textView2)).setText("OPEN");
                }
                else
                {
                    ((TextView) v.findViewById(R.id.textView2)).setText("CLOSED");
                      +"-"+p.getOption1().get("count"));
                }*/

                ((TextView) v.findViewById(R.id.progress_title_1)).setText(p.option1);

                ((TextView) v.findViewById(R.id.progress_title_2)).setText(p.option2);

                ((TextView) v.findViewById(R.id.progress_title_3)).setText(p.option3);

               /* long total = (long) p.getOption1().get("count") + (long) p.getOption2().get("count") +(long) p.getOption3().get("count") ;
                long count1 = (long) p.getOption1().get("count");
                long count2 = (long) p.getOption2().get("count");
                long count3 = (long) p.getOption3().get("count");
                float pb11 =0 ,pb12 =0,pb13 = (float) 0.0;
if(total!=0) {
     pb11 = (int) count1 / (int) total;
     pb12 = (int) count2 / (int) total;
     pb13 = (int) count3 / (int) total;
}*/

             ProgressBar pb = (ProgressBar)v.findViewById(R.id.pb_option1);
                pb.setProgress((int)(0*100));
                ProgressBar pb1 = (ProgressBar)v.findViewById(R.id.pb_option2);
                pb1.setProgress((int)(0*100));
                ProgressBar pb2 = (ProgressBar)v.findViewById(R.id.pb_option3);
                pb2.setProgress((int)(0*100));


            }
        };
        lview.setAdapter(adapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!(GlobalValues.getSecurityGroupSettings().CanCreatePoll==true))
                { final Poll p = (Poll) adapter.getItem(position);
                    final String pollid = adapter.getRef(position).getKey();

                    mpollresults.child(adapter.getRef(position).getKey()).child("poll_responses").child(fireauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                // TODO: handle the case where the data already exists
                                {

                                    Toast.makeText(getActivity(), "Poll Response Already Submitted", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {

                                Bundle bundle = new Bundle();
                                bundle.putString("Question", p.question);
                                bundle.putString("Option1", p.option1);
                               // bundle.putInt("Count1", Integer.parseInt(p.getOption1().get("count").toString()));
                                bundle.putString("Option2", p.option2);
                                //bundle.putInt("Count2", Integer.parseInt(p.getOption2().get("count").toString()));
                                bundle.putString("Option3", p.option3);
                                //bundle.putInt("Count3", Integer.parseInt(p.getOption3().get("count").toString()));
                                bundle.putString("PollId",pollid );
                                Intent i = new Intent(getActivity(), PollResultActivity.class);
                                i.putExtras(bundle);

//Fire that second activity
                                startActivity(i);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        return fragPollView;


    }
}
