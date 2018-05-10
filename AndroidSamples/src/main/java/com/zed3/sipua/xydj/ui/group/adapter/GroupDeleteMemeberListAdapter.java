package com.zed3.sipua.xydj.ui.group.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupDeleteMemeberListAdapter extends RecyclerView.Adapter<GroupDeleteMemeberListAdapter.ItemVH> {

    private List<CustomGroupMemberInfo> mData = new ArrayList<CustomGroupMemberInfo>();
    private Context mContext;
    private OnItemClickListener<CustomGroupMemberInfo> mListener;
    private OnItemCheckedListener<CustomGroupMemberInfo> mOnItemCheckedListener;
    private Map<Integer,Boolean> mCheckStatusMap = new HashMap<Integer,Boolean>();

    public GroupDeleteMemeberListAdapter(Context context){
        this.mContext = context;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener= listener;
    }
    public void setOnItemCheckedListener(OnItemCheckedListener listener){this.mOnItemCheckedListener = listener;};

    public void setData(List<CustomGroupMemberInfo> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_group_delete_member_list_item,parent,false);
            ItemVH vh = new ItemVH(view);
            view.setOnClickListener(mClickListener);
         return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {
        CustomGroupMemberInfo info = mData.get(position);
        holder.itemView.setTag(position);
        holder.mName.setText(info.getMemberName());
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(mCheckListenr);
        holder.mStatus.setVisibility(View.VISIBLE);
        if(mCheckStatusMap.containsKey(position))
            holder.mCheckBox.setChecked(mCheckStatusMap.get(position));
        else
            holder.mCheckBox.setChecked(false);

        if ("0".equals(info.getMemberStatus())) {
            holder.mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.xydj_group_member_status_offline));
        } else {
            holder.mStatus.setBackground(mContext.getResources().getDrawable(R.drawable.xydj_group_member_status_online));
        }
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

    private CompoundButton.OnCheckedChangeListener mCheckListenr = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int postion = (int) buttonView.getTag();
            mCheckStatusMap.put(postion,isChecked);
            if(mOnItemCheckedListener!=null){
                CustomGroupMemberInfo info = mData.get(postion);
                mOnItemCheckedListener.onItemCheckedChange(buttonView,postion,isChecked,info);
            }
        }
    };

    public static class ItemVH extends RecyclerView.ViewHolder{

        ImageView mIcon;
        TextView mName;
        View mStatus;
        CheckBox mCheckBox;
        public ItemVH(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.head_icon_img);
            mName = itemView.findViewById(R.id.member_name_tv);
            mStatus = itemView.findViewById(R.id.stauts_view);
            mCheckBox = itemView.findViewById(R.id.del_check);
        }



    }
}
