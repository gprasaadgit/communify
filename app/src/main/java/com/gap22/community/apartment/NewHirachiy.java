package com.gap22.community.apartment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

import android.view.View;
import android.widget.Toast;

import com.gap22.community.apartment.Common.CommunifyCrypto;
import com.gap22.community.apartment.Common.IPAddress;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Dao.*;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.*;
import com.gap22.community.apartment.Entities.PostResponse;

import javax.crypto.SecretKey;

public class NewHirachiy extends AppCompatActivity {

    //private Button btn_community;
    private KeyGenerator keyGen = new KeyGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //btn_community = (Button) findViewById(R.id.btn_community);

        setContentView(R.layout.activity_new_hirachiy);
    }

    public void btn_community_onClickBtn(View v) {
        CommunityDao communityDao = new CommunityDao();
        Date createdDate = new Date();

        Community community = new Community("La Parsley", "No 17, Guruswamy Main Road", "Near Nolambur Bus Stand",
                "Nolambur", "Chennai", "Tamil Nadu", 600095, Community.Status.Active, "TXN-ID-54885NN776", "Jain Housing And Constructions Ltd",
                "Lorum Ipsum", "54588", "OiUxTtK6rdMVnDYJpDAHXSPYpIx1", createdDate, null, null);
        Members members = new Members("narayanan.coc@gmail.com", createdDate, null, Members.Status.Active, "Narayanan", "Dayalan",
                4, 1, 0, "Mr", "45", "9840399445", "", "", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tristique varius risus eget gravida. Duis ac eros quis sapien accumsan iaculis.");
        ActionResponse response = communityDao.CreateCommunity(keyGen.GetNewCommunityKey(), community, keyGen.GetNewMemberKey(), members);
        if (!response.success) {
            Toast.makeText(this, response.error_message, Toast.LENGTH_LONG).show();
        }
    }

    public void btn_Invites_onClickBtn(View v) throws CommunifyCrypto.CryptoException {
        AppConfigDao appConfigDao = new AppConfigDao();
        appConfigDao.SetDefaultAppConfig();
    }

    public void btn_PostResponse_onClickBtn(View v) {
        PostDao postDao = new PostDao();
        Date createdDate = new Date();
        PostResponse postResponse = new PostResponse("KgpySaT5EuZjvmuK1aO1KrEYHj52", IPAddress.getMACAddress("wlan0"), IPAddress.getMACAddress("eth0"), createdDate, "Nam gravida scelerisque risus. Cras in quam vitae leo cursus ornare. Nullam nec diam eros. Phasellus sed justo nisl. Praesent pharetra massa vel mauris efficitur malesuada.",
                PostResponse.Status.Approved, "", "");
        postDao.CreatePostResponse("CMTY20171011210657333", "POST20171011211110040", keyGen.GetNewPostCommentKey(), postResponse);
    }

    public void btn_PollResponse_onClickBtn(View v) {

    }

    public void btn_Questions_onClickBtn(View v) throws CommunifyCrypto.CryptoException {
        CommunifyCrypto communifyCrypto = new CommunifyCrypto();
        AppConfigDao appConfigDao = new AppConfigDao();
        AppConfig appConfig;

        SecretKey secretKey = communifyCrypto.getSecretKey(GlobalValues.getEncriptionPass(), GlobalValues.getEncriptionSalt());

        appConfig = appConfigDao.GetAppConfig();
        //Toast.makeText(this, appConfig.email_user_id, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, communifyCrypto.decrypt(secretKey, appConfig.email_user_id) , Toast.LENGTH_LONG).show();
    }

    public void btn_Rights_onClickBtn(View v) {

    }

    public void btn_Events_onClickBtn(View v) {

    }

    public void btn_Members_onClickBtn(View v) {

    }

    public void btn_Post_onClickBtn(View v) {
        PostDao postDao = new PostDao();
        Date createdDate = new Date();
        Post post = new Post("KgpySaT5EuZjvmuK1aO1KrEYHj52", createdDate, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tristique varius risus eget gravida. Duis ac eros quis sapien accumsan iaculis. Sed sapien justo, consectetur ut rhoncus et, interdum nec dolor. Nullam sem risus, pellentesque in neque a, venenatis tempor neque. Donec gravida lorem vel mauris consequat, vel porta dui pretium. Sed odio nulla, pretium sed iaculis in, scelerisque sit amet massa. Integer at mi eget lorem lacinia venenatis. Vivamus a dui eget lacus pellentesque porttitor. Ut maximus lacus eu eleifend suscipit. Nullam magna purus, tincidunt sed ante eget, suscipit blandit lectus. Duis posuere massa at tellus rutrum, ac varius ipsum venenatis. Donec sed euismod mi. Curabitur pharetra laoreet bibendum. Morbi lacinia sapien id odio commodo volutpat. ",
                "A Test Post Title", Post.Status.Active, Post.CommentStatus.EveryOne, "", null, "", "");
        postDao.CreatePost("CMTY20171011210657333", keyGen.GetNewPostKey(), post);
    }

    public void btn_UpdatePost_onClickBtn(View v) {
        PostDao postDao = new PostDao();
        Date createdDate = new Date();
        Post post = new Post("KgpySaT5EuZjvmuK1aO1KrEYHj52", createdDate, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tristique varius risus eget gravida. Duis ac eros quis sapien accumsan iaculis. Sed sapien justo, consectetur ut rhoncus et, interdum nec dolor. Nullam sem risus, pellentesque in neque a, venenatis tempor neque. Donec gravida lorem vel mauris consequat, vel porta dui pretium. Sed odio nulla, pretium sed iaculis in, scelerisque sit amet massa. Integer at mi eget lorem lacinia venenatis. Vivamus a dui eget lacus pellentesque porttitor. Ut maximus lacus eu eleifend suscipit. Nullam magna purus, tincidunt sed ante eget, suscipit blandit lectus. Duis posuere massa at tellus rutrum, ac varius ipsum venenatis. Donec sed euismod mi. Curabitur pharetra laoreet bibendum. Morbi lacinia sapien id odio commodo volutpat. ",
                "A Test Post Title", Post.Status.Active, Post.CommentStatus.EveryOne, "", null, "", "");
        postDao.UpdatePost("CMTY20171011210657333", "POST-20171003224413208", post);
    }

    public void btn_Poll_onClickBtn(View v) {

    }

    public void btn_Notifications_onClickBtn(View v) {

    }

    public void btn_Users_onClickBtn(View v) {
        GlobalUserDao globalUserDao = new GlobalUserDao();
        Date createdDate = new Date();
        GlobalUser globalUser = new GlobalUser("Mr", "Narayanan", "Dayalan", "narayanan.vsn@gmail.com", "gavs123", "+919840399445", createdDate, "CMTY-20171002214959926");
        ActionResponse response = globalUserDao.CreateUser(globalUser);
        if (response.success == false) {
            Toast.makeText(this, response.error_message, Toast.LENGTH_LONG).show();
        }
    }
}