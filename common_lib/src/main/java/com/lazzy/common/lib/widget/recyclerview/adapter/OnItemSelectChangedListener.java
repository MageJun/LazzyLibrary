package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.view.View;

public interface OnItemSelectChangedListener<T> {
    void onItemSelected(View view, int position, T data, boolean selected);
}
