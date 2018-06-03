package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.view.View;

/**
 * RecyclerView点击事件
 * @param <T>
 */
public interface OnItemClickListener<T> {

    void onItemClick(View view,int pos,T data,Object obj);
    void onItemLongClick(View view,int pos,T data,Object obj);
}
