package com.zed3.sipua.xydj.ui.group.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;

import com.zed3.sipua.xydj.ui.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.group.adapter.GroupMemeberListAdapter;

public class GroupMemberListDecoration extends ItemDividerDecoration {
    /**
     * @param context
     * @param direction {@link LinearLayout#HORIZONTAL}  or {@link LinearLayout#VERTICAL}
     */
    public GroupMemberListDecoration(Context context, int direction) {
        super(context, direction);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

//        Adapter ad = (GroupMemeberListAdapter) parent.getAdapter();
//        if(ad!=null && ad instanceof  GroupMemeberListAdapter){
//            GroupMemeberListAdapter adapter = (GroupMemeberListAdapter) ad;
//            int count = parent.getChildCount();
//            for (int i = 0;i<count;i++){
//                int type = adapter.getItemViewType(i);
//                if(type == GroupMemeberListAdapter.TYPE_DATA){
//
//                }
//            }
//        }
    }
}
