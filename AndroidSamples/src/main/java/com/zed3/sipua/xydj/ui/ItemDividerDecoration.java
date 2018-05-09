package com.zed3.sipua.xydj.ui;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class ItemDividerDecoration extends RecyclerView.ItemDecoration {


    private static final String TAG = ItemDividerDecoration.class.getSimpleName();
    private Context mContext;

    /**
     *
     * @param context
     * @param direction {@link LinearLayout#HORIZONTAL}  or {@link LinearLayout#VERTICAL}
     */
    public ItemDividerDecoration(Context context,int direction){
        mContext = context;
        this.direction = direction;
    }

    protected int offset;//偏移量
    protected int color;//分割线颜色

    protected int direction = LinearLayout.VERTICAL;//方向


    protected Drawable mDrawble;//分割线背景图片

    public void setOffset(int offset){
        this.offset = offset;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setmDrawble(Drawable drawable){
        if(drawable==null){
            throw  new IllegalArgumentException("drawable may not be null!");
        }
        this.mDrawble = drawable;
    }

    public void setDirection(int direction){
        if(direction!=LinearLayout.HORIZONTAL&&direction!=LinearLayout.VERTICAL){
            throw new IllegalArgumentException("direction unknown!");
        }
        this.direction = direction;
    }


    protected boolean isOverrideOnDraw(){
        return true;
    }

    /**
     * 可以在RecyclerView 的itemView的下方画内容
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(!isOverrideOnDraw()){
            return ;
        }

        int childCount = parent.getChildCount();

        for(int i = 0;i<childCount;i++){
            if(i ==0){
                continue;
            }
            if(i==childCount-1){
                continue;
            }
            View child = parent.getChildAt(i);

            Rect rect = drawOutRect(child,parent);
            Paint paint = new Paint();
            paint.setColor(this.color);
            c.drawRect(rect,paint);
        }
    }

    private Rect drawOutRect(View child,RecyclerView parent) {
        Rect tmpRect = new Rect();
        switch (direction) {
            case LinearLayout.VERTICAL:
                //垂直方向，除了第一个ItemView，其它的ItemView画自己和上一个ItemView之间的部分
                //顶部：当前ItemView的顶部，减去间隔距离offset
                //左边：从父布局的左边paddingLeft开始
                //下边：当前ItemView的顶部
                //右边：当前父布局宽度，减去paddingRight开始
                tmpRect.left = parent.getPaddingLeft();
                tmpRect.top = child.getTop()-offset;
                tmpRect.right = parent.getWidth()-parent.getPaddingRight();
                tmpRect.bottom = child.getTop();

                break;
            case LinearLayout.HORIZONTAL:
                //水平方向，除了第一个ItemView，其它的ItemView画自己和上一个ItemView之间的部分
                //顶部：从父布局的左边paddingTop开始
                //左边：当前ItemView的左边，减去间隔距离offset
                //下边：父布局高度，减去paddingBottom开始
                //右边：当前ItemView的左边
                tmpRect.left = child.getLeft()-offset;
                tmpRect.top = parent.getPaddingTop();
                tmpRect.right = child.getLeft();
                tmpRect.bottom = parent.getHeight()-parent.getPaddingBottom();

                break;
        }

        return tmpRect;
    }

    /**
     * 可以在RecyclerView 的itemView的上方画内容
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.i(TAG,"onDrawOver");
        super.onDrawOver(c, parent, state);

    }

    /**
     * 设置每个itemView四边的偏移量，
     * 可以想象在每个ItemView的外围包裹了一个OutRect，
     * 而这个outRect的位置偏移量就是在这个方法中设置
     *
     * 通过方法查看可以发现，OutRect保存的l/t/r/b四个数字，在绘制ItemView的时候，用来确认相应方向的偏移量，例如：
     *
     * insets.left += mTempRect.left;
     *  insets.top += mTempRect.top;
     *
     * @param outRect  是一个全为 0 的 Rect
     * @param view itemView
     * @param parent RecyclerView本身
     * @param state 状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);

        switch (direction) {
            case LinearLayout.VERTICAL:
                if(parent.getChildAdapterPosition(view)==0){
                    return ;
                }
                outRect.top = offset;

                break;
            case LinearLayout.HORIZONTAL:
                if(parent.getChildAdapterPosition(view)==0){
                    return ;
                }
                outRect.left = offset;
                break;
        }
    }
}
