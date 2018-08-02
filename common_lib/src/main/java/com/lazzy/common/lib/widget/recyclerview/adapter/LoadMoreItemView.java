package com.lazzy.common.lib.widget.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazzy.common.lib.R;


public class LoadMoreItemView extends BaseItemView<Object> {
    private LoadMoreRecyclerViewAdapter mAdapter;

    public void setAdapter(LoadMoreRecyclerViewAdapter adapter){
        this.mAdapter = adapter;
    }

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_itemview_layout,parent,false);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, Object data) {

    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(Object o, int position) {
        if(mAdapter!=null&&mAdapter.isShowFooter()){
            Log.i("LoadMoreTrace","isForViewType pos = "+position+",getItemCount = "+mAdapter.getItemCount());
            return (position+1)==mAdapter.getItemCount();
        }

        return false;

    }
}
