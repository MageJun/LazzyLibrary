package com.lazzy.common.lib.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GroupItemDecoration extends ItemDividerDecoration {

    List<? extends ItemDecorationData> mDatas = new ArrayList<>();
    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect

    private int mTitleHeight=30;//title的高
    private  int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private  int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private  float mTitleFontSize = 16;//title字体大小
    private boolean isStickTitle =false;//是否是粘性头部


    /**
     * @param context
     * @param direction {@link LinearLayout#HORIZONTAL}  or {@link LinearLayout#VERTICAL}
     */
    public GroupItemDecoration(Context context, int direction) {
        super(context, direction);
        init(context);
    }

    public void setTitleTextColor(int color){
        COLOR_TITLE_FONT = mContext.getResources().getColor(color);
    }

    public void setTitleBgColor(int color){
        COLOR_TITLE_BG = mContext.getResources().getColor(color);
    }

    public void setTitleTextSize(int size){
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, mContext.getResources().getDisplayMetrics());
    }

    public void setTitleHight(int size){
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, mContext.getResources().getDisplayMetrics());
    }

    public void setStickTitle(boolean isStickTitle){this.isStickTitle = isStickTitle;}


    private void init(Context context){
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }

    public void setData(List datas){
        mDatas.clear();
        mDatas.addAll(datas);
    }

    @Override
    protected boolean isOverrideOnDraw() {
        return true;
    }

    public interface ItemDecorationData{
        String getTag();
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            String tag = mDatas.get(position).getTag();
            if(TextUtils.isEmpty(tag)){
                super.getItemOffsets(outRect, view, parent, state);
                return ;
            }
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else {//其他的通过判断
                if (null != mDatas.get(position).getTag() && !mDatas.get(position).getTag().equals(mDatas.get(position - 1).getTag())) {
                    outRect.set(0, mTitleHeight, 0, 0);//不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                } else {
                    super.getItemOffsets(outRect, view, parent, state);
                    return ;
                }
            }
        }else{
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0;i<childCount;i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                String tag = mDatas.get(position).getTag();
                if(TextUtils.isEmpty(tag)){
                    continue;
                }
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                } else {//其他的通过判断
                    if (null != mDatas.get(position).getTag() && !mDatas.get(position).getTag().equals(mDatas.get(position - 1).getTag())) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(!isStickTitle){
            super.onDrawOver(c,parent,state);
        }else{
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int top = parent.getPaddingTop();
            final int childCount = parent.getChildCount();
            LinearLayoutManager lm = (LinearLayoutManager) parent.getLayoutManager();
            if(lm!=null){
              int pos =   lm.findFirstVisibleItemPosition();
              String tag = mDatas.get(pos).getTag();
              if(!TextUtils.isEmpty(tag)){
                  View child =parent.findViewHolderForAdapterPosition(pos).itemView;
                  //添加实现粘性TItle滚动切换效果的实现
                  boolean isReady2Change =false;
                  if(pos<mDatas.size()-1){
                      if(!tag.equals(mDatas.get(pos+1).getTag())){
                          if(child.getBottom()<=mTitleHeight){
                              isReady2Change = true;
                          }
                      }
                  }
                  mPaint.setColor(COLOR_TITLE_BG);
                  int pLeft = parent.getLeft();
                  int pTop = parent.getTop();
                  int pRight = parent.getRight();
                  int pBottom = parent.getBottom();
                  Rect bgRect = null;
                  if(!isReady2Change){
                      bgRect = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getTop()+mTitleHeight);
                  }else{
                      bgRect = new Rect(parent.getLeft(),child.getBottom()-mTitleHeight, parent.getRight(), child.getBottom());
                  }
                  c.drawRect(bgRect, mPaint);
                  mPaint.setColor(COLOR_TITLE_FONT);

                  mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
                  c.drawText(tag, child.getLeft()+child.getPaddingLeft(), bgRect.top + (mTitleHeight / 2 + mBounds.height() / 2), mPaint);
              }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);

        mPaint.getTextBounds(mDatas.get(position).getTag(), 0, mDatas.get(position).getTag().length(), mBounds);
        c.drawText(mDatas.get(position).getTag(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }
}
