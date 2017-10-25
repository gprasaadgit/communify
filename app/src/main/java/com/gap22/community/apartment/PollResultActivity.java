package com.gap22.community.apartment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.IPAddress;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PollsDao;
import com.gap22.community.apartment.Entities.PollResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class PollResultActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_result);
        fireauth = FirebaseAuth.getInstance();

        //mpollresults= FirebaseDatabase.getInstance().getReference("pollResults");
        Question = (TextView) findViewById(R.id.tview_question);
        Option1 =(RadioButton)findViewById(R.id.radioOption1);
        Option2 =(RadioButton)findViewById(R.id.radioOption2);
        Option3 =(RadioButton)findViewById(R.id.radioOption3);
        result =(RadioGroup) findViewById(R.id.radioResult);
        Submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        Bundle bundle = getIntent().getExtras();
        storagePref = StoragePreferences.getInstance(this);
        option1 = bundle.getString("Option1");
        option2 = bundle.getString("Option2");
        option3 = bundle.getString("Option3");


        pollId = bundle.getString("PollId");
        Question.setText(bundle.getString("Question"));
        Option1.setText(option1);
        Option1.setTextColor(Color.WHITE);
        Option2.setText(option2);
        Option2.setTextColor(Color.WHITE);
        Option3.setText(option3);
        Option3.setTextColor(Color.WHITE);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedId = result.getCheckedRadioButtonId();

                // find the radiobutton by returned id
               if(Option1== (RadioButton) findViewById(selectedId))
               {
                   resultvalue =Option1.getText().toString();
                   finalcount = 1+option1count;
               }

               else if(Option2== (RadioButton) findViewById(selectedId))
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
                PollResponse pollResponse = new PollResponse(IPAddress.getMACAddress("wlan0"), IPAddress.getMACAddress("eth0"), resultvalue, createdDate, null,null);

                pollsDao.CreatePollResponse(storageUserId,pollId,GlobalValues.getCurrentUserUuid(), pollResponse);
                Toast.makeText(PollResultActivity.this, "Poll Response Submitted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PollResultActivity.this, GetCollaborated.class));
                finish();

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


   /* public boolean onOptionsItemSelected(MenuItem item) {

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
                        //.setDeepLink(Uri.parse("https://play.google.com/store/apps/details?id=com.vazhagavalamudhan.vethathiri&hl=en"))
                        //.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))

                        .setEmailSubject("App Invite")
                        .setEmailHtmlContent("<html><body>https://play.google.com/store/apps/details?id=com.vazhagavalamudhan.vethathiri&hl=en<br>Install</body></html>")
                        .build();
                startActivityForResult(intent, 59);

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
