package com.mellow;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;

import com.mellow.mellow.R;

public class MainActivity extends AppCompatActivity {

    private CustomPagerAdapter customPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        setUpViewPager();
        setUpTabLayout();
        mViewPager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setUpTabLayout(){
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.setSelectedTabIndicatorHeight(7);
        }
    }

    private void setUpViewPager(){
        this.mViewPager = (ViewPager) findViewById(R.id.view_pager);
        if (mViewPager != null) {
            mViewPager.setAdapter(customPagerAdapter);
        }
    }

    private void setUpToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
