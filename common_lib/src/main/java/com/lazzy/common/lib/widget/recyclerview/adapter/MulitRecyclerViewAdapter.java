package com.lazzy.common.lib.widget.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * 多选Adapter，回调每个ItemView的状态
 * @param <T>
 */
public abstract  class MulitRecyclerViewAdapter<T> extends BaseRecycleViewAdapter {
    private SparseBooleanArray mDataStatusMap = new SparseBooleanArray();
    private OnItemSelectChangedListener mItemSelectListener;

    public void setItemSelectChangeListener(OnItemSelectChangedListener listener){
        this.mItemSelectListener = listener;
    }
    public void removeSelectElement(T data){
        if(mDatas.contains(data)){
            int pos = mDatas.indexOf(data);
            mDataStatusMap.delete(pos);
            notifyDataSetChanged();
        }
    }
    public void addSelectElement(T data){
        if(mDatas.contains(data)){
            int pos = mDatas.indexOf(data);
            mDataStatusMap.put(pos,true);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        IItemView itemView = mItemViewManager.getItemView(viewType);
        if (itemView != null && itemView instanceof MulitItemView) {
            MulitItemView itemView1 = (MulitItemView) itemView;
            //检测当前Item是否可选，不可选不做任何处理
            itemView1.onMulitItemViewBindVH((BaseViewHolder)holder,position,mDatas.get(position),mDataStatusMap.get(position,false));
            if(!itemView1.onItemSelectAvailable((BaseViewHolder)holder,position,mDatas.get(position))){
                return ;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean status = !mDataStatusMap.get(position,false);
                    mDataStatusMap.put(position,status);
                    if(mItemSelectListener!=null){
                        mItemSelectListener.onItemSelected(holder.itemView,position,mDatas.get(position),status);
                    }
                    notifyItemChanged(position);
                }
            });
        }
    }

    public interface onItemSelectChangedListener<T>{
        void onItemSelectChanged(int position, boolean checked, T data);
    }
}
