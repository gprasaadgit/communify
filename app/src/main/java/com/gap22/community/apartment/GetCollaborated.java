package com.gap22.community.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gap22.community.apartment.Common.FontsOverride;
import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Fragments.AcceptInvite;
import com.gap22.community.apartment.Fragments.CollabrationPallet;
import com.gap22.community.apartment.Fragments.EventsCalendar;
import com.gap22.community.apartment.Fragments.InviteNeighbours;
import com.gap22.community.apartment.Fragments.MyCommunity;
import com.gap22.community.apartment.Fragments.MyProfile;
import com.gap22.community.apartment.Fragments.Notifications;
import com.gap22.community.apartment.Fragments.RaiseMyQuery;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GetCollaborated extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView;

    private StoragePreferences storagePref;
    private TextView username, useremail;
    private ImageView userimg;
    Menu menu;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_get_collaborated);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        storagePref = StoragePreferences.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        View header = navigationView.getHeaderView(0);
        {
            MenuItem item = menu.findItem(R.id.nav_Manage);

            //item.setVisible(false);
            //System.out.print("Community :" + item.isVisible() + "item" + item.getTitle());

            username = (TextView) header.findViewById(R.id.tv_username);
            useremail = (TextView) header.findViewById(R.id.tv_email);
            userimg = (ImageView) header.findViewById(R.id.img_user_image);
            StorageReference storageRef = storage.getReferenceFromUrl("gs://communify-4b71c.appspot.com/" + GlobalValues.getCurrentUserUuid() + ".jpg");
            username.setText(GlobalValues.getCurrentUserName());
            useremail.setText(GlobalValues.getCurrentUserEmail());
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(storageRef)
                    .error(R.drawable.admin)
                    .into(userimg);
        }

        setTitle(R.string.main_panel_title);
        displaySelectedScreen(R.id.nav_home);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_get_collaborated_drawer, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new CollabrationPallet();
                break;
            case R.id.nav_profile:
                fragment = new MyProfile();
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_events:
                fragment = new EventsCalendar();
                break;
            case R.id.nav_notification:
                fragment = new Notifications();
                break;
            case R.id.nav_my_community:
                fragment = new MyCommunity();
                break;
            case R.id.nav_invite_neighbours:
                fragment = new InviteNeighbours();
                break;
            case R.id.nav_raise_a_query:
                fragment = new RaiseMyQuery();
                break;
            case R.id.nav_manage_invites:
                fragment = new AcceptInvite();
                break;
            case R.id.nav_update_community_info:
                break;
            case R.id.nav_set_rights:
                break;
            case R.id.nav_set_answer_queries:
                break;
            case R.id.nav_signout:
                Intent signUpActivity = new Intent(getApplicationContext(), MainActivity.class);
                SignOutProcess();
                startActivity(signUpActivity);
                overridePendingTransition(R.anim.slide_up_info, R.anim.slide_down_info);
                finish();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void SignOutProcess() {
        storagePref.deletePreference("EmailId");
        storagePref.deletePreference("SafePassword");
        storagePref.deletePreference("FullName");
        storagePref.deletePreference("CommunityID");
        storagePref.deletePreference("Image");
        storagePref.deletePreference("UserId");
    }
}
