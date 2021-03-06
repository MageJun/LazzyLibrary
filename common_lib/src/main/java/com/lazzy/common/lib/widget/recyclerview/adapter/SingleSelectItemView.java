package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

public abstract class SingleSelectItemView<T> extends BaseItemView {

    public abstract  void onSingleItemViewBindVH(BaseViewHolder holder,int position,T data,boolean checked);

    public abstract boolean onItemSelectAvailable(BaseViewHolder holder,int position,T data);

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, Object data) {

    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }
}
