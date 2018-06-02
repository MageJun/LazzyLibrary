package com.android.common.lib.widget.recyclerview.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 抽象类，实现IItemView接口
 * 需要自定义的ItemView继承该类
 * @param <T>
 */
public abstract class BaseItemView<T> implements IItemView<T> {
    public BaseItemView(){

    }
    public abstract View getItemView(ViewGroup parent, int viewType);

    public abstract void onBindVH(@NonNull BaseViewHolder holder, int position, T data);


    @Override
    final public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getItemView(parent, viewType);
        BaseViewHolder  holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    final public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, T data) {
        if(holder instanceof  BaseViewHolder){
            BaseViewHolder  baseViewHolder = (BaseViewHolder) holder;
            onBindVH(baseViewHolder,position,data);
        }
    }

    @Override
    public boolean isForViewType(T t, int position) {
        return true;
    }
}
