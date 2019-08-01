package com.zed3.sipua.xydj.ui.group.adapter;

import android.view.View;

/**
 * Item的选中状态监听
 */
public interface OnItemCheckedListener<T> {

    void onItemCheckedChange(View view,int pos,boolean checked,T data);
}
