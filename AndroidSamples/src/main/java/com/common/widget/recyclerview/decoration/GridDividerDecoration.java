package com.common.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.widget.recyclerview.decoration.ItemDividerDecoration;

public class GridDividerDecoration extends ItemDividerDecoration {
    /**
     * @param context
     * @param direction {@link LinearLayout#HORIZONTAL}  or {@link LinearLayout#VERTICAL}
     */
    public GridDividerDecoration(Context context, int direction,boolean includeEdge) {
        super(context, direction);
        this.includeEdge = includeEdge;
    }

    private boolean includeEdge;//是否包含外围
    private int spanCount;//列数

    public void setSpanCount(int spanCount){
        this.spanCount = spanCount;
    }

    @Override
    protected boolean isOverrideOnDraw() {
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = offset - column * offset / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * offset / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = offset;
            }
            outRect.bottom = offset; // item bottom
        } else {
            outRect.left = column * offset / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = offset - (column + 1) * offset / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = offset; // item top
            }
        }
    }
}
