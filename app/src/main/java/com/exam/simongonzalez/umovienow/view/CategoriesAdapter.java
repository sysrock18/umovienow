package com.exam.simongonzalez.umovienow.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by simongonzalez on 10/03/17.
 */

public class CategoriesAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public CategoriesAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
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
