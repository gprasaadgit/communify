package com.gap22.community.apartment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gap22.community.apartment.Common.CommonFunctions;
import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.gap22.community.apartment.Entities.Members;
import com.gap22.community.apartment.Entities.SecurityGroupSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et_userName;
    private EditText et_password;
    private ProgressDialog progressModal;
    private FirebaseAuth fireauth;

    private TextView tview_dont_have_account;
    private ImageView iv_password_error;
    private ImageView iv_userName_error;
    private DatabaseReference dRefMembers, dRefCommunity, dRefSecutityRights;

    private StoragePreferences storagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_main);
        storagePref = StoragePreferences.getInstance(this);
        progressModal = new ProgressDialog(this);
        progressModal.setMessage("Loggin in Please Wait");
        fireauth = FirebaseAuth.getInstance();

        et_userName = (EditText) findViewById(R.id.et_userName);
        et_password = (EditText) findViewById(R.id.et_password);
        tview_dont_have_account = (TextView) findViewById(R.id.tview_dont_have_account);

        String storageUserId = storagePref.getPreference("UserId");
        if (storageUserId != "") {
            LoadWithStoredPreference();
        }
    }

    public void LoadWithStoredPreference() {
        progressModal.show();
        GetGlobalValues();
        fireauth.signInWithEmailAndPassword(GlobalValues.getCurrentUserEmail(), GlobalValues.getCurrentUserPassword()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dRefMembers = FirebaseDatabase.getInstance().getReference(GlobalValues.getCommunityId()).child("Members");
                    dRefMembers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Members> membersesCol = new ArrayList<Members>();
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Members members = postSnapshot.getValue(Members.class);
                                    membersesCol.add(members);
                                }
                                Intent getCollaborated = new Intent(getApplicationContext(), GetCollaborated.class);
                                startActivity(getCollaborated);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
                                finish();
                                progressModal.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }

    public void btn_Dont_Have_Account_onClickBtn(View v) {
        Intent menu = new Intent(MainActivity.this, SignUp.class);
        finish();
        startActivity(menu);
        return;
    }

    public void GetGlobalValues() {
        GlobalValues.setCurrentUserEmail(storagePref.getPreference("EmailId"));
        GlobalValues.setCurrentUserPassword(storagePref.getPreference("SafePassword"));
        GlobalValues.setCurrentUserName(storagePref.getPreference("FullName"));
        GlobalValues.setCurrentUserUuid(fireauth.getCurrentUser().getUid());
        GlobalValues.setCommunityId(storagePref.getPreference("CommunityID"));
        GlobalValues.setSecurityGroupSettings(CommonFunctions.GetSavedObjectFromPreference(MainActivity.this, "securityGroupSettings", "secGroup", SecurityGroupSettings.class));
    }

    public void btn_SignIn_onClickBtn(View v) {
        final String email = et_userName.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if (ValidateFormBeforeSubmit() == true) {
            progressModal.show();

            fireauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        storagePref.savePreference("UserId", email);

                        dRefCommunity = FirebaseDatabase.getInstance().getReference("USER-DIRECTORY").child(fireauth.getCurrentUser().getUid());
                        dRefCommunity.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    GlobalUser globalUser = dataSnapshot.getValue(GlobalUser.class);
                                    overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);

                                    if (!globalUser.default_community.equals("")) {
                                        GlobalValues.setCurrentUserEmail(globalUser.email);
                                        storagePref.savePreference("EmailId", globalUser.email);
                                        storagePref.savePreference("SafePassword", password);
                                        GlobalValues.setCurrentUserName(globalUser.title + ". " + globalUser.first_name + " " + globalUser.last_name);
                                        storagePref.savePreference("FullName", globalUser.title + ". " + globalUser.first_name + " " + globalUser.last_name);
                                        GlobalValues.setCurrentUserUuid(fireauth.getCurrentUser().getUid());
                                        storagePref.savePreference("Image", fireauth.getCurrentUser().getUid());
                                        GlobalValues.setCommunityId(globalUser.default_community);
                                        storagePref.savePreference("CommunityID", globalUser.default_community);

                                        dRefMembers = FirebaseDatabase.getInstance().getReference(GlobalValues.getCommunityId()).child("Members").child(fireauth.getCurrentUser().getUid());
                                        dRefMembers.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    Members members = dataSnapshot.getValue(Members.class);

                                                    dRefSecutityRights = FirebaseDatabase.getInstance().getReference(GlobalValues.getCommunityId()).child("Security").child(members.security_group);
                                                    dRefSecutityRights.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            if (dataSnapshot.exists()) {
                                                                SecurityGroupSettings securityGroupSettings = dataSnapshot.getValue(SecurityGroupSettings.class);
                                                                GlobalValues.setSecurityGroupSettings(securityGroupSettings);
                                                                CommonFunctions.SaveObjectToSharedPreference(MainActivity.this, "securityGroupSettings", "secGroup", securityGroupSettings);
                                                                Intent getCollaborated = new Intent(MainActivity.this, GetCollaborated.class);
                                                                startActivity(getCollaborated);
                                                                progressModal.dismiss();
                                                                finish();
                                                                return;
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                                } else {
                                                    return;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        Intent joinOrCreateCommunity = new Intent(MainActivity.this, JoinOrCreateCommunity.class);
                                        startActivity(joinOrCreateCommunity);
                                        progressModal.dismiss();
                                        finish();
                                        return;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                    } else

                    {
                        Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        }

    }

    private boolean ValidateFormBeforeSubmit() {
        boolean response = true;
        iv_userName_error = (ImageView) findViewById(R.id.iv_userName_error);
        iv_password_error = (ImageView) findViewById(R.id.iv_password_error);
        if (et_userName == null || et_userName.getText().toString().equals("")) {
            iv_userName_error.setVisibility(View.VISIBLE);
            response = false;
        } else {
            iv_userName_error.setVisibility(View.INVISIBLE);
        }

        if (et_password == null || et_password.getText().toString().equals("")) {
            iv_password_error.setVisibility(View.VISIBLE);
            response = false;
        } else {
            iv_password_error.setVisibility(View.INVISIBLE);
        }

        return response;
    }
}





