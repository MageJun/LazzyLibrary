package com.android.kotlindemo.presenter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.kotlindemo.AppApplication;
import com.android.kotlindemo.R;
import com.android.kotlindemo.view.IViewer;

public abstract class TestBasePresenter {

    protected IViewer mViewer;

    public void bindView(IViewer viewer){
        this.mViewer = viewer;
    }

    private PagerAdapter adapter = new PagerAdapter() {
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_item_img,container,false);
            ImageView img = view.findViewById(R.id.img);
            container.addView(view);
            return view;
        }


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    };

    private void initView(){
        ViewPager viewPager = new ViewPager(AppApplication.Companion.appContext());
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
