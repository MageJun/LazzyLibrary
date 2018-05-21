package com.zed3.sipua.xydj.ui.group.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;

import java.util.ArrayList;
import java.util.List;

public class FriendShareGroupListAdapter extends RecyclerView.Adapter<FriendShareGroupListAdapter.FriendViewHolder> {

    private List<PttCustomGrp> mData = new ArrayList<PttCustomGrp>();

    public void setData(List<PttCustomGrp> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_group_member_detail_list_item_nor,parent,false);
        FriendViewHolder vh = new FriendViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        PttCustomGrp grp = mData.get(position);
        holder.mName.setText(grp.getGroupName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{
        ImageView mIcon;
        TextView mName;
        public FriendViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_name);
            mIcon = itemView.findViewById(R.id.head_icon);
        }
    }

}
