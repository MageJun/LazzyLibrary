package com.zed3.sipua.xydj.ui.group.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberSearchResultAdapter extends RecyclerView.Adapter<GroupMemberSearchResultAdapter.SearchViewHolder> {

    private OnItemClickListener<CustomGroupMemberInfo> mItemClickListener;
    private List<CustomGroupMemberInfo> mData = new ArrayList<CustomGroupMemberInfo>();

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setData(List<CustomGroupMemberInfo> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_group_member_search_list_item,parent,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        view.setOnClickListener(mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        CustomGroupMemberInfo info = mData.get(position);
        holder.itemView.setTag(position);
        String name = info.getMemberName();
        String number = info.getMemberNum();
        holder.mNameTv.setText(name);
        holder.mNumberTv.setText(number);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if(getItemCount()>position){
                CustomGroupMemberInfo info = mData.get(position);
                if(mItemClickListener!=null){
                    mItemClickListener.onDataItemClick(v,position,info);
                }
            }
        }
    };

    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView mIcon;
        TextView mNameTv,mNumberTv;

        public SearchViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.head_icon);
            mNameTv = itemView.findViewById(R.id.tv_name);
            mNumberTv = itemView.findViewById(R.id.tv_number);
        }
    }
}
