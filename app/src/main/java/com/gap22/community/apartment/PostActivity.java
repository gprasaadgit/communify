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
import android.widget.Toast;

import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PostDao;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PostActivity extends AppCompatActivity {

    private DatabaseReference mposts,mauthor;
    private FirebaseAuth fireauth;
    private EditText Title,Posts;
    private Button create,cancel;
    private StoragePreferences storagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        fireauth = FirebaseAuth.getInstance();
        storagePref = StoragePreferences.getInstance(this);
        mposts = FirebaseDatabase.getInstance().getReference("post");
        mauthor= FirebaseDatabase.getInstance().getReference("author");
        Title = (EditText) findViewById(R.id.et_title);
        Posts = (EditText) findViewById(R.id.et_description);
        create =(Button) findViewById(R.id.btn_submit);
        cancel = (Button) findViewById(R.id.btn_cancel);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                PostDao postDao = new PostDao();
                Date createdDate = new Date();
                KeyGenerator keyGen = new KeyGenerator();
                final String CommunityId = GlobalValues.getCommunityId();
                Post post = new Post(fireauth.getCurrentUser().getUid(), createdDate, Posts.getText().toString(),
                        Title.getText().toString(), Post.Status.Active, Post.CommentStatus.EveryOne, "", null, "", "");
                validate(post);
                postDao.CreatePost(CommunityId, keyGen.GetNewPostKey(), post);


                Toast.makeText(PostActivity.this, "Post Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PostActivity.this, GetCollaborated.class));
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();
            }
        });

    }
    public boolean validate(Post p)
    {

        if(TextUtils.isEmpty(p.title))
        {
            Toast.makeText(PostActivity.this,"Please Enter Title",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(p.content))
        {
            Toast.makeText(PostActivity.this,"Please Enter Content",Toast.LENGTH_SHORT).show();
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
            case ViewResidents:
                startActivity(new Intent(this, CoreOperation.class));
                finish();
                return true;

            case R.id.Signout:
fireauth.signOut();
                startActivity(new Intent(this, MainActivity.class));
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


    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: handle the case where the data already exists
        fireauth = null;
        mposts = null;
        mauthor = null;
    }
}
