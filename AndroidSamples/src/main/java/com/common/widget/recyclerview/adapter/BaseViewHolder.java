package com.common.widget.recyclerview.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.android.samples.R;

/**
 * 通用ViewHolder
 *
 * 用一个
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <V extends View>V getView(int viewId) {
        try{
            return retrieveView(viewId);
        }catch (ClassCastException e){
            return null;
        }
    }

   public TextView setText(int viewId,String text){
        TextView tv = getView(viewId);
        if(tv!=null){
            tv.setText(text);
        }
        return tv;
   }

   public ImageView setImgResource(int viewId,int resId){
       ImageView img = getView(viewId);
       if(img!=null){
           img.setImageResource(resId);
       }
       return img;
   }

   public View setBackGroupDrawable(int viewId,Drawable drawable){

        View view = getView(viewId);
        if(view!=null){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(drawable);
            else{
                view.setBackgroundDrawable(drawable);
            }
        }
        return view;
   }

    public View setBackGroupDrawable(int viewId, int resId){

        View view = getView(viewId);
        if(view!=null){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(view.getContext().getResources().getDrawable(resId));
            else{
                view.setBackgroundDrawable(view.getContext().getResources().getDrawable(resId));
            }
        }
        return view;
    }

    protected <V extends View> V retrieveView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (V) view;
    }
}
