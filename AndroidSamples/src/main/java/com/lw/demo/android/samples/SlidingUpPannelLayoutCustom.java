package com.lw.demo.android.samples;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class SlidingUpPannelLayoutCustom extends SlidingUpPanelLayout {
    public SlidingUpPannelLayoutCustom(Context context) {
        super(context);
    }

    public SlidingUpPannelLayoutCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingUpPannelLayoutCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean isMainHandleTouch = true;

    public void setMainCanTouch(boolean handleTouch){
        this.isMainHandleTouch = handleTouch;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isMainHandleTouch){
            View mainView = getChildAt(1);
            boolean result = isTouchPointInView(mainView,ev.getRawX(),ev.getRawY());
            if(!result){
                return false;
            }else{
                return super.onTouchEvent(ev);
            }
        }
        return super.onTouchEvent(ev);
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isMainHandleTouch){
            View mainView = getChildAt(1);
            boolean result = isTouchPointInView(mainView,ev.getRawX(),ev.getRawY());
            if(!result){
                return false;
            }else{
                return super.dispatchTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
