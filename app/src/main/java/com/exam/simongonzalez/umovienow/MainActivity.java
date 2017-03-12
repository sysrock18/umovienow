package com.exam.simongonzalez.umovienow;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.exam.simongonzalez.umovienow.view.CategoriesAdapter;
import com.exam.simongonzalez.umovienow.view.PopularView;
import com.exam.simongonzalez.umovienow.view.TopRatedView;
import com.exam.simongonzalez.umovienow.view.UpcomingView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        setUpViewPager();
    }

    private ArrayList<Fragment> addCategoryFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PopularView());
        fragments.add(new TopRatedView());
        fragments.add(new UpcomingView());

        return fragments;
    }

    private void setUpViewPager() {
        viewPager.setAdapter(new CategoriesAdapter(getSupportFragmentManager(), addCategoryFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(getResources().getString(R.string.popular));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.top_rated));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.upcoming));
    }


}
