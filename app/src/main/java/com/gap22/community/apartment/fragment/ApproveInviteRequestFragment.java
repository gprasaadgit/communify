package com.gap22.community.apartment.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gap22.community.apartment.R;

public class ApproveInviteRequestFragment extends Fragment {

    private int mIndex;

    public ApproveInviteRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragPollView = inflater.inflate(R.layout.fragment_approve_invite_request, container, false);
        return fragPollView;
    }
}
