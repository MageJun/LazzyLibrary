package com.lw.android.demo.view.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by zed on 2018/4/26.
 *
 * 自定义分割线样式，用来为RecyclerView的瀑布流实现，添加分割线
 *
 */

public class RecyclerViewStaggeredGridDivider extends RecyclerView.ItemDecoration {
    private final String TAG = RecyclerViewStaggeredGridDivider.class.getSimpleName();

    private int space;
    private int spanCount;

    public RecyclerViewStaggeredGridDivider(int spanCount){
        this.spanCount = spanCount;
    }

    public void setSpace(int space){
        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int posIndex = parent.getChildAdapterPosition(view);
        Log.i(TAG,"getItemOffsets index = "+posIndex);
        if(posIndex<spanCount){
            Log.i(TAG,"getItemOffsets outRect.top=space");
            outRect.top=2*space;
        }else{
            outRect.top = 2*space;
        }
//        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为space，右边距为space/2  （第二列反之）
         */
//        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

        //当前View在当前行所分配的索引位置，第一列为0，第二列为1.。依次类推
//        int spanIndex = params.getSpanIndex();
//        if(spanIndex==0){
//            outRect.left = space;
//            outRect.right = space/2;
//        }else if(spanIndex==(spanCount-1)){
//            outRect.left = space/2;
//            outRect.right = space;
//        }else{
//            outRect.left = space/2;
//            outRect.right = space/2;
//        }
//        super.getItemOffsets(outRect, view, parent, state);
    }
}
