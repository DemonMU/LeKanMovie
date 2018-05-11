package com.project.rudy.lekanmovie.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * 首页TabPagerAdapter
 * <p/>
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[];
    private List<Fragment> fragments;

    public TabPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setTabTitles(@NonNull String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null || fragments.size() == 0)
            return 0;
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTitles == null)
            return "";
        return tabTitles[position];
    }
}