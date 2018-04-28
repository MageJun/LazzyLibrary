package com.lw.android.commonui.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lw.android.commonui.R;

/**
 * Created by zed on 2018/4/27.
 *
 * 可以试着把控件想象成一个摄像头，控件里的内容物就是摄像头可以看到的景象，
 * 当使用scrollto和scrollby方法时，都会产生偏移量，
 * 偏移量的正负和数值，就表示摄像头的移动方向和移动到的坐标点或者移动了的坐标点。
 * 最终你看到的现象就是控件里的内容物发生与你定义的数值相反的移动轨迹。
 * 这个问题主要看你选择的参考物来判断这个方法到底是什么含义了
 */

public class SwipeMenuLayout extends RelativeLayout {

    private static final String TAG = SwipeMenuLayout.class.getSimpleName();

    private float mLeftMenuWeight = 0.3f;
    private float mRightMenuWeight= 0.3f;
    private Context mContext;
    private LayoutInflater mInflater ;

    private int right_menu_id=-1;
    private int left_menu_id=-1;
    private int content_id=-1;


    private ViewGroup content;//内容显示区

    private ViewGroup rightMenu;//右菜单显示区

    private ViewGroup leftMenu;//左菜单显示区

    private boolean isShowLeftMenu;//是否显示左菜单，默认不显示

    private boolean isSHowRightMenu = true;//是否显示右菜单，默认显示

    private int  rightMenuWidth,leftMenuWidth;
    private int width;


    public SwipeMenuLayout(Context context) {
        super(context);
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }

    public SwipeMenuLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.SwipeMenuLayout);

        int typeCount = typedArray.getIndexCount();
        for (int i = 0;i<typeCount;i++){
            int attr = typedArray.getIndex(i);

            if(R.styleable.SwipeMenuLayout_layout_right_menu == attr){
                right_menu_id= typedArray.getResourceId(attr,-1);
            }else if(R.styleable.SwipeMenuLayout_layout_left_menu == attr){
                left_menu_id = typedArray.getResourceId(attr,-1);
            }else if(R.styleable.SwipeMenuLayout_layout_content == attr){
                content_id = typedArray.getResourceId(attr,-1);
            }else if(R.styleable.SwipeMenuLayout_isShowLeftMenu == attr){
                isShowLeftMenu = typedArray.getBoolean(attr,isShowLeftMenu);
            }else if(R.styleable.SwipeMenuLayout_isShowRightMenu == attr){
                isSHowRightMenu = typedArray.getBoolean(attr,isSHowRightMenu);
            }
        }

        typedArray.recycle();
        init();
    }

    public SwipeMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        init();
    }

    private void init() {
        Log.i(TAG,"init()");


        content = new LinearLayout(mContext);
        LayoutParams contentLP = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        content.setLayoutParams(contentLP);
        content.setId(View.generateViewId());
        content.setBackgroundColor(getResources().getColor(R.color.commonui_color_item1));
        Log.i(TAG,"init contentID = "+content.getId());

        rightMenu = new LinearLayout(mContext);
        RelativeLayout.LayoutParams rMenuLP = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        rightMenu.setLayoutParams(rMenuLP);
        rMenuLP.addRule(RelativeLayout.RIGHT_OF,content.getId());
        rightMenu.setId(View.generateViewId());
        rightMenu.setBackgroundColor(getResources().getColor(R.color.commonui_color_item2));
        Log.i(TAG,"init rightMenu = "+rightMenu.getId());

        leftMenu = new LinearLayout(mContext);
        RelativeLayout.LayoutParams lMenuLP = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lMenuLP.addRule(RelativeLayout.LEFT_OF,content.getId());
        leftMenu.setLayoutParams(lMenuLP);
        leftMenu.setId(View.generateViewId());
        leftMenu.setBackgroundColor(Color.BLUE);
        Log.i(TAG,"init leftMenu = "+leftMenu.getId());

        if(content_id!=-1){
            View view = mInflater.inflate(content_id,null);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            content.addView(view);
        }

        if(right_menu_id!=-1){
            View view = mInflater.inflate(right_menu_id,null);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            rightMenu.addView(view);
        }
        if(left_menu_id!=-1){
            View view = mInflater.inflate(left_menu_id,null);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
            leftMenu.addView(view);
        }

        addView(leftMenu,0);
        addView(content,1);
        addView(rightMenu,2);

        setBackgroundColor(Color.CYAN);

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = MeasureSpec.getSize(widthMeasureSpec);
        rightMenuWidth = (int)(width*mRightMenuWeight);
        leftMenuWidth = (int)(width*mLeftMenuWeight);

        Log.i(TAG,"onMeasure width = "+width+",rightMenuWidth = "+rightMenuWidth);

        rightMenu.getLayoutParams().width = rightMenuWidth;
        leftMenu.getLayoutParams().width = -leftMenuWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private ScrollInfo sInfo = new ScrollInfo();
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                sInfo.clear();
                sInfo.downX = event.getX();
                sInfo.downY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX()-sInfo.downX;
                float dy = event.getY()-sInfo.downY;

                if(dx<0){
                    sInfo.scrollDirection=ScrollInfo.DIRECTION_LEFT;
                }else{
                    sInfo.scrollDirection=ScrollInfo.DIRECTION_RIGHT;
                }


                sInfo.scrollX = Math.abs(dx);
                sInfo.scrollY = Math.abs(dy);
                Log.i(TAG,"onTouchEvent sInfo.scrollX = "+sInfo.scrollX+", rightMenuWidth = "+rightMenuWidth);
                if(sInfo.scrollX<rightMenuWidth){
                    move(sInfo.scrollDirection,(int)sInfo.scrollX);
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                if(sInfo.scrollX<rightMenuWidth/2){
                    recovery();
                }else{
                    openMenu(sInfo.scrollDirection);
                }
                sInfo.clear();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private void move(int direction,int dx){
        if(direction==ScrollInfo.DIRECTION_LEFT){
            scrollTo(dx,0);
        }else{
            scrollTo(-dx,0);
        }
    }


    /**
     * 恢复原位，滚动回初始位置
     */
    private void recovery(){
        scrollTo(0,0);

    }

    /**
     * 展开菜单
     */
    private void openMenu(int direction){
        move(direction,rightMenuWidth);
    }


    private static class ScrollInfo{
        float downX;
        float downY;

        float scrollX;
        float scrollY;
        public static final int DIRECTION_LEFT = 1;
        public static final int DIRECTION_RIGHT = 2;
        int scrollDirection;

        public void clear(){
            this.downX = 0;
            this.downY = 0;
            this.scrollX = 0;
            this.scrollY = 0;
        }

    }
}
