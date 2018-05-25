package com.lw.demo.android.samples;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity2 extends AppCompatActivity {

    private static final String TAG = ViewPagerActivity2.class.getSimpleName();
    private ViewPager mViewPager;
    private MagicIndicator mIndicator;
    private CommonPagerAdapter<Integer> mAdapter;

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
       List<Integer> data = new ArrayList<Integer>();
       data.add(R.drawable.mm1);
       data.add(R.drawable.mm2);
       data.add(R.drawable.mm3);
       data.add(R.drawable.mm4);
       data.add(R.drawable.mm5);
        mAdapter.setData(data);
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

            //我们给不同状态的页面设置不同的效果

            //通过position的值来分辨页面所处于的状态

                /*int margin = getResources().getDimensionPixelOffset(R.dimen.pager_margin)/2;
                margin/=width;
                margin = 0;

                if(position < - 1) { //滑出的页面

                    page.setScrollX(( int) (width * 0.75*position - 1));

                } else if(position <= 1) { //[-1,1]

                    if(position < 0) { //[-1,0]

                        page.setScrollX(( int) (width * 0.75* position)-margin);

                    } else{ //[0,1]

                        page.setScrollX(( int) (width * 0.75* position)+margin);

                    }

                } else{ //即将滑入的页面

                    page.setScrollX(( int) (width * 0.75)+margin);

                }*/
            }
        });
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mIndicator = findViewById(R.id.indicator);
    }


}
