package com.zed3.sipua.xydj.ui.group.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnItemSelectedListener<T> {

    void onItemSelected(View view, RecyclerView parent,int pos,int type,T data);

    void onNothingSelected(View view);
}
