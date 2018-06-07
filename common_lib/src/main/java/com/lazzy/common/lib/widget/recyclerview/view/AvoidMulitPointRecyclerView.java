package com.lazzy.common.lib.widget.recyclerview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AvoidMulitPointRecyclerView extends RecyclerView {
    public AvoidMulitPointRecyclerView(Context context) {
        super(context);
        addOnItemTouchListener(mItemTouchListener);
    }

    public AvoidMulitPointRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addOnItemTouchListener(mItemTouchListener);
    }

    public AvoidMulitPointRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnItemTouchListener(mItemTouchListener);
    }

    private OnItemTouchListener mItemTouchListener = new OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            switch (e.getAction()& MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    return false;
                case MotionEvent.ACTION_POINTER_DOWN:
                    return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

}
