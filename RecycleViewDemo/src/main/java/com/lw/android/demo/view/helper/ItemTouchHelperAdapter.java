package com.lw.android.demo.view.helper;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zed on 2018/4/26.
 */

public interface ItemTouchHelperAdapter {

    void onItemMoved(RecyclerView.ViewHolder from,RecyclerView.ViewHolder target);

    void onItemSwiped(RecyclerView.ViewHolder viewHolder);
}
