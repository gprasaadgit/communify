package com.gap22.community.apartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PollsDao;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.Poll;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class CreatePoll extends Fragment {

    private EditText Question;
    private Button create,cancel;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    private RadioGroup Options;
    private RadioButton option1,option2,option3;
    int selectedid = 0;
    String text = "";
    private StoragePreferences storagePref;
    KeyGenerator keyGen = new KeyGenerator();
    public CreatePoll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View createpoll = inflater.inflate(R.layout.fragment_poll_create, container, false);
        storagePref = StoragePreferences.getInstance(getActivity());
        Options = (RadioGroup)createpoll.findViewById(R.id.radioResult);
        option1 = (RadioButton) createpoll.findViewById(R.id.radioOption1);
        option2 = (RadioButton) createpoll.findViewById(R.id.radioOption2);
        option3 = (RadioButton) createpoll.findViewById(R.id.radioOption3);
        Question=(EditText)createpoll.findViewById(R.id.edtInput);
        create =(Button)createpoll.findViewById(R.id.btn_submit);
        cancel = (Button) createpoll.findViewById(R.id.btn_cancel);
        selectedid = Options.getCheckedRadioButtonId();
        text = "";





        fireauth = FirebaseAuth.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedid==option1.getId())
                {
                    text = option1.getText().toString();


                }
                else if(selectedid==option2.getId())
                {
                    text = option2.getText().toString();
                }
                else
                {
                    text = option3.getText().toString();
                }

                String communityid = GlobalValues.getCommunityId();


                PollsDao pollsDao = new PollsDao();
                Date createdDate = new Date();
                Poll poll = new Poll(Question.getText().toString(), Poll.Status.Active
                        , text.split("/")[0], text.split("/")[1], text.split("/")[2], "YNC", fireauth.getCurrentUser().getUid(), createdDate, null, null);
                validate(poll);
                pollsDao.CreatePoll(communityid, keyGen.GetNewPollKey(), poll);
                Toast.makeText(getActivity(), "Poll Created", Toast.LENGTH_SHORT).show();
                Fragment Polls = new PollsFragment();



                FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, Polls);
                ft.commit();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Polls = new PollsFragment();



                FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, Polls);
                ft.commit();
            }
        });



        return createpoll;
    }

    public boolean validate(Poll p)
    {

        if(TextUtils.isEmpty(p.question))
        {
            Toast.makeText(getActivity(),"Please Enter Question",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedid == 0)
        {
            Toast.makeText(getActivity(),"Please Choose Options",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}