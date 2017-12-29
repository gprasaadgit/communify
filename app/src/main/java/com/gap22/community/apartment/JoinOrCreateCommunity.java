package com.gap22.community.apartment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Dao.CommunityInvitesDao;
import com.gap22.community.apartment.Entities.CommunityInviteCode;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinOrCreateCommunity extends AppCompatActivity {

    private EditText et_invite_code;
    private TextView tview_error_message;
    private String communityId = "";
    private FirebaseAuth fireauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_join_or_create_community);

        et_invite_code = (EditText) findViewById(R.id.et_invite_code);
        tview_error_message = (TextView) findViewById(R.id.tview_error_message);
        fireauth = FirebaseAuth.getInstance();
    }

    public void btn_Join_onClickBtn(View v) {
        Query query = FirebaseDatabase.getInstance().getReference("INVITES").orderByChild("invite_code").equalTo(et_invite_code.getText().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    CommunityInviteCode communityInviteCode = dataSnapshot.getValue(CommunityInviteCode.class);
                    if (communityInviteCode != null) {
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            communityId = post.getKey();
                        }

                        Query globalUserQry = FirebaseDatabase.getInstance().getReference("USER-DIRECTORY").child(fireauth.getCurrentUser().getUid());
                        globalUserQry.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    GlobalUser globalUser = dataSnapshot.getValue(GlobalUser.class);
                                    CommunityInvitesDao communityInvitesDao = new CommunityInvitesDao();
                                    communityInvitesDao.CreateCommunityInvites(communityId, fireauth.getCurrentUser().getUid(), globalUser);
                                    tview_error_message.setText("Your invite is under security checks. Once the community administrator accepts, you will be getting notification.");
                                    tview_error_message.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                } else {
                    tview_error_message.setText("Invalid Invite Code!");
                    tview_error_message.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
