package com.zed3.sipua.xydj.ui;

import android.content.Context;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.common.widget.recyclerview.decoration.ItemDividerDecoration;

public class GridDividerDecoration2 extends ItemDividerDecoration {
    /**
     * @param context
     * @param direction {@link LinearLayout#HORIZONTAL}  or {@link LinearLayout#VERTICAL}
     */
    public GridDividerDecoration2(Context context, int direction,boolean includeEdge) {
        super(context, direction);
        this.includeEdge = includeEdge;
    }

    private boolean includeEdge;//是否包含外围
    private int spanCount;//列数
    private boolean isRanksSameOffset = true;//行列间隔是否相同，默认相同，用offset
    private int rowOffset;//行间距
    private int columnOffset;//列间距

    public void setSpanCount(int spanCount){
        this.spanCount = spanCount;
    }
    public void setRowOffset(int rowOffset){
        this.rowOffset = rowOffset;
    }
    public void setColumnOffset(int columnOffset){
        this.columnOffset = columnOffset;
    }
    public void setRanksSameOffset(boolean isSame){
        this.isRanksSameOffset = isSame;
    }

    @Override
    protected boolean isOverrideOnDraw() {
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect,view,parent,state);
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if(isRanksSameOffset){
            rowOffset = offset;
            columnOffset = offset;
        }else{
            if(rowOffset==0){
                rowOffset = offset;
            }
            if(columnOffset == 0){
                columnOffset = offset;
            }
        }
        if (includeEdge) {
            if(columnOffset!=0) {
                outRect.left = columnOffset - column * columnOffset / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * columnOffset / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            }
            if(rowOffset!=0) {
                if (position < spanCount) { // top edge
                    outRect.top = rowOffset;
                }
                outRect.bottom = rowOffset; // item bottom
            }
        } else {
            if(columnOffset!=0) {
                outRect.left = column * columnOffset / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = columnOffset - (column + 1) * columnOffset / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            }
            if(rowOffset!=0) {
                if (position >= spanCount) {
                    outRect.top = rowOffset; // item top
                }
            }
        }
    }
}
