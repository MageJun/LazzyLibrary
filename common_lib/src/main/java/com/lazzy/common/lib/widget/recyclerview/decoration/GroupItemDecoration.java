package com.lazzy.common.lib.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.lazzy.common.lib.widget.recyclerview.helper.ILetterData;

import java.util.ArrayList;
import java.util.List;

public class GroupItemDecoration extends ItemDividerDecoration {

    List<? extends ILetterData> mDatas = new ArrayList<>();
    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect

    private int mTitleHeight=30;//title的高
    private  int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private  int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private  float mTitleFontSize = 16;//title字体大小
    private boolean isStickTitle =false;//是否是粘性头部
    private boolean isFullWidthTitleBg = false;//是否不计算width padding，显示title


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

    public void setFullWidthTitleBg(boolean isFull){this.isFullWidthTitleBg = isFull;}


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

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            String tag = mDatas.get(position).getLetter();
            if(TextUtils.isEmpty(tag)){
                super.getItemOffsets(outRect, view, parent, state);
                return ;
            }
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else {//其他的通过判断
                if (null != mDatas.get(position).getLetter() && !mDatas.get(position).getLetter().equals(mDatas.get(position - 1).getLetter())) {
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

        final int left = isFullWidthTitleBg?0: parent.getPaddingLeft();
        final int right =isFullWidthTitleBg?parent.getWidth(): parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0;i<childCount;i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                String tag = mDatas.get(position).getLetter();
                if(TextUtils.isEmpty(tag)){
                    continue;
                }
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                } else {//其他的通过判断
                    if (null != mDatas.get(position).getLetter() && !mDatas.get(position).getLetter().equals(mDatas.get(position - 1).getLetter())) {
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
            LinearLayoutManager lm = (LinearLayoutManager) parent.getLayoutManager();
            if(lm!=null){
              int pos =   lm.findFirstVisibleItemPosition();
              if(pos<0||pos>mDatas.size()){
                  super.onDrawOver(c,parent,state);
                  return ;
              }
              String tag = mDatas.get(pos).getLetter();
              if(!TextUtils.isEmpty(tag)){
                  View child =parent.findViewHolderForAdapterPosition(pos).itemView;
                  //添加实现粘性TItle滚动切换效果的实现
                  boolean isReady2Change =false;
                  if(pos<mDatas.size()-1){
                      if(!tag.equals(mDatas.get(pos+1).getLetter())){
                          if(child.getBottom()<=mTitleHeight){
                              isReady2Change = true;
                          }
                      }
                  }
                  mPaint.setColor(COLOR_TITLE_BG);
                  int pLeft =isFullWidthTitleBg?0: parent.getPaddingLeft();
                  int pTop = parent.getPaddingTop();
                  int pRight =isFullWidthTitleBg?0: parent.getPaddingRight();
                  int pBottom = parent.getPaddingBottom();

                  mPaint.setColor(COLOR_TITLE_BG);
                  Rect bgRect = null;
                  if(!isReady2Change){
                      bgRect = new Rect(pLeft, pTop, parent.getMeasuredWidth()-pRight, pTop+mTitleHeight);
                  }else{
                      bgRect = new Rect(pLeft,child.getBottom()-mTitleHeight,parent.getMeasuredWidth()-pRight, child.getBottom());
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
        Rect rect = new Rect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin);
        c.drawRect(rect, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);

        mPaint.getTextBounds(mDatas.get(position).getLetter(), 0, mDatas.get(position).getLetter().length(), mBounds);
        c.drawText(mDatas.get(position).getLetter(),child.getLeft()+ child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }
}
