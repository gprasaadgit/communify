package com.gap22.community.apartment.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Entities.Members;
import com.gap22.community.apartment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.gap22.community.apartment.R.id.img_user_image;


public class MyProfile extends Fragment {
    private int mIndex;
    private DatabaseReference myprofile;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragPollView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        String CommunityId = GlobalValues.getCommunityId();
        myprofile = FirebaseDatabase.getInstance().getReference(CommunityId.trim()).child("Members").child(GlobalValues.getCurrentUserUuid());
        myprofile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Members mem = dataSnapshot.getValue(Members.class);
                    ImageView userimg = (ImageView) fragPollView.findViewById(img_user_image);
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com/"+GlobalValues.getCurrentUserUuid()+".jpg");
                    Glide.with(MyProfile.this )
                            .using(new FirebaseImageLoader())
                            .load(storageRef)
                            .error(R.drawable.admin)
                            .into(userimg);

                    TextView fullname = (TextView)fragPollView.findViewById(R.id.textView1);
                    fullname.setText(GlobalValues.getCurrentUserName());

                    EditText   tview_adults_value = (EditText) fragPollView.findViewById(R.id.tview_adults_value);
                    tview_adults_value.setText(mem.adults);
                    EditText tview_child_value = (EditText) fragPollView.findViewById(R.id.tview_child_value);
                    tview_child_value.setText(mem.children);
                    EditText tview_infants_value =(EditText) fragPollView.findViewById(R.id.tview_infants_value);
                    tview_infants_value.setText(mem.infants);
                    EditText CommunityName =(EditText) fragPollView.findViewById(R.id.tview_occupation_value);
                    CommunityName.setText(GlobalValues.getCommunityId());

                    EditText tview_unit_value =(EditText) fragPollView.findViewById(R.id.tview_unit_value);
                    tview_unit_value.setText(mem.unit);
                    EditText tview_email_value =(EditText) fragPollView.findViewById(R.id.tview_email_value);
                    tview_email_value.setText(mem.email);
                    EditText tview_phone_value =(EditText) fragPollView.findViewById(R.id.tview_phone_value);
                    tview_phone_value.setText(mem.phone);
                    EditText et_last_name_value =(EditText) fragPollView.findViewById(R.id.et_last_name_value);
                    et_last_name_value.setText(mem.last_name);
                    EditText et_first_name_value =(EditText) fragPollView.findViewById(R.id.et_first_name_value);
                    et_first_name_value.setText(mem.first_name);












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
        getActivity().setTitle("My Profile");
    }
}
