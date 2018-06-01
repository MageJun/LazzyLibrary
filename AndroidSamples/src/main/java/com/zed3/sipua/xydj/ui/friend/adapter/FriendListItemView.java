package com.zed3.sipua.xydj.ui.friend.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.widget.recyclerview.adapter.BaseItemView;
import com.common.widget.recyclerview.adapter.BaseViewHolder;
import com.common.widget.recyclerview.adapter.ItemViewManager;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.friend.bean.FrindInfo;

public class FriendListItemView extends BaseItemView<FrindInfo> {
    private final int itemType = ItemViewManager.obtainItemType();
    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_friend_list_item,parent,false);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, FrindInfo data) {
        holder.setText(R.id.tv_name,data.getName());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if("0".equals(data.getStatus())){
            holder.setBackGroupDrawable(R.id.stauts_view,R.drawable.xydj_group_member_status_offline);
        }else{
            holder.setBackGroupDrawable(R.id.stauts_view,R.drawable.xydj_group_member_status_online);
        }
    }

    @Override
    public int getItemViewType() {
        return itemType;
    }
}
