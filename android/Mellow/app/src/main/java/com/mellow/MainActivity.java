package com.mellow;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.mellow.mellow.R;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
    }
}
