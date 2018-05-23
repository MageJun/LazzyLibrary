package com.lw.demo.android.samples;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.lucode.hackware.magicindicator.MagicIndicator;

public class ViewPagerActivity2 extends AppCompatActivity {

    private ViewPager mViewPager;
    private MagicIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_2);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mIndicator = findViewById(R.id.indicator);
    }
}
