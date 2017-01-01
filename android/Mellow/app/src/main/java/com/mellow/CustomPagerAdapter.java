package com.mellow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mellow.fragment.ChatFragment;
import com.mellow.fragment.FlowFragment;
import com.mellow.fragment.ProfileFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChatFragment();
            case 1:
                return new FlowFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new FlowFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chat";
            case 1:
                return "Flow";
            case 2:
                return "Profile";
            default:
                return "Flow";
        }
    }
}
