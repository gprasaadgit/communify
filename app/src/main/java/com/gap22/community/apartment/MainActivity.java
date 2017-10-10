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

import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.gap22.community.apartment.Entities.Members;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gap22.community.apartment.R.id.et_userName;

public class MainActivity extends AppCompatActivity {

    private Button Login;
    private EditText Email;
    private EditText Pwd;
    private ProgressDialog progress;
    private FirebaseAuth fireauth;

    private TextView newuser;
    private ImageView iv_password_error;
    private ImageView iv_userName_error;
    private DatabaseReference mDatabase,mCommunityMember;

    private StoragePreferences storagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_main);
        storagePref = StoragePreferences.getInstance(this);
        mDatabase = FirebaseDatabase.getInstance().getReference("USER-DIRECTORY");
        //mCommunity = FirebaseDatabase.getInstance().getReference("community");
        String storageUserId = storagePref.getPreference("userId");
      /*  if (storageUserId != "") {
            Intent intent_coreOper = new Intent(getApplicationContext(), CoreOperation.class);
            startActivity(intent_coreOper);
            overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
        }*/
        Login = (Button) findViewById(R.id.btn_sign_in);
        Email = (EditText) findViewById(et_userName);
        Pwd = (EditText) findViewById(R.id.et_password);
        newuser = (TextView) findViewById(R.id.tview_dont_have_account);
        progress = new ProgressDialog(this);
        fireauth = FirebaseAuth.getInstance();



       /* if (fireauth.getCurrentUser() != null) {
            // User is logged in
            Intent intent_coreOper = new Intent(getApplicationContext(), CoreOperation.class);
            startActivity(intent_coreOper);
            overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
        }*/

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(MainActivity.this, SignUp.class);

                finish();
                startActivity(menu);

                return;

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = Email.getText().toString().trim();
                String password = Pwd.getText().toString().trim();
                if (ValidateFormBeforeSubmit() == true) {

                    progress.setMessage("Loggin in Please Wait");
                    progress.show();
                    fireauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progress.dismiss();
                            if (task.isSuccessful()) {

                               storagePref.savePreference("userId", fireauth.getCurrentUser().getEmail());




                                        mDatabase.child(fireauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {

                                            Intent menu = new Intent(MainActivity.this, GetCollaborated.class);


                                            GlobalUser m = dataSnapshot.getValue(GlobalUser.class);
                                            storagePref.savePreference("name",m.first_name);
                                            storagePref.savePreference("email",m.email);
                                            storagePref.savePreference("img",fireauth.getCurrentUser().getUid());
                                            storagePref.savePreference("CommunityID",m.default_community);

                                            mCommunityMember =  FirebaseDatabase.getInstance().getReference(m.default_community).child("Members").child(fireauth.getCurrentUser().getUid());
                                            mCommunityMember.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                    if (dataSnapshot.exists()) {
                                                        Members mem = dataSnapshot.getValue(Members.class);
                                                        if(mem.security_group.equals("USER"))
                                                        {
                                                            storagePref.savePreference("type", "member");
                                                        }

                                                        else
                                                        {
                                                            storagePref.savePreference("type", "admin");

                                                        }
                                                        Intent menu = new Intent(MainActivity.this, GetCollaborated.class);

                                                        finish();
                                                        startActivity(menu);
                                                        overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
                                                        return;

                                                    } else {

                                                        Toast.makeText(MainActivity.this, "USER Waiting For Approval", Toast.LENGTH_SHORT).show();
                                                        return;



                                                    }
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            finish();
                                            startActivity(menu);
                                            overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
                                            return;
                                        }
                                        else
                                        {


                                            Toast.makeText(MainActivity.this, "USER DOES NOT EXIST", Toast.LENGTH_SHORT).show();
                                            return;

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            } else {

                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

                }
            }
        });
    }


    private boolean ValidateFormBeforeSubmit() {
        boolean response = true;
        iv_userName_error = (ImageView) findViewById(R.id.iv_userName_error);
        iv_password_error = (ImageView) findViewById(R.id.iv_password_error);
        System.out.print("ee" + Email.getText().toString());
        if (Email == null || Email.getText().toString().equals("")) {
            iv_userName_error.setVisibility(View.VISIBLE);
            response = false;
        } else {
            iv_userName_error.setVisibility(View.INVISIBLE);
        }

        if (Pwd == null || Pwd.getText().toString().equals("")) {
            iv_password_error.setVisibility(View.VISIBLE);
            response = false;
        } else {
            iv_password_error.setVisibility(View.INVISIBLE);
        }

        return response;
    }
}





