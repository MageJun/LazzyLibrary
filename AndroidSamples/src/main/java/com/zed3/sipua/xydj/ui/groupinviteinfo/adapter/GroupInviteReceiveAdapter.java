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
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap.GroupInviteReceiveData;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GroupInviteReceiveAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final String TAG = GroupInviteReceiveAdapter.class.getSimpleName();

    private List<GroupInviteReceiveData> mData = new ArrayList<GroupInviteReceiveData>();

    private List<DragLayout> mOpenLayouts = new ArrayList<DragLayout>();

    private OnItemClickListener<GroupInviteReceiveData> mListener;

    public void setListener(OnItemClickListener<GroupInviteReceiveData> listener){
        this.mListener = listener;
    }


    public void setData(List<GroupInviteReceiveDataMap> mapData){
        mData.clear();

        for (int i = 0;i<mapData.size();i++){
            GroupInviteReceiveData data = new GroupInviteReceiveData();
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
                return  new InviteReceiveMapVH(mapView);
            case TYPE_DATA:

                View dataView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_item_group_invite_data,parent,false);
                InviteReceiveDataVH vh = new InviteReceiveDataVH(dataView);
                vh.mLayout.setSwipeAvailable(true);
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
                        InviteReceiveDataVH holder= (InviteReceiveDataVH) dragLayout.getTag();
                        Log.i(TAG,"onClose holder = "+holder);
                        if(holder!=null){
                            holder.mStatusLayout.setVisibility(View.VISIBLE);
                        }
                        mOpenLayouts.remove(dragLayout);
                    }

                    @Override
                    public void onStartOpen(DragLayout dragLayout) {
                        InviteReceiveDataVH holder= (InviteReceiveDataVH) dragLayout.getTag();
                        Log.i(TAG,"onStartOpen holder = "+holder);
                        if(holder!=null){
                            holder.mStatusLayout.setVisibility(View.INVISIBLE);
                        }
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

        GroupInviteReceiveData data = mData.get(position);
        int type = holder.getItemViewType();

        switch (type){
            case TYPE_MAP:
                InviteReceiveMapVH mapVH = (InviteReceiveMapVH) holder;
                mapVH.mTV.setText(data.getMapName());
                break;
            case TYPE_DATA:
                InviteReceiveDataVH dataVH = (InviteReceiveDataVH) holder;
                dataVH.mDel.setTag(position);
                dataVH.mDel.setOnClickListener(this);

                dataVH.mNameTV.setText(data.getGroupName());
                dataVH.mInfoTV.setText(data.getInviteInfo());
                if(data.getStatus()== GroupInviteReceiveData.Status.ACCEPTED){
                    dataVH.mStatusTV.setVisibility(View.VISIBLE);
                    dataVH.mBtnAccept.setVisibility(View.GONE);
                    dataVH.mStatusTV.setText(R.string.xydj_status_accepted);
                }else{
                    dataVH.mStatusTV.setVisibility(View.GONE);
                    dataVH.mBtnAccept.setVisibility(View.VISIBLE);
                }
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



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.del:
                if(mListener!=null){
                    int pos = (int) v.getTag();
                    Log.i(TAG,"onClick pos = "+pos);
                    GroupInviteReceiveData data = mData.get(pos);
                    mListener.onItemClick(v, pos,data);
                }
                break;
        }
    }

    public static class InviteReceiveMapVH extends RecyclerView.ViewHolder{

        TextView mTV;

        public InviteReceiveMapVH(View itemView) {
            super(itemView);
            mTV = itemView.findViewById(R.id.textinfo);
        }
    }

    public static class InviteReceiveDataVH extends RecyclerView.ViewHolder{

        TextView mNameTV,mInfoTV,mStatusTV,mDel;
        DragLayout mLayout;
        Button mBtnAccept;
        View mStatusLayout;

        public InviteReceiveDataVH(View itemView) {
            super(itemView);
            mLayout = (DragLayout) itemView;
            mNameTV= itemView.findViewById(R.id.tv_name);
            mInfoTV= itemView.findViewById(R.id.tv_info);
            mStatusTV =itemView.findViewById(R.id.tv_status);
            mBtnAccept = itemView.findViewById(R.id.btn_accept);
            mStatusLayout = itemView.findViewById(R.id.layout_status);
            mDel = itemView.findViewById(R.id.del);
        }
    }
}
