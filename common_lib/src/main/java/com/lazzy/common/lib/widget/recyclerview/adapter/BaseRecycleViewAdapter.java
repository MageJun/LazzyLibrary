package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter抽象类
 * @param <T>
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDatas = new ArrayList<>();//所有的数据
    protected ItemViewManager<T> mItemViewManager;

    public abstract void onCreateMulitTypeItemView();

    protected BaseRecycleViewAdapter(){
        mDatas = new ArrayList<>();
        mItemViewManager = new ItemViewManager<T>();
        onCreateMulitTypeItemView();
    }

    protected void addItemView(BaseItemView iItemView){
        mItemViewManager.addItemView(iItemView);
    }

    protected void removeItemView(BaseItemView iItemView){
        mItemViewManager.removeItemView(iItemView);
    }

    public void setData(List<T> data){
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener<T> listener){
        mItemViewManager.setItemClickListener(listener);
    }

    public void insertData(T data){
        int size = mDatas.size();
        mDatas.add(data);
        notifyItemChanged(size);
    }

    public void removeData(T data){
        int pos = mDatas.indexOf(data);
        if(pos!=-1){
            mDatas.remove(data);
            notifyItemRemoved(pos);
            if(pos!=mDatas.size())
                notifyItemRangeChanged(pos,mDatas.size()-pos);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return mItemViewManager.getItemView(viewType).onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        IItemView itemView = mItemViewManager.getItemView(viewType);
        if (itemView != null) {
            itemView.onBindViewHolder(holder, position, mDatas.get(position));
        }
    }

    @Override
   final public int getItemCount() {
        return mDatas.size();
    }

    @Override
   final public int getItemViewType(int position) {
        return mItemViewManager.getViewType(mDatas.get(position), position);
    }
}
