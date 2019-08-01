package com.lw.demo.android.samples;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class CommonPagerAdapter<T> extends PagerAdapter {

    public enum Type{
        TEXT,
        IMG;
    }
    private final String TAG = CommonPagerAdapter.class.getSimpleName();
    private Type mType = Type.TEXT;

    public void setType(Type type){
        this.mType = type;
    }


    private List<T> mData = new ArrayList<T>();

    public void setData(List<T> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if(mType == Type.TEXT){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.common__viewpager_item,container,false);
        }else if(mType == Type.IMG){
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.common__viewpager_item_img,container,false);
            ImageView img = view.findViewById(R.id.img_content);
            RequestManager manager = Glide.with(img);
            manager.load(mData.get(position)).into(img);
        }
        container.addView(view);
        view.setTag(position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        /*if(object instanceof View){
            View itemView = (View) object;
            TextView tv = itemView.findViewById(R.id.tv_content);
            tv.setText(mData.get(position).toString());
        }*/
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(object instanceof  View){
            View view = (View) object;
            Object obj = view.getTag();
            if(obj!=null &&obj instanceof Integer){
                int position = (int) view.getTag();
                Log.i(TAG,"getItemPosition() position = "+position);
                return position;
            }
        }

        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
