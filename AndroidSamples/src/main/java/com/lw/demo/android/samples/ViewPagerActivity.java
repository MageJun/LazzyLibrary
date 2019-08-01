package com.lw.demo.android.samples;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.LayoutBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private IndicatorViewPager mIndV;
    private Indicator mIndicator;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        mContext = this;
        initView();
        initData();
    }


    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mIndicator =  findViewById(R.id.indicator);
    }

    private void initData() {
        mIndV = new IndicatorViewPager(mIndicator,mViewPager);
        MyAdapter adapter = new MyAdapter();
//        DrawableBar bar = new DrawableBar(this,R.drawable.person_icon);
//        TextWidthColorBar bar = new TextWidthColorBar(this,mIndicator,R.color.commonui_color_bg,4);
//        ColorBar bar = new ColorBar(this,R.color.commonui_color_bg,2, ScrollBar.Gravity.TOP);
        LayoutBar bar = new LayoutBar(this,R.layout.triangle_indicator, ScrollBar.Gravity.TOP_FLOAT);
        mIndV.setIndicatorScrollBar(bar);
        mIndV.setAdapter(adapter);

    }

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter{

        private String[] names = {"推荐","发现","Vip","好友","我"};

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if(convertView==null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_viewpager,container,false);
            }
            TextView tv = convertView.findViewById(R.id.tab_tv);
            tv.setText(names[position]);
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if(convertView==null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_pager_content,container,false);
            }
            TextView tv = convertView.findViewById(R.id.content_tv);
            tv.setText("第"+(position+1)+"页");
            return convertView;
        }
    }
}
