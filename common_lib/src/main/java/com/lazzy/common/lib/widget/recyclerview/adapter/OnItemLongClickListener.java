package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.view.View;

public interface OnItemLongClickListener<T> {
    void onItemLongClick(View v, T t, int pos);
}
