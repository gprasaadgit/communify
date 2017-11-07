package com.gap22.community.apartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.IPAddress;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PollsDao;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class PollResponse extends Fragment {

    private TextView Question;
    private RadioButton Option1,Option2,Option3;
    private Button Submit, btn_cancel;
    private RadioGroup result;
    private String option1,option2,option3;
    private String resultvalue;
    private String pollId;
    private DatabaseReference mpolls,mpollresults;
    private FirebaseAuth fireauth;
    int finalcount,option1count,option2count,option3count;
    private StoragePreferences storagePref;
    public PollResponse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View fragpollresponse = inflater.inflate(R.layout.fragment_poll_response, container, false);

        fireauth = FirebaseAuth.getInstance();

        //mpollresults= FirebaseDatabase.getInstance().getReference("pollResults");
        Question = (TextView)fragpollresponse.findViewById(R.id.tview_question);
        Option1 =(RadioButton)fragpollresponse.findViewById(R.id.radioOption1);
        Option2 =(RadioButton)fragpollresponse.findViewById(R.id.radioOption2);
        Option3 =(RadioButton)fragpollresponse.findViewById(R.id.radioOption3);
        result =(RadioGroup) fragpollresponse.findViewById(R.id.radioResult);
        Submit = (Button) fragpollresponse.findViewById(R.id.btn_submit);
        btn_cancel = (Button)fragpollresponse.findViewById(R.id.btn_cancel);
        Bundle bundle = this.getArguments();
        option1 = bundle.getString("Option1");
        option2 = bundle.getString("Option2");
        option3 = bundle.getString("Option3");


        pollId = bundle.getString("PollId");
        Question.setText(bundle.getString("Question"));
        Option1.setText(option1);

        Option2.setText(option2);

        Option3.setText(option3);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedId = result.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                if(Option1== (RadioButton) fragpollresponse.findViewById(selectedId))
                {
                    resultvalue =Option1.getText().toString();
                    finalcount = 1+option1count;
                }

                else if(Option2== (RadioButton) fragpollresponse.findViewById(selectedId))
                {
                    resultvalue =Option2.getText().toString();
                }
                else
                {
                    resultvalue =Option3.getText().toString();
                }
                String storageUserId = GlobalValues.getCommunityId();

                // mpollresults.child(storageUserId).child(pollId).child(fireauth.getCurrentUser().getUid()).setValue(resultvalue);
                //mpolls.child(storageUserId).child(pollId).child(resultvalue).child("count").setValue(finalcount);
                PollsDao pollsDao = new PollsDao();
                Date createdDate = new Date();
                com.gap22.community.apartment.Entities.PollResponse pollResponse = new com.gap22.community.apartment.Entities.PollResponse(IPAddress.getMACAddress("wlan0"), IPAddress.getMACAddress("eth0"), resultvalue, createdDate, null,null);

                pollsDao.CreatePollResponse(storageUserId,pollId,GlobalValues.getCurrentUserUuid(), pollResponse);
                Toast.makeText(getActivity(), "Poll Response Submitted", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(PollResultActivity.this, GetCollaborated.class));
                // finish();

            }
        });
btn_cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Fragment Polls = new PollsFragment();
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, Polls);
        ft.commit();


    }
});
        return fragpollresponse;
    }
}
