package com.gap22.community.apartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gap22.community.apartment.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostResponses extends Fragment {


    public PostResponses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_responses, container, false);
    }

}
