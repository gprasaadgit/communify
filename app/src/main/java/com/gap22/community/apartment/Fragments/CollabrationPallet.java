package com.gap22.community.apartment.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gap22.community.apartment.R;

import java.util.ArrayList;
import java.util.List;

public class CollabrationPallet extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public CollabrationPallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragPollView = inflater.inflate(R.layout.fragment_collabration_pallet, container, false);
        viewPager = (ViewPager) fragPollView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) fragPollView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return fragPollView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.main_panel_title);
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("  Post  ");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tab_icon_post, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("  Polls ");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tab_icon_polls, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("  Community");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tab_icon_community, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        CollabrationPallet.ViewPagerAdapter adapter = new CollabrationPallet.ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new PostFragment(), "Post");
        adapter.addFragment(new PollsFragment(), "Polls");
        adapter.addFragment(new CommunityFragment(), "Community");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
