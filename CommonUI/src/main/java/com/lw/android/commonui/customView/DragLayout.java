package com.lw.android.commonui.customView;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 自定义可拖拽子视图的ViewGroup
 * 使用自定义视图工具ViewDragHelper和ViewDragHelper.CallBack来实现
 * Created by zed on 2018/5/3.
 */

public class DragLayout extends FrameLayout {

    private static final String TAG = DragLayout.class.getSimpleName();

    public  enum Status {
        OPEN, CLOSE, DRAGING
    }

    public interface SwipeStatusChangeListener {
        void onDraging(DragLayout mSwipeLayout);

        void onOpen(DragLayout mSwipeLayout);

        void onClose(DragLayout mSwipeLayout);

        void onStartOpen(DragLayout mSwipeLayout);

        void onStartClose(DragLayout mSwipeLayout);
    }


    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private Context mContext;

    private ViewDragHelper mDragHelper;

    private Status status = Status.CLOSE;

    private SwipeStatusChangeListener swipeChangeListener;

    private ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {

        /**
         *在这个方法中，决定某个View是否可以拖动
         * @param view
         * @param i pointerId 单点触摸时间的ID，手指点击屏幕，一个完整的触摸时间是从DOWN到UP，在DOWN状态时，会分配一个ID，这个就是PointerID，会在UP时取消
         * @return true允许拖动  false不允许拖动
         */
        @Override
        public boolean tryCaptureView(View view, int i) {
            Log.i(TAG, "tryCaptureView view = " + view.getId() + ",pointerID = " + i);
            return true;
        }


        /**
         * 拖拽完成，手指离开屏幕会回调这个方法
         * @param releasedChild
         * @param xvel 水平方向手指离开的速度
         * @param yvel 垂直方向上手指离开的速度
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.i(TAG, "onViewReleased() releasedChild = " + releasedChild.getId() + ",xvel = " + xvel + ", yvel = " + yvel);
            if (xvel == 0 && mContentView.getLeft() < -menuWidth * 0.5f) {//水平不自由滑动
                open();
            } else if (xvel < 0) {//水平向左自由滑动
                open();
            } else {
                close();
            }
        }

        /**
         * 返回水平方向可以移动的边界位置，返回给ViewDragHelper
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.i(TAG, "clampViewPositionHorizontal child = " + child.getId() + ",left = " + left + ",dx = " + dx);
            if (child == mContentView) {
                if (left > 0) {
                    return 0;
                } else if (left < -menuWidth) {
                    return -menuWidth;
                }
            } else if (child == mMenuView) {
                if (left > contentWidth) {
                    return contentWidth;
                } else if (left < contentWidth - menuWidth) {
                    return contentWidth - menuWidth;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "onViewPositionChanged changedView = " + changedView.getId() + ",left = " + left + ",top = " + top + ",dx = " + dx + ",dy = " + dy);
//            super.onViewPositionChanged(changedView, left, top, dx, dy);

            if (changedView == mContentView) {
                mMenuView.offsetLeftAndRight(dx);
            } else if (changedView == mMenuView) {
                mContentView.offsetLeftAndRight(dx);
            }


            dispatchSwipeEvent();
            //兼容低版本，不加上这个，菜单显示区不会移动出来，再显示区左移之后，无法显示菜单项
            invalidate();
        }


        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }
    };


    private int contentWidth, contentHeight;//主显示区宽

    private int menuWidth, menuHeight;//菜单去宽度

    private View mContentView;//主显示View

    private View mMenuView;//菜单View

    private void init(Context context) {

        /**
         *  forParent 一个ViewGroup， 也就是ViewDragHelper将要用来拖拽谁下面的子View；
         sensitivity 灵敏度，一般设置为1.0f就行，默认就是1.0，值越大越灵敏；
         cb 一个回调，用来处理拖拽后的逻辑
         */
        mDragHelper = ViewDragHelper.create(this, 1.0f, mCallBack);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mDragHelper.processTouchEvent(event);

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        Log.i(TAG, "onSizeChanged enter");
        super.onSizeChanged(w, h, oldw, oldh);

        contentWidth = mContentView.getMeasuredWidth();
        contentHeight = mContentView.getMeasuredHeight();
        menuWidth = mMenuView.getMeasuredWidth();
        Log.i(TAG, "onSizeChanged enter contentWidth = " + contentWidth + ",menuWidth = " + menuWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure enter");
    }

    @Override
    protected void onFinishInflate() {
        Log.i(TAG, "onFinishInflate enter");
        super.onFinishInflate();

        if (getChildCount() < 2) {
            throw new RuntimeException("child count may not be less than 2 !");
        }

        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    private boolean isOpen;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout enter");
        super.onLayout(changed, left, top, right, bottom);

        layoutView(false);
    }

    private void layoutView(boolean isOpen) {
        int cLeft = 0;
        if (isOpen) {
            cLeft = -menuWidth;
        }
        Rect contentRect = new Rect(cLeft, 0, cLeft + contentWidth, contentHeight);
        layoutContentView(contentRect);
        Rect menuRect = new Rect(contentRect.right, 0, contentRect.right + menuWidth, contentRect.bottom);
        layoutMenuView(menuRect);
    }

    private void layoutMenuView(Rect rect) {
        mMenuView.layout(rect.left, rect.top, rect.right, rect.bottom);
    }

    private void layoutContentView(Rect rect) {
        mContentView.layout(rect.left, rect.top, rect.right, rect.bottom);
    }

    // 持续平滑动画 高频调用
    public void computeScroll() {
// 如果返回true，动画还需要继续
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    ;

    public void open() {
        open(true);
    }

    public void open(boolean isSmooth) {
        int finalLeft = -menuWidth;
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(mContentView, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutView(true);
        }
    }

    public void close() {
        close(true);
    }

    public void close(boolean isSmooth) {
        int finalLeft = 0;
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(mContentView, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutView(false);
        }
    }


    //根据当前状态判断回调事件
    protected void dispatchSwipeEvent() {
        Status preStatus = status;
        status = updateStatus();

        if (swipeChangeListener != null) {
            swipeChangeListener.onDraging(this);
        }

        if (preStatus != status && swipeChangeListener != null) {
            if (status == Status.CLOSE) {
                swipeChangeListener.onClose(this);
            } else if (status == Status.OPEN) {
                swipeChangeListener.onOpen(this);
            } else if (status == Status.DRAGING) {
                if (preStatus == Status.CLOSE) {
                    swipeChangeListener.onStartOpen(this);
                } else if (preStatus == Status.OPEN) {
                    swipeChangeListener.onStartClose(this);
                }
            }
        }
    }

    //更改状态
    private Status updateStatus() {
        int left = mContentView.getLeft();
        if (left == 0) {
            return Status.CLOSE;
        } else if (left == -menuWidth) {
            return Status.OPEN;
        }
        return Status.DRAGING;
    }

    public void setSwipeChangeListener(SwipeStatusChangeListener listener){
        this.swipeChangeListener = listener;
    }

}
