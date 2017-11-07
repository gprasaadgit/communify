package com.gap22.community.apartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gap22.community.apartment.Common.GlobalValues;
import com.gap22.community.apartment.Common.StoragePreferences;
import com.gap22.community.apartment.Dao.PostDao;
import com.gap22.community.apartment.Database.KeyGenerator;
import com.gap22.community.apartment.Entities.Post;
import com.gap22.community.apartment.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class CreatePost extends Fragment {

    private FirebaseAuth fireauth;
    private EditText Title,Posts;
    private Button create,cancel;
    private StoragePreferences storagePref;
    public CreatePost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View CreatePost = inflater.inflate(R.layout.fragment_post_create, container, false);
        fireauth = FirebaseAuth.getInstance();
        storagePref = StoragePreferences.getInstance(getActivity());

        Title = (EditText) CreatePost.findViewById(R.id.et_title);
        Posts = (EditText) CreatePost.findViewById(R.id.et_description);
        create =(Button) CreatePost.findViewById(R.id.btn_submit);
        cancel = (Button) CreatePost.findViewById(R.id.btn_cancel);
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


                Toast.makeText(getActivity(), "Post Created", Toast.LENGTH_SHORT).show();
               /* startActivity(new Intent(PostActivity.this, GetCollaborated.class));
                finish();*/
                Fragment Post = new PostFragment();


                               /* Intent i = new Intent(getActivity(), PollResultActivity.class);
                                i.putExtras(bundle);*/

                FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, Post);
                ft.commit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();*/
                Fragment Post = new PostFragment();


                               /* Intent i = new Intent(getActivity(), PollResultActivity.class);
                                i.putExtras(bundle);*/

                FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, Post);
                ft.commit();
            }
        });
        return CreatePost;
    }
    public boolean validate(Post p)
    {

        if(TextUtils.isEmpty(p.title))
        {
            Toast.makeText(getActivity(),"Please Enter Title",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(p.content))
        {
            Toast.makeText(getActivity(),"Please Enter Content",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}