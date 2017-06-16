package com.gap22.community.apartment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import static com.gap22.community.apartment.R.id.img_user_image;

public class OptionalSignup extends AppCompatActivity {

    private EditText occupation, noofAdults, noofChild, noofInfants, unit;
    Button Create;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    private ProgressDialog progress;
    private Button signup;
    private Spinner residence;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_setting);
        img =(ImageView) findViewById(img_user_image);
        populateResidence();
        progress = new ProgressDialog(this);
        fireauth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("member");
        occupation = (EditText) findViewById(R.id.et_occupation);
        unit = (EditText) findViewById(R.id.et_unit);
        noofAdults = (EditText) findViewById(R.id.et_adults);
        noofChild = (EditText) findViewById(R.id.et_child);
        noofInfants = (EditText) findViewById(R.id.et_infants);
        residence =(Spinner) findViewById(R.id.spin_residence);
        signup =(Button)findViewById(R.id.btn_sign_up);
        Bundle bundle = getIntent().getExtras();
        final String userid  = bundle.getString("userid");
        Uri myUri = Uri.parse(bundle.getString("imageuri"));
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
            img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(userid).child("unit").setValue(unit.getText().toString());
                mDatabase.child(userid).child("residencestatus").setValue(residence.getSelectedItem().toString());
                mDatabase.child(userid).child("occupation").setValue(occupation.getText().toString());
             if(!TextUtils.isEmpty(noofAdults.getText().toString()))
             {
                    mDatabase.child(userid).child("numAdults").setValue(Integer.parseInt(noofAdults.getText().toString()));
                }
            if(!TextUtils.isEmpty(noofChild.getText().toString())) {
                    mDatabase.child(userid).child("numChildren").setValue(Integer.parseInt(noofChild.getText().toString()));
                }
            if(!TextUtils.isEmpty(noofInfants.getText().toString())){
                    mDatabase.child(userid).child("numInfants").setValue(Integer.parseInt(noofInfants.getText().toString()));
                }
                Intent menu = new Intent(OptionalSignup.this, MainActivity.class);

                finish();
                startActivity(menu);
                overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
            }
        });
    }

    public void populateResidence() {
        Spinner spinner = (Spinner) findViewById(R.id.spin_residence);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
