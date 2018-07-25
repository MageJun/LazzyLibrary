package com.android.kotlindemo.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.kotlindemo.R;
import com.android.kotlindemo.model.bean.net.ThemeNewsBean;
import com.lazzy.common.lib.utils.ViewHelper;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder;
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager;

public class EditorItemView extends BaseItemView<ThemeNewsBean.EditorsBean> {
    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_editor,parent,false);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, ThemeNewsBean.EditorsBean data) {
        ImageView imageView = holder.getView(R.id.img_view);
        ViewHelper.setCircleImgView(imageView,data.getAvatar());
    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }
}
