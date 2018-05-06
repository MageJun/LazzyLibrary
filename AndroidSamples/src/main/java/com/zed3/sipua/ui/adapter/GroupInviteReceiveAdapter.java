package com.zed3.sipua.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lw.android.commonui.customView.DragLayout;
import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.ui.bean.GroupInviteReceiveDataMap;

import java.util.ArrayList;
import java.util.List;

public class GroupInviteReceiveAdapter extends RecyclerView.Adapter {

    private List<GroupInviteReceiveDataMap.GroupInviteReceiveData> mData = new ArrayList<GroupInviteReceiveDataMap.GroupInviteReceiveData>();

    private List<DragLayout> mOpenLayouts = new ArrayList<DragLayout>();

    public void setData(List<GroupInviteReceiveDataMap> mapData){
        mData.clear();

        for (int i = 0;i<mapData.size();i++){
            GroupInviteReceiveDataMap.GroupInviteReceiveData data = new GroupInviteReceiveDataMap.GroupInviteReceiveData();
            data.setMapName(mapData.get(i).getmMapName());
            data.setMap(true);

            mData.add(data);
            mData.addAll(mapData.get(i).getmDatas());
        }

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_MAP:
                View mapView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_item_group_invite_map,parent,false);
                return  new InviteReceiveMapVH(mapView);
            case TYPE_DATA:

                View dataView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_item_group_invite_data,parent,false);
                InviteReceiveDataVH vh = new InviteReceiveDataVH(dataView);
                vh.mLayout.setSwipeChangeListener(new DragLayout.SwipeStatusChangeListener() {
                    @Override
                    public void onDraging(DragLayout dragLayout) {

                    }

                    @Override
                    public void onOpen(DragLayout dragLayout) {
                        mOpenLayouts.add(dragLayout);
                    }

                    @Override
                    public void onClose(DragLayout dragLayout) {
                        mOpenLayouts.remove(dragLayout);
                    }

                    @Override
                    public void onStartOpen(DragLayout dragLayout) {
                        for (DragLayout layout:mOpenLayouts) {
                            layout.close();
                        }
                        mOpenLayouts.clear();
                    }

                    @Override
                    public void onStartClose(DragLayout dragLayout) {

                    }
                });
                return vh ;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        GroupInviteReceiveDataMap.GroupInviteReceiveData data = mData.get(position);
        int type = holder.getItemViewType();

        switch (type){
            case TYPE_MAP:
                InviteReceiveMapVH mapVH = (InviteReceiveMapVH) holder;
                mapVH.mTV.setText(data.getMapName());
                break;
            case TYPE_DATA:
                InviteReceiveDataVH dataVH = (InviteReceiveDataVH) holder;
                dataVH.mNameTV.setText(data.getGroupName());
                dataVH.mInfoTV.setText(data.getInviteInfo());
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
        GroupInviteReceiveDataMap.GroupInviteReceiveData data = mData.get(position);
        if(data!=null){
            if(data.isMap()){
                return TYPE_MAP;
            }else{
                return TYPE_DATA;
            }
        }
        return super.getItemViewType(position);
    }

    public static class InviteReceiveMapVH extends RecyclerView.ViewHolder{

        TextView mTV;

        public InviteReceiveMapVH(View itemView) {
            super(itemView);
            mTV = itemView.findViewById(R.id.textinfo);
        }
    }

    public static class InviteReceiveDataVH extends RecyclerView.ViewHolder{

        TextView mNameTV,mInfoTV;
        DragLayout mLayout;

        public InviteReceiveDataVH(View itemView) {
            super(itemView);
            mLayout = (DragLayout) itemView;
            mNameTV= itemView.findViewById(R.id.tv_name);
            mInfoTV= itemView.findViewById(R.id.tv_info);
        }
    }
}
