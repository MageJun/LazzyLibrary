package com.zed3.sipua.xydj.ui.group.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.helper.GroupViewer;

import java.util.ArrayList;
import java.util.List;

public class GroupMemeberListAdapter extends RecyclerView.Adapter<GroupMemeberListAdapter.ItemVH> {

    public static final int TYPE_DATA = 1;//普通数据
    public static final int TYPE_ADD_SYMBOL = 2;//加号
    public static final int TYPE_SUBTRATION_SNMBOL = 3;//减号
    private List<CustomGroupMemberInfo> mData = new ArrayList<CustomGroupMemberInfo>();
    private Context mContext;
    private OnItemClickListener<CustomGroupMemberInfo> mListener;

    public interface OnItemClickListener<T>{
        void onItemClick(View v,int pos);
        void onDataItemClick(View v,int pos,T t);
        void onAddItemClick();
        void onSubTraction();
    }

    public GroupMemeberListAdapter(Context context){
        this.mContext = context;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener= listener;
    }
    public void setData(List<CustomGroupMemberInfo> data){
        mData.clear();
        mData.addAll(data);
        mData.add(new CustomGroupMemberInfo());
        mData.add(new CustomGroupMemberInfo());
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_group_member_list_item,parent,false);
            ItemVH vh = new ItemVH(view);
            view.setOnClickListener(mClickListener);
         return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {
        int type = holder.getItemViewType();
        CustomGroupMemberInfo info = mData.get(position);
        holder.mStatus.setVisibility(View.INVISIBLE);
        holder.mName.setVisibility(View.INVISIBLE);
        holder.itemView.setTag(position);
        switch(type){
            case TYPE_DATA:
                holder.mName.setVisibility(View.VISIBLE);
                holder.mName.setText(info.getMemberName());
                holder.mStatus.setVisibility(View.VISIBLE);
                if("0".equals(info.getMemberStatus())){
                    holder.mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.xydj_group_member_status_offline));
                }else{
                    holder.mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.xydj_group_member_status_online));
                }
                break;
            case TYPE_ADD_SYMBOL:
                holder.mIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xydj_group_member_item_add));
                break;
            case TYPE_SUBTRATION_SNMBOL:
                holder.mIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xydj_group_member_item_sub));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-2){
            return TYPE_ADD_SYMBOL;
        }else if(position==getItemCount()-1){
            return TYPE_SUBTRATION_SNMBOL;
        }else{
            return TYPE_DATA;
        }
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mListener!=null){
                int postion = (int) v.getTag();
                int viewType = getItemViewType(postion);
                mListener.onItemClick(v,postion);
                switch(viewType){
                    case TYPE_ADD_SYMBOL:
                        mListener.onAddItemClick();
                        break;
                    case TYPE_SUBTRATION_SNMBOL:
                        mListener.onSubTraction();
                        break;
                    case TYPE_DATA:
                        CustomGroupMemberInfo info = mData.get(postion);
                        mListener.onDataItemClick(v,postion,info);
                        break;
                }
            }
        }
    };

    public static class ItemVH extends RecyclerView.ViewHolder{

        ImageView mIcon;
        TextView mName;
        View mStatus;
        public ItemVH(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.head_icon_img);
            mName = itemView.findViewById(R.id.member_name_tv);
            mStatus = itemView.findViewById(R.id.stauts_view);
        }



    }
}
