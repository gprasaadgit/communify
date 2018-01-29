package com.gap22.community.apartment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.gap22.community.apartment.Common.DeviceInfo;
import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.SmsIO;
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

    private EditText et_phoneNumber;
    private ProgressDialog progressModal;
    private FirebaseAuth fireauth;

    private TextView tview_dont_have_account;
    private ImageView iv_phoneNumber_error;
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

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        tview_dont_have_account = (TextView) findViewById(R.id.tview_dont_have_account);
        iv_phoneNumber_error = (ImageView) findViewById(R.id.iv_phoneNumber_error);

        //SmsIO.SendSMS("", "");
        String storageUserId = storagePref.getPreference("UserId");
        if (storageUserId != "") {
            et_phoneNumber.setText(DeviceInfo.GetPhoneNumber(this, this));
            LoadWithStoredPreference();
        } else {
            if (!DeviceInfo.CheckPermission(GlobalValues.WantPermission, this)) {
                DeviceInfo.RequestPermission(GlobalValues.WantPermission, this);
            } else {
                et_phoneNumber.setText(DeviceInfo.GetPhoneNumber(this, this));
            }
        }
    }

    public void LoadWithStoredPreference() {
        progressModal.show();
        GetGlobalValues();
        dRefMembers = FirebaseDatabase.getInstance().getReference(GlobalValues.getCommunityId()).child("Members");
        dRefMembers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Members members = postSnapshot.getValue(Members.class);
                        members.id = postSnapshot.getKey();
                        GlobalValues.addCommunityMembers(members);
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
                progressModal.dismiss();
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
        GlobalValues.setCurrentUserUuid(et_phoneNumber.getText().toString().trim());
        GlobalValues.setCommunityId(storagePref.getPreference("CommunityID"));
        GlobalValues.setSecurityGroupSettings(CommonFunctions.GetSavedObjectFromPreference(MainActivity.this, "securityGroupSettings", "secGroup", SecurityGroupSettings.class));
    }

    public void btn_SignIn_onClickBtn(View v) {
        final String phoneNumber = et_phoneNumber.getText().toString().trim();

        if (ValidateFormBeforeSubmit() == true) {
            progressModal.show();

            dRefCommunity = FirebaseDatabase.getInstance().getReference("USER-DIRECTORY").child(phoneNumber);
            dRefCommunity.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        GlobalUser globalUser = dataSnapshot.getValue(GlobalUser.class);
                        overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);

                        if (!globalUser.default_community.equals("")) {
                            storagePref.savePreference("UserId", phoneNumber);
                            GlobalValues.setCurrentUserEmail(globalUser.email);
                            storagePref.savePreference("EmailId", globalUser.email);
                            //storagePref.savePreference("SafePassword", password);
                            GlobalValues.setCurrentUserName(globalUser.title + ". " + globalUser.first_name + " " + globalUser.last_name);
                            storagePref.savePreference("FullName", globalUser.title + ". " + globalUser.first_name + " " + globalUser.last_name);
                            GlobalValues.setCurrentUserUuid(phoneNumber);
                            storagePref.savePreference("Image", phoneNumber);
                            GlobalValues.setCommunityId(globalUser.default_community);
                            storagePref.savePreference("CommunityID", globalUser.default_community);

                            dRefMembers = FirebaseDatabase.getInstance().getReference(GlobalValues.getCommunityId()).child("Members").child(phoneNumber);
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
                            storagePref.savePreference("OtpPhoneNumber", phoneNumber);
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
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GlobalValues.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //et_phone.setText(DeviceInfo.GetPhoneNumber(this, this));
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean ValidateFormBeforeSubmit() {
        boolean response = true;
        if (et_phoneNumber == null || et_phoneNumber.getText().toString().equals("")) {
            response = false;
        } else {
            iv_phoneNumber_error.setVisibility(View.INVISIBLE);
        }

        return response;
    }
}





