package com.gap22.community.apartment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.View;
import android.widget.Toast;

import com.gap22.community.apartment.Common.CommunifyCrypto;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.IPAddress;
import com.gap22.community.apartment.Dao.AppConfigDao;
import com.gap22.community.apartment.Dao.CommunityDao;
import com.gap22.community.apartment.Dao.CommunityInviteCodeDao;
import com.gap22.community.apartment.Dao.EventsDao;
import com.gap22.community.apartment.Dao.GlobalUserDao;
import com.gap22.community.apartment.Dao.MembersDao;
import com.gap22.community.apartment.Dao.PollsDao;
import com.gap22.community.apartment.Dao.PostDao;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.ActionResponse;
import com.gap22.community.apartment.Entities.AppConfig;
import com.gap22.community.apartment.Entities.Community;
import com.gap22.community.apartment.Entities.GlobalUser;
import com.gap22.community.apartment.Entities.Post;
import com.gap22.community.apartment.Entities.*;
import com.gap22.community.apartment.Entities.PostResponses;

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
      /*  Members members = new Members("narayanan.coc@gmail.com", createdDate, null, Members.Status.Active, "Narayanan", "Dayalan",
                4, 1, 0, "Mr", "45", "9840399445", "", "", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tristique varius risus eget gravida. Duis ac eros quis sapien accumsan iaculis.");
        ActionResponse response = communityDao.CreateCommunity(keyGen.GetNewCommunityKey(), community, keyGen.GetNewMemberKey(), members);
        if (!response.success) {
            Toast.makeText(this, response.error_message, Toast.LENGTH_LONG).show();
        }*/

    }

    public void btn_Invites_onClickBtn(View v) throws CommunifyCrypto.CryptoException {
        AppConfigDao appConfigDao = new AppConfigDao();
        appConfigDao.SetDefaultAppConfig();
    }

   /* public void btn_PostResponse_onClickBtn(View v) {
        PostDao postDao = new PostDao();
        Date createdDate = new Date();
        PostResponse postResponse = new PostResponse("KgpySaT5EuZjvmuK1aO1KrEYHj52", IPAddress.getMACAddress("wlan0"), IPAddress.getMACAddress("eth0"), createdDate, "Nam gravida scelerisque risus. Cras in quam vitae leo cursus ornare. Nullam nec diam eros. Phasellus sed justo nisl. Praesent pharetra massa vel mauris efficitur malesuada.",
                PostResponse.Status.Approved, "", "");
        postDao.CreatePostResponse("CMTY20171011210657333", "POST20171011211110040", keyGen.GetNewPostCommentKey(), postResponse);
    }*/

    public void btn_PollResponse_onClickBtn(final View v) {
        PollsDao pollsDao = new PollsDao();
        Date createdDate = new Date();
        PollResponse pollResponse = new PollResponse(IPAddress.getMACAddress("wlan0"), IPAddress.getMACAddress("eth0"), "N", createdDate, null, null);

        pollsDao.CreatePollResponse("CMTY-20171002214959926", "POLL-20171020200025163", "KgpySaT5EuZjvmuK1aO1KrEYHj52", pollResponse);
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
        CommunityInviteCodeDao communityInviteCodeDao = new CommunityInviteCodeDao();
        CommunityInviteCode communityInviteCode = new CommunityInviteCode("54588");
        communityInviteCodeDao.CreateInviteCode("CMTY-20171002214959926", communityInviteCode);
    }

    public void btn_Events_onClickBtn(View v) throws Exception {
        EventsDao eventsDao = new EventsDao();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date createdDate = new Date();
        String contents = "Dialogues Cafe invites all the book lovers to its October edition of Dialogues with books. We host this delightful gathering every second Saturday of the month! \n" +
                "\n" +
                "At Dialogues with Books, the participants read and discuss their favorite book from the specified genre to some of the fellow bibliophiles. \n" +
                "It is a 2-hour event where we divide time equally between the participants and we prefer each participant to contribute through reading a passage from their favorite book. We also allow free-flowing discussions which are moderated by our host. Those who do not wish to read can also attend the event and participate in the discussions. The theme of the October episode is Children Literature.\n" +
                "Also, This event is not a platform for people to promote their own(or family/friends) books.";

        Events events = new Events("Dialogues with Books (Children Literature) - With Deya Bhattacharya", contents, "Community Hall", "Committee Members", "Monthly Meeting",
                createdDate, createdDate, "4A0lC6N0XOTWdsr2DetL1NOcGax1", createdDate, null, null);
        eventsDao.CreateEvents("CMTY-20171002214959926", keyGen.GetNewEventsKey(), events);
    }

    public void btn_Members_onClickBtn(View v) {
        MembersDao membersDao = new MembersDao();
        Date createdDate = new Date();
        Members members = new Members("narayanan.vsn@gmail.com", createdDate, createdDate, Members.Status.Active, "Narayanan", "Dayalan", 0, 0, 0, "Mr", "", "9840399445", null, null, "This is all what i have", "ADMIN");
        membersDao.CreateMembers("CMTY-20171002214959926", "6wR7YC72wecEhqKsiggCtrAgylj2", members);
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

        PollsDao pollsDao = new PollsDao();
        Date createdDate = new Date();
        Poll poll = new Poll("Do you believe that the recent actions initiated by the government, against certain Twitter accounts is wrong?", Poll.Status.Active
                , "Yes", "No", "Cant Say", "YNC", "6wR7YC72wecEhqKsiggCtrAgylj2", createdDate, null, null);

        pollsDao.CreatePoll("CMTY-20171002214959926", keyGen.GetNewPollKey(), poll);
    }

    public void btn_Notifications_onClickBtn(View v) {

    }

    public void btn_Users_onClickBtn(View v) {
        GlobalUserDao globalUserDao = new GlobalUserDao();
        Date createdDate = new Date();
        GlobalUser globalUser = new GlobalUser("Mr", "Narayanan", "Dayalan", "narayanan.vsn@gmail.com", "gavs123", "+919840399445", "N", "5487541", "N", "4A0lC6N0XOTWdsr2", createdDate, "CMTY-20171002214959926");
        ActionResponse response = globalUserDao.CreateUser(globalUser);
        if (response.success == false) {
            Toast.makeText(this, response.error_message, Toast.LENGTH_LONG).show();
        }
    }
}
