package com.zed3.sipua.xydj.ui.group.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;

import java.util.ArrayList;
import java.util.List;

public class GroupDeleteSelectMemeberListAdapter extends RecyclerView.Adapter<GroupDeleteSelectMemeberListAdapter.ItemVH> {

    private List<CustomGroupMemberInfo> mData = new ArrayList<CustomGroupMemberInfo>();
    private Context mContext;
    private OnItemClickListener<CustomGroupMemberInfo> mListener;

    public GroupDeleteSelectMemeberListAdapter(Context context){
        this.mContext = context;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener= listener;
    }

    public void setData(List<CustomGroupMemberInfo> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void insertData(CustomGroupMemberInfo data){
        int size = mData.size();
        mData.add(size,data);
        notifyItemInserted(size);
    }

    public void removeData(CustomGroupMemberInfo data){
        int pos = mData.indexOf(data);
        if(pos!=-1){
            mData.remove(pos);
            notifyItemRemoved(pos);
            if(pos<getItemCount()){
                notifyItemChanged(pos,getItemCount()-pos);
            }
        }
    }

    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_group_member_select_list_item,parent,false);
            ItemVH vh = new ItemVH(view);
            view.setOnClickListener(mClickListener);
         return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {
        CustomGroupMemberInfo info = mData.get(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mListener!=null){
                int postion = (int) v.getTag();
                mListener.onItemClick(v,postion);
                CustomGroupMemberInfo info = mData.get(postion);
                mListener.onDataItemClick(v,postion,info);
            }
        }
    };


    public static class ItemVH extends RecyclerView.ViewHolder{

        ImageView mIcon;
        View mDel;
        public ItemVH(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.head_icon_img);
            mDel = itemView.findViewById(R.id.stauts_view);
        }



    }
}
