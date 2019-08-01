package com.lw.demo.android.samples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigatorSeparate;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.CommonPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.CommonPagerIndicatorSeparate;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.ScaleTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity2 extends AppCompatActivity {

    private static final String TAG = ViewPagerActivity2.class.getSimpleName();
    private ViewPager mViewPager;
    private MagicIndicator mIndicator;
    private CommonPagerAdapter<Integer> mAdapter;
    private List<Integer> mDataList =mDataList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_2);
        initView();
        initData();
    }

    private void initData() {
        mAdapter = new CommonPagerAdapter();
        mAdapter.setType(CommonPagerAdapter.Type.IMG);
        mViewPager.setAdapter(mAdapter);
        int page_margin = getResources().getDimensionPixelOffset(R.dimen.pager_margin);
        mViewPager.setPageMargin(page_margin/2);
        mViewPager.setOffscreenPageLimit(3);
       mDataList.add(R.drawable.mm1);
       mDataList.add(R.drawable.mm2);
       mDataList.add(R.drawable.mm3);
       mDataList.add(R.drawable.mm4);
       mDataList.add(R.drawable.mm5);
        mAdapter.setData(mDataList);

        CommonNavigatorSeparate navigator = new CommonNavigatorSeparate(this);
        navigator.setAdapter(mNavigatorAdapter);
        navigator.setAdjustMode(true);
        navigator.setIndicatorOnTop(true);
        navigator.setTitleBackGroundColor(Color.parseColor("#2EA251"));
        mIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(mIndicator,mViewPager);

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                Log.i(TAG,"transformPage page = "+page.getTag().toString()+",position = "+position);
                int width = page.getWidth();
                page.setPivotY(page.getHeight()/2);//Y方向缩放的锚点
                page.setPivotX(0);//X方向缩放的锚点
                if(position<=0){
                    page.setPivotX(page.getWidth());//设置X方向缩放的锚点，默认是视图的中心点坐标，如果想要实现同一个屏幕显示多个Item的效果，那么就不能用默认值，默认是视图中心点坐标
                }
                /*
                 * [-1,0]这个区间是左边页面
                 * 0 是中间当前页面
                 * [0,1]是右边页面
                 * */
                if(position>=-1&&position<=0){//当前页，慢慢想左边滑动
                    float radio = Math.abs(position+1);
                    page.setScaleX(0.6f+radio*0.4f);
                    page.setScaleY(0.6f+radio*0.4f);
                }else if(position>=0&&position<=1){//右边页面，慢慢向页面中间滑动
                    float radio = Math.abs(1-position);
                    page.setScaleX(0.6f+radio*0.4f);
                    page.setScaleY(0.6f+radio*0.4f);
                }else if(position>1&&position<2){
                    float radio = Math.abs(position-1);
                    page.setScaleX(0.6f+radio*0.4f);
                    page.setScaleY(0.6f+radio*0.4f);
                }
            }
        });
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mIndicator = findViewById(R.id.indicator);
    }

    private CommonNavigatorAdapter mNavigatorAdapter = new CommonNavigatorAdapter() {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
//            SimplePagerTitleView titleView = new ColorTransitionPagerTitleView(context);
            SimplePagerTitleView titleView = new ScaleTransitionPagerTitleView(context);
            titleView.setText("Tab"+index);
//            titleView.setBackgroundColor(Color.parseColor("#2EA251"));
            titleView.setNormalColor(Color.parseColor("#FCFEFD"));
            titleView.setSelectedColor(Color.parseColor("#ABF9C2"));
            titleView.setSize(getResources().getDimensionPixelSize(R.dimen.indicator_text_size));
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(index);
                }
            });
            return titleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            final CommonPagerIndicatorSeparate indicator = new CommonPagerIndicatorSeparate(context);
            indicator.setIndicatorDrawable(getResources().getDrawable(R.drawable.sanjiao));
            indicator.setMode(CommonPagerIndicator.MODE_EXACTLY);
          /* RequestManager manager =  Glide.with(context);
            RequestBuilder builder = manager.asBitmap();
            builder.load(R.drawable.sanjiao);
            builder.into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
                    indicator.setDrawableHeight(resource.getHeight());
                    indicator.setDrawableWidth(resource.getWidth());
                }
            });*/


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sanjiao);
            indicator.setDrawableHeight(bitmap.getHeight());
            indicator.setDrawableWidth(bitmap.getWidth());
          return indicator;
        }
    };


}
