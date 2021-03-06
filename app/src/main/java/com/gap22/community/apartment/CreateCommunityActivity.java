package com.gap22.community.apartment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gap22.community.apartment.Entities.Community;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateCommunityActivity extends AppCompatActivity {


    EditText name,buildername,seceretary,city,state,address,address2,pincode,taxid;
    Button Create;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireauth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("community");


        mDatabase.child(fireauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    // TODO: handle the case where the data already exists
                    {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateCommunityActivity.this);


                        // set title
                        alertDialogBuilder.setTitle("Community Already Created");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click OK to exit!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity

                                        startActivity(new Intent(CreateCommunityActivity.this, GetCollaborated.class));
                                        finish();

                                    }
                                });


                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                }
                else
                    // TODO: handle the case where the data does not yet exist
                    {
                        setContentView(R.layout.activity_create_community);
                        name = (EditText) findViewById(R.id.Name);
                        buildername = (EditText) findViewById(R.id.builder);
                        seceretary = (EditText) findViewById(R.id.sec);
                        city = (EditText) findViewById(R.id.city);
                        state = (EditText) findViewById(R.id.state);
                        address = (EditText) findViewById(R.id.Address1);
                        address2 = (EditText) findViewById(R.id.Address2);
                        pincode = (EditText) findViewById(R.id.pincode);
                        taxid = (EditText) findViewById(R.id.tax);

                        Create = (Button) findViewById(R.id.create);

                        Create.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              /*  Community c = new Community();
                                c.setAddress1(address.getText().toString());
                                c.setAddress2(address2.getText().toString());
                                c.setBuilder(buildername.getText().toString());
                                c.setCity(city.getText().toString());
                                c.setState(state.getText().toString());
                                System.out.println("pincode" + pincode.getText().toString());
                                int a = Integer.parseInt(pincode.getText().toString());
                                c.setPinCode(a);
                                c.setName(name.getText().toString());
                                c.setSeceretary(seceretary.getText().toString());
                                c.setStatus(1);
                                c.setTaxID(taxid.getText().toString());
                                if (validate(c)) {
                                    create(c);
                                }*/
                            }
                        });

                    }
                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    public boolean validate(Community c)
    {

      /*  if(TextUtils.isEmpty(c.getName()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(c.getBuilder()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter BuilderName",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(c.getSeceretary()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter Seceretary",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(c.getAddress1()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter Address1",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(c.getAddress2()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter Address2",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(c.getCity()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter City",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(c.getState()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter State",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(c.getTaxID()))
        {
            Toast.makeText(CreateCommunityActivity.this,"Please Enter TaxId",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }
    public void create(Community c)
    {
       //Inserting Community

        mDatabase.child(fireauth.getCurrentUser().getUid()).setValue(c);
        Toast.makeText(CreateCommunityActivity.this, "Community Created", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, GetCollaborated.class));
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

            inflater.inflate(R.menu.adminmenu, menu);

        return true;
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: handle the case where the data already exists
        fireauth = null;
        mDatabase = null;
    }
}
