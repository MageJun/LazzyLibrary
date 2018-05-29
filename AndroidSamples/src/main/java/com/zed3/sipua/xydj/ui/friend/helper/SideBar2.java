package com.zed3.sipua.xydj.ui.friend.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class SideBar2 extends View {
    public SideBar2(Context context) {
        super(context);
    }

    public SideBar2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 字母数组
    private static final String[] letters = { "#","A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z" };
    private static final String COLOR_NORMAL = "#6E6D6E";
    private static final String COLOR_SELECT = "#FEA329";
    private static final String COLOR_ONTOUCH = "#40000000";

    private int currentIndex = 0;

    private int maxTouchAvabileBond = 100;

    private Paint mPaint = new Paint(Color.BLUE);

    private boolean isIndexChanged =false;
    private float letterSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics());
    private float centerSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,25,getResources().getDisplayMetrics());

    private Handler mHandler = new Handler();

    private Runnable mClearCircleRunnable = new Runnable() {
        @Override
        public void run() {
            isIndexChanged = false;
            invalidate();
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = maxTouchAvabileBond;
        int h = getMeasuredHeight();
        int itemH = h/letters.length;
        int startY = 0;
        int leftX = getMeasuredWidth()-maxTouchAvabileBond;
        for (int i = 0;i<letters.length;i++){
            mPaint.setTextSize(letterSize);
            mPaint.setAntiAlias(true);
            mPaint.setFakeBoldText(true);
            if(currentIndex==i){
                mPaint.setColor(Color.parseColor(COLOR_SELECT));
            }else{
                mPaint.setColor(Color.parseColor(COLOR_NORMAL));
            }
            startY = itemH*i;
            String text = letters[i];
            Rect rect = new Rect();
            mPaint.getTextBounds(text,0,text.length(),rect);
            int textH = rect.height();
            int textW = rect.width();
            int startX=leftX+(maxTouchAvabileBond-textW)/2;
            startY+=(itemH-textH)/2;
            canvas.drawText(text,startX,startY,mPaint);
            mPaint.reset();
        }
        if(isIndexChanged){
            draCenterCircle(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
       if(getMeasuredWidth()-x>maxTouchAvabileBond){
           return super.onTouchEvent(event);
       }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getRawY();
                //计算当前在第几个index
                int index = (int) (y/(getMeasuredHeight()/letters.length));
                if(index>=0&&index<letters.length){
                    if(currentIndex!=index){
                        currentIndex = index;
                        isIndexChanged = true;
                    }else{
                        return super.onTouchEvent(event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
//                currentIndex = -1;
                break;
        }
        invalidate();
        return true;
    }

    private void draCenterCircle(Canvas canvas){
        mHandler.removeCallbacks(mClearCircleRunnable);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,80,paint);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        canvas.drawLine(0,getMeasuredHeight()/2,getMeasuredWidth(),getMeasuredHeight()/2,paint);
        canvas.drawLine(getMeasuredWidth()/2,0,getMeasuredWidth()/2,getMeasuredHeight(),paint);
        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setTextSize(centerSize);
        String text = letters[currentIndex];
        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        int textH = rect.height();
        int textW = rect.width();
        int startX = (getMeasuredWidth()-textW)/2;
        int startY = (getMeasuredHeight()+textH)/2;
        //测试发现，drawText指定的位置，是以左下角开始的
        canvas.drawText(text,startX,startY,paint);
        mHandler.postDelayed(mClearCircleRunnable,1000);
    }



}
