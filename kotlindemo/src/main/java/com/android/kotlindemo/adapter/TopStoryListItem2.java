package com.android.kotlindemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.kotlindemo.R;
import com.android.kotlindemo.model.bean.net.StoryBean;
import com.android.kotlindemo.model.bean.net.ThemeNewsBean;
import com.lazzy.common.lib.utils.ResourceHelper;
import com.lazzy.common.lib.utils.ViewHelper;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseRecycleViewAdapter;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder;
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager;
import com.lazzy.common.lib.widget.recyclerview.decoration.ItemDividerDecoration;

import java.util.ArrayList;

public class TopStoryListItem2 extends BaseItemView<StoryBean> {

    private LinearLayoutManager layoutManager ;

    private BaseRecycleViewAdapter<ThemeNewsBean.EditorsBean> mAdapter = new BaseRecycleViewAdapter<ThemeNewsBean.EditorsBean>() {
        @Override
        public void onCreateMulitTypeItemView() {
            addItemView(new EditorItemView());
        }
    };

    private RecyclerView mListView;

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_top_story2,parent,false);
        mListView = view.findViewById(R.id.editorListView);
        layoutManager = new LinearLayoutManager(parent.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mListView.setLayoutManager(layoutManager);
        ItemDividerDecoration decoration = new ItemDividerDecoration(parent.getContext(),LinearLayoutManager.HORIZONTAL);
        decoration.setOffset(ResourceHelper.getDimen(6));
        mListView.addItemDecoration(decoration);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, StoryBean data) {

        ImageView img = holder.getView(R.id.imgview);

        holder.setText(R.id.description,data.getTitle());

        ViewHelper.setImgview(img,data.getImage());
        if(data.getExtra()!=null){
            ArrayList<ThemeNewsBean.EditorsBean> editorsBeans =data.getExtra().getParcelableArrayList("editors");
            if(editorsBeans!=null){
                mAdapter.setData(editorsBeans);
            }
        }
    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(StoryBean storyBean, int position) {
        return storyBean.getMode()== StoryBean.Mode.IMG;
    }
}
