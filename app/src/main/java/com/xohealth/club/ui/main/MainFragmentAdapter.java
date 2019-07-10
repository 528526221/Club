package com.xohealth.club.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Date：2018/4/10
 * Desc：
 * Created by xuliangchun.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;


    public MainFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
