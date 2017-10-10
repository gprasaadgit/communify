package com.gap22.community.apartment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Database.Member;
import com.gap22.community.apartment.Entities.PostResponses;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.gap22.community.apartment.R.id.ViewResidents;

public class PostResponse extends AppCompatActivity {

    private TextView title;
    private EditText et_post_response_message;
    ListView lview;


    private DatabaseReference mDatabase,mpost,mmember;
    private FirebaseAuth fireauth;
    private String pollId;
    private String postQuestion;
    private Button Response;
    private int responsecount;
    private StoragePreferences storagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_response);
        lview = (ListView) findViewById(R.id.list_Post_Reponse);
        //  post =(TextView) findViewById(R.id.Post);
        title =(TextView) findViewById(R.id.tview_title);
        et_post_response_message =(EditText) findViewById(R.id.et_post_response_message);

        fireauth = FirebaseAuth.getInstance();
        Bundle bundle = getIntent().getExtras();
        pollId = bundle.getString("PostId");
        postQuestion = bundle.getString("Post");
        //title.setText( bundle.getString("Title"));
        responsecount = bundle.getInt("ResponseCount");
        title.setText(postQuestion);
        storagePref = StoragePreferences.getInstance(this);
        Response =(Button) findViewById(R.id.btn_send);
        final String storageUserId = storagePref.getPreference("CommunityID");
        mDatabase = FirebaseDatabase.getInstance().getReference(storageUserId).child("Post").child(pollId).child("post_responses");

        mmember = FirebaseDatabase.getInstance().getReference("member");

        mpost = FirebaseDatabase.getInstance().getReference("post").child(storageUserId).child(pollId);
        FirebaseListAdapter adapter = new FirebaseListAdapter(this, PostResponses.class, R.layout.viewpostresponse, mDatabase)
        {

            @Override
            protected void populateView(View v, Object model, int position) {

PostResponses ob =(PostResponses) model;




                final TextView name = (TextView) v.findViewById(R.id.text_User_Display_Name);

                final TextView response = (TextView)v.findViewById(R.id.text_User_Response);
                response.setText(ob.content);
              //  name.setText();





            }
        };

        lview.setAdapter(adapter);


        Response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                final HashMap Output = new HashMap();
                final String srt = et_post_response_message .getText().toString();

                mmember.child(fireauth.getCurrentUser().getUid()).addListenerForSingleValueEvent( new ValueEventListener()
                {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Member m = dataSnapshot.getValue(Member.class);

                            Output.put("text",srt);
                            Output.put("name",m.getFirstname());
                        }

                        else
                        {
                            Output.put("text",srt);
                            Output.put("name","Admin");
                        }

                        mDatabase.child(fireauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {

                                }
                                else
                                {
                                    responsecount = responsecount +1;
                                }

                                mDatabase.child(fireauth.getCurrentUser().getUid()).setValue(Output);

                                mpost.child("responses").setValue(responsecount);
                                Toast.makeText(PostResponse.this,"Response Submitted Succesfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PostResponse.this, CoreOperation.class));
                                finish();


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });








                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        if (fireauth.getCurrentUser().getEmail().equals("testadmin@gmail.com")) {
            inflater.inflate(R.menu.adminmenu, menu);
        } else {
            inflater.inflate(R.menu.menu, menu);
        }
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection

        switch (item.getItemId()) {
            case R.id.Polls:
                startActivity(new Intent(this, CoreOperation.class));
                finish();
                return true;
            case R.id.Community:
                startActivity(new Intent(this, ViewCommunityUser.class));
                finish();
                return true;
            case R.id.Posts:
                startActivity(new Intent(this, CoreOperation.class));
                finish();
                return true;
            case R.id.CreateCommunity:
                startActivity(new Intent(this, CreateCommunityActivity.class));
                finish();
                return true;
            case R.id.ViewCommunity:
                startActivity(new Intent(this, ViewCommunity.class));
                finish();
                return true;
            case R.id.CreatePosts:
                startActivity(new Intent(this, PostActivity.class));
                finish();
                return true;
            case R.id.CreatePolls:
                startActivity(new Intent(this, PollActivity.class));
                finish();
                return true;
            case R.id.Signout:
                fireauth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            case ViewResidents:
                startActivity(new Intent(this, CoreOperation.class));
                finish();
                return true;
            case R.id.Residents:
                Intent intent = new AppInviteInvitation.IntentBuilder("Communify App")
                        .setMessage("please install the app")
                        .setDeepLink(Uri.parse("https://play.google.com/store/apps/details?id=com.vazhagavalamudhan.vethathiri&hl=en"))
                        //.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))

                        .setEmailSubject("App Invite")
                        .setEmailHtmlContent("<html><body>https://play.google.com/store/apps/details?id=com.vazhagavalamudhan.vethathiri&hl=en<br>Install</body></html>")
                        .build();
                startActivityForResult(intent, 59);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}