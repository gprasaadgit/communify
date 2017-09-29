package com.gap22.community.apartment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gap22.community.apartment.Common.FontsOverride;

public class JoinOrCreateCommunity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/avenirltstd-book.ttf");
        setContentView(R.layout.activity_join_or_create_community);
    }
}
