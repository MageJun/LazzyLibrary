package com.lw.android.demo.view.helper;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

/**
 * Created by zed on 2018/4/26.
 */

public class CustomItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter mAdapter;

    public CustomItemTouchHelperCallBack(){
    }
    public CustomItemTouchHelperCallBack(ItemTouchHelperAdapter adapter){
        this.mAdapter = adapter;
    }

    public void setItemTouchHelperAdapter(ItemTouchHelperAdapter adapter){
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.DOWN|ItemTouchHelper.UP|ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT,
                    ItemTouchHelper.LEFT);
//        return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT) |
//                makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT /*| ItemTouchHelper.RIGHT*/);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMoved(viewHolder,target);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemSwiped(viewHolder);
    }
}
