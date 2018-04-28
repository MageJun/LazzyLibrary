package com.lw.android.commonui.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import static android.content.ContentValues.TAG;

/**
 * Created by zed on 2018/4/27.
 */

public class SwipeLayout extends HorizontalScrollView {

    private static final String TAG = SwipeLayout.class.getSimpleName();

    private boolean flag;
    public SwipeLayout(Context context) {
        super(context);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mMenuWidth;
    private int mContentWidth;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        Log.i(TAG,"onMeasure widthMeasureSpec = "+MeasureSpec.getSize(widthMeasureSpec)+", heightMeasureSpec = "+MeasureSpec.getSize(heightMeasureSpec));
        if(!flag){
            flag = true;

            ViewGroup parent = (ViewGroup) getChildAt(0);

            int childCount = parent.getChildCount();

           /* if(childCount==2){
                ViewGroup content = (ViewGroup) parent.getChildAt(0);
                ViewGroup menu = (ViewGroup) parent.getChildAt(1);

                mMenuWidth = menu.getMeasuredWidth();

            }*/

        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
