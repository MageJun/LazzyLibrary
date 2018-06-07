package com.lw.demo.android.samples.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder;
import com.lazzy.common.lib.widget.recyclerview.adapter.SingleSelectItemView;
import com.lw.demo.android.samples.R;
import com.lw.demo.android.samples.SingleSelectListActivity;

public class SelectSingleItemView extends SingleSelectItemView<SingleSelectListActivity.DemoData> {
    @Override
    public void onSingleItemViewBindVH(BaseViewHolder holder, int position, SingleSelectListActivity.DemoData data, boolean checked) {
        holder.setText(R.id.tv,data.getText());
        if(checked){
            holder.setImgResource(R.id.select_icon,R.drawable.single_selected);
        }else{
            holder.setImgResource(R.id.select_icon,R.drawable.single_un_selected);
        }


    }

    @Override
    public boolean onItemSelectAvailable(BaseViewHolder holder, int position, SingleSelectListActivity.DemoData data) {
        return true;
    }

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_select_item,parent,false);
        return view;
    }
}
