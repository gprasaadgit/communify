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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
    private DatabaseReference mDatabase;
    private StoragePreferences storagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_main);
        storagePref = StoragePreferences.getInstance(this);
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
                                storagePref.savePreference("userId", email);
                                Intent menu = new Intent(MainActivity.this, CoreOperation.class);

                                finish();
                                startActivity(menu);
                                overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
                                return;


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





