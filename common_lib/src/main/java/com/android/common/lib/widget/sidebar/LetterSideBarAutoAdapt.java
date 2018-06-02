package com.android.common.lib.widget.sidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 自适应LetterSideBar
 * 需要用户手动设置侧边显示的字母
 */

public class LetterSideBarAutoAdapt extends View {


    public LetterSideBarAutoAdapt(Context context) {
        super(context);
    }

    public LetterSideBarAutoAdapt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterSideBarAutoAdapt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 字母数组
    private static final String[] letters = {"A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private List<String> letterList = new ArrayList<>();
    private static final String COLOR_NORMAL = "#6E6D6E";
    private static final String COLOR_SELECT = "#FEA329";
    private static final String COLOR_ONTOUCH = "#40000000";

    private Rect mLetterRect = new Rect();//字母显示的区域，动态设置Rect需要重新计算Rect

    private int currentIndex = 0;

    private int maxTouchAvabileBond = 100;//字母显示区域最大宽度，也是默认宽度

    private int offset = 10;//字母默认间隔距离

    private boolean isNeedCalc = false;

    private Paint mPaint = new Paint(Color.BLUE);

    private boolean isIndexChanged = false;
    private float letterSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private float centerSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, getResources().getDisplayMetrics());
    private OnIndexSelectChanged mSelectListener;

    public void setSelectChangedListener(OnIndexSelectChanged listener) {
        this.mSelectListener = listener;
    }

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
        if (letterList.size() == 0) {
            return;
        }
        if (isNeedCalc) {
            calcLetterRect();
        }
        int w = maxTouchAvabileBond;
        int h = mLetterRect.height();
        int itemH = h / letterList.size();
        int startY = mLetterRect.top;
        int leftX = mLetterRect.left;
       /* mPaint.reset();
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(mLetterRect,mPaint);*/
        for (int i = 0; i < letterList.size(); i++) {
            mPaint.reset();
            mPaint.setTextSize(letterSize);
            mPaint.setAntiAlias(true);
            mPaint.setFakeBoldText(true);
            if (currentIndex == i) {
                mPaint.setColor(Color.parseColor(COLOR_SELECT));
            } else {
                mPaint.setColor(Color.parseColor(COLOR_NORMAL));
            }
            String text = letterList.get(i);
            Rect rect = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), rect);
            int textH = rect.height();
            int textW = rect.width();
            int startX = leftX + (maxTouchAvabileBond - textW) / 2;
//            startY+=(itemH+textH)/2;
            canvas.drawText(text, startX, startY + itemH * (i + 1), mPaint);
//            mPaint.reset();
        }
        if (isIndexChanged) {
            draCenterCircle(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (letterList.size() == 0) {
            return super.onTouchEvent(event);
        }
        if (!mLetterRect.contains((int) event.getX(), (int) event.getY())) {
            return super.onTouchEvent(event);
        }
        float x = event.getRawX();
        if (getMeasuredWidth() - x > maxTouchAvabileBond) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //计算当前在第几个index
                int index = (int) ((y - mLetterRect.top) / (mLetterRect.height() / letterList.size()));
                if (index >= 0 && index < letterList.size()) {
                    if (currentIndex != index) {
                        if (mSelectListener != null) {
                            mSelectListener.onSelectChanged(currentIndex, index, letterList.get(index));
                        }
                        currentIndex = index;
                        isIndexChanged = true;
                    } else {
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

    private void draCenterCircle(Canvas canvas) {
        mHandler.removeCallbacks(mClearCircleRunnable);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 100, paint);
        paint.reset();
        paint.setColor(Color.parseColor(COLOR_SELECT));
        paint.setTextSize(centerSize);
        String text = letterList.get(currentIndex);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int textH = rect.height();
        int textW = rect.width();
        int startX = (getMeasuredWidth() - textW) / 2;
        int startY = (getMeasuredHeight() + textH) / 2;
        //测试发现，drawText指定的位置，是以基线为准的，基线并不是文字的水平中心线，而是文字底部开始的线，所以（x,y）对应的是左下角开始的
        canvas.drawText(text, startX, startY, paint);
        mHandler.postDelayed(mClearCircleRunnable, 1000);
    }

    public interface OnIndexSelectChanged {
        void onSelectChanged(int lastIndex, int curIndex, String letter);
    }

    public void setCurrentLetter(String letter) {
        if (!TextUtils.isEmpty(letter)) {
            for (int i = 0; i < letterList.size(); i++) {
                if (letterList.get(i).equalsIgnoreCase(letter)) {
                    if (currentIndex != i) {
                        currentIndex = i;
                        invalidate();
                    }
                }
            }
        }
    }

    public void setLetters(List<String> letters) {
        letterList.clear();
        letterList.addAll(letters);
        isNeedCalc = true;
        invalidate();
    }

    /**
     * 计算字母显示的区域范围
     * 从屏幕右侧位置，
     * 宽度是maxTouchAvabileBond
     * 高度需要计算 = offfset*count+letterHeight
     * 计算每一个字母的高度+字母间的间隔距离即可
     * 间隔距离默认是2
     */
    private void calcLetterRect() {
        Paint paint = new Paint();
        paint.setTextSize(letterSize);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        int letterHeight = 0;
        for (int i = 0; i < letterList.size(); i++) {
            String text = letterList.get(i);
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            letterHeight += rect.height();
        }
        letterHeight += (offset * letterList.size());
        mLetterRect.left = getMeasuredWidth() - maxTouchAvabileBond;
        mLetterRect.right = getMeasuredWidth();
        mLetterRect.top = (getMeasuredHeight() - letterHeight) / 2;
        mLetterRect.bottom = (getMeasuredHeight() + letterHeight) / 2;
        isNeedCalc = false;
    }

}
