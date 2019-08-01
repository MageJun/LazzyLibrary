package com.lazzy.common.lib.widget.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class SingleSelectRecyclerViewAdapter<T> extends BaseRecycleViewAdapter {

    protected int curSelectPos = 0;

    public abstract int initSelectPos();

    private OnItemSelectChangedListener mItemSelectListener;

    public void setItemSelectListener(OnItemSelectChangedListener listener){
        this.mItemSelectListener = listener;
    }

    @Override
    public void setData(List data) {
        super.setData(data);
        curSelectPos =  initSelectPos();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        IItemView itemView = mItemViewManager.getItemView(viewType);
        if (itemView != null && itemView instanceof SingleSelectItemView) {
            SingleSelectItemView itemView1 = (SingleSelectItemView) itemView;
            //检测当前Item是否可选，不可选不做任何处理
            itemView1.onSingleItemViewBindVH((BaseViewHolder)holder,position,mDatas.get(position),(position==curSelectPos));
            if(!itemView1.onItemSelectAvailable((BaseViewHolder)holder,position,mDatas.get(position))){
                return ;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(curSelectPos==position){
                        return ;
                    }
                    int preSelectPos = curSelectPos;
                    curSelectPos = position;
                    notifyItemChanged(preSelectPos);
                    notifyItemChanged(curSelectPos);
                    if(mItemSelectListener!=null){
                        mItemSelectListener.onItemSingleSelected(holder.itemView,position,preSelectPos,mDatas.get(position));
                    }
                }
            });
        }
    }

}
