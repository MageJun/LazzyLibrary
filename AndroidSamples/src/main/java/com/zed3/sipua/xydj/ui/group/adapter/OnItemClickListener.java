package com.zed3.sipua.xydj.ui.group.adapter;

import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(View v, int pos);
    void onDataItemClick(View v,int pos,T t);
    void onAddItemClick();
    void onSubTraction();
}
