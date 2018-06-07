package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.support.annotation.NonNull;

public abstract class MulitItemView<T> extends BaseItemView {

    public abstract  void onMulitItemViewBindVH(BaseViewHolder holder,int position,T data,boolean checked);

    public abstract boolean onItemSelectAvailable(BaseViewHolder holder,int position,T data);

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, Object data) {
        //do nothing
    }
}
