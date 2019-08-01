package com.zed3.sipua.xydj.ui.groupinviteinfo.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lw.android.commonui.customView.DragLayout;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteSendDataMap;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteSendDataMap.GroupInviteSendData;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.OnItemClickListener;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class GroupInviteSendAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final String TAG = GroupInviteSendAdapter.class.getSimpleName();


    private List<GroupInviteSendData> mData = new ArrayList<GroupInviteSendData>();

    private List<DragLayout> mOpenLayouts = new ArrayList<DragLayout>();

    private OnItemClickListener<GroupInviteSendData> mListener;
    private OnItemLongClickListener<GroupInviteSendData> mItemLongClickListener;


    public void setListener(OnItemClickListener<GroupInviteSendData> listener){
        this.mListener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener<GroupInviteSendData> listener){
        this.mItemLongClickListener = listener;
    }


    public void setData(List<GroupInviteSendDataMap> mapData){
        mData.clear();

        for (int i = 0;i<mapData.size();i++){
            GroupInviteSendData data = new GroupInviteSendData();
            data.setMapName(mapData.get(i).getmMapName());
            data.setMap(true);

            mData.add(data);
            mData.addAll(mapData.get(i).getmDatas());
        }

        notifyDataSetChanged();
    }

    public void deleteData(int pos){
        mData.remove(pos);
        notifyItemRemoved(pos);
        if(pos<getItemCount()){
            notifyItemRangeChanged(pos,getItemCount()-pos,"remove");
        }

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_MAP:
                View mapView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_item_group_invite_map,parent,false);
                return  new InviteSendMapVH(mapView);
            case TYPE_DATA:

                View dataView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_item_group_invite_data_nodrag,parent,false);
                InviteSendDataVH vh = new InviteSendDataVH(dataView);

                return vh ;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        GroupInviteSendData data = mData.get(position);
        int type = holder.getItemViewType();

        switch (type){
            case TYPE_MAP:
                InviteSendMapVH mapVH = (InviteSendMapVH) holder;
                mapVH.mTV.setText(data.getMapName());
                break;
            case TYPE_DATA:
                final InviteSendDataVH dataVH = (InviteSendDataVH) holder;
                dataVH.itemView.setTag(position);

                dataVH.mNameTV.setText(data.getGroupName());
                dataVH.mInfoTV.setText(data.getInviteInfo());
                if(data.getStatus()== GroupInviteSendData.Status.ACCEPTED){
                    dataVH.mStatusTV.setVisibility(View.VISIBLE);
                    dataVH.mBtnAccept.setVisibility(View.GONE);
                    dataVH.mStatusTV.setText(R.string.xydj_status_accepted);
                }else{
                    dataVH.mStatusTV.setVisibility(View.GONE);
                    dataVH.mBtnAccept.setVisibility(View.VISIBLE);
                }

                dataVH.itemView.setClickable(true);
                dataVH.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.i(TAG,"onLongClick");
                        if(mItemLongClickListener!=null){
                            int pos = (int) v.getTag();
                            GroupInviteSendData data = mData.get(pos);
                            mItemLongClickListener.onItemLongClick(v,data,pos);
                            return true;
                        }
                        return false;
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private static final int TYPE_MAP = 0;
    private static final int TYPE_DATA = 1;
    @Override
    public int getItemViewType(int position) {
        GroupInviteSendData data = mData.get(position);
        if(data!=null){
            if(data.isMap()){
                return TYPE_MAP;
            }else{
                return TYPE_DATA;
            }
        }
        return super.getItemViewType(position);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.del:
                if(mListener!=null){
                    int pos = (int) v.getTag();
                    Log.i(TAG,"onClick pos = "+pos);
                    GroupInviteSendData data = mData.get(pos);
                    mListener.onItemClick(v, pos,data);
                }
                break;
        }
    }

    public static class InviteSendMapVH extends RecyclerView.ViewHolder{

        TextView mTV;

        public InviteSendMapVH(View itemView) {
            super(itemView);
            mTV = itemView.findViewById(R.id.textinfo);
        }
    }

    public static class InviteSendDataVH extends RecyclerView.ViewHolder{

        TextView mNameTV,mInfoTV,mStatusTV;
        Button mBtnAccept;
        View mStatusLayout;

        public InviteSendDataVH(View itemView) {
            super(itemView);
            mNameTV= itemView.findViewById(R.id.tv_name);
            mInfoTV= itemView.findViewById(R.id.tv_info);
            mStatusTV =itemView.findViewById(R.id.tv_status);
            mBtnAccept = itemView.findViewById(R.id.btn_accept);
            mStatusLayout = itemView.findViewById(R.id.layout_status);
        }
    }
}
