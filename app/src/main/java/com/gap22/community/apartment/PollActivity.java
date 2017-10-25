package com.gap22.community.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PollsDao;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.Poll;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class PollActivity extends AppCompatActivity {
    private EditText Question;
    private Button create,cancel;
    private DatabaseReference mDatabase;
    private FirebaseAuth fireauth;
    private RadioGroup Options;
    private RadioButton option1,option2,option3;
    int selectedid = 0;
    String text = "";
    private StoragePreferences storagePref;
    KeyGenerator keyGen = new KeyGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        storagePref = StoragePreferences.getInstance(this);
         Options = (RadioGroup)findViewById(R.id.radioResult);
       option1 = (RadioButton) findViewById(R.id.radioOption1);
         option2 = (RadioButton) findViewById(R.id.radioOption2);
       option3 = (RadioButton) findViewById(R.id.radioOption3);
       Question=(EditText)findViewById(R.id.edtInput);
        create =(Button)findViewById(R.id.btn_submit);
        cancel = (Button) findViewById(R.id.btn_cancel);
         selectedid = Options.getCheckedRadioButtonId();
         text = "";




        /*Option1=(EditText)findViewById(R.id.option1);
        Option2=(EditText)findViewById(R.id.option2);
        Option3=(EditText)findViewById(R.id.option3);*/
        //create =(Button)findViewById(R.id.create);*/

        fireauth = FirebaseAuth.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedid==option1.getId())
                {
                    text = option1.getText().toString();


                }
                else if(selectedid==option2.getId())
                {
                    text = option2.getText().toString();
                }
                else
                {
                    text = option3.getText().toString();
                }
               /* Poll p = new Poll();

                if(selectedid==option1.getId())
                {
                     text = option1.getText().toString();


                }
                else if(selectedid==option2.getId())
                {
                    text = option2.getText().toString();
                }
                else
                {
                    text = option3.getText().toString();
                }
             //   p.setOption1(Option1.getText().toString());


p.setOption1(option);
                p.setOption2(option2);
                p.setOption3(option3);
                p.setQuestion(Question.getText().toString());
                p.setStatus("Active");
                p.setDate(System.currentTimeMillis());
                if(validate(p)) {
                    createPoll(p);
                }
*/
                String communityid = GlobalValues.getCommunityId();


                PollsDao pollsDao = new PollsDao();
                Date createdDate = new Date();
                Poll poll = new Poll(Question.getText().toString(), Poll.Status.Active
                        , text.split("/")[0], text.split("/")[1], text.split("/")[2], "YNC", fireauth.getCurrentUser().getUid(), createdDate, null, null);
validate(poll);
                pollsDao.CreatePoll(communityid, keyGen.GetNewPollKey(), poll);
                Toast.makeText(PollActivity.this, "Poll Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PollActivity.this, GetCollaborated.class));
                finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PollActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public boolean validate(Poll p)
    {

        if(TextUtils.isEmpty(p.question))
        {
            Toast.makeText(PollActivity.this,"Please Enter Question",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedid == 0)
        {
            Toast.makeText(PollActivity.this,"Please Choose Options",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

            inflater.inflate(R.menu.adminmenu, menu);

        return true;
    }
   /* public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection

       /* switch (item.getItemId()) {
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: handle the case where the data already exists

        mDatabase = null;
    }
}
