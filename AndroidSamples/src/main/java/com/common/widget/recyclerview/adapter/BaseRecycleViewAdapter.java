package com.common.widget.recyclerview.adapter;

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
    private List<T> mDatas = new ArrayList<>();//所有的数据
    private ItemViewManager<T> mItemViewManager;

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

    @NonNull
    @Override
   final public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return mItemViewManager.getItemView(viewType).onCreateViewHolder(parent,viewType);
    }

    @Override
   final public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
