package com.lazzy.common.lib.widget.recyclerview.adapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

public abstract  class LoadMoreRecyclerViewAdapter<T> extends BaseRecycleViewAdapter {

    private LoadMoreItemView mLoadMoreItemView;
    private RecyclerView mListView;

    private boolean isNeedShowFooter = false;

    public abstract LoadMoreItemView createLoadMoreItemView();

    public interface  OnLoadMoreListener{
        void loadMore();
    }

    private OnLoadMoreListener mLoadListener;

    public void setLoadMoreListener(OnLoadMoreListener listener){
        this.mLoadListener = listener;
    }

    public void setFooterAvaible(boolean isShowFooter){
        if(isNeedShowFooter==isShowFooter){
            return;
        }
        this.isNeedShowFooter = isShowFooter;
        notifyDataSetChanged();
    }

    public boolean isShowFooter(){
        return this.isNeedShowFooter;
    }

    public LoadMoreRecyclerViewAdapter(RecyclerView listView){
        mLoadMoreItemView = createLoadMoreItemView();
        if(mLoadMoreItemView==null)
            mLoadMoreItemView = new LoadMoreItemView();

        mLoadMoreItemView.setAdapter(this);
        addItemView(mLoadMoreItemView);
        this.mListView = listView;
        this.mListView.addOnScrollListener(mScrollListener);

    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(isSlideToBottom(recyclerView)){
                boolean isCanScrollUP = mListView.canScrollVertically(-1);
                if(isCanScrollUP){
                   setFooterAvaible(true);
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //手指离开屏幕，此时判断是否需要自行加载更多
            if(newState==RecyclerView.SCROLL_STATE_IDLE) {
                if (mLoadListener != null) {
                    if (isSlideToBottom(recyclerView)) {
                        boolean isCanScrollUP = mListView.canScrollVertically(-1);
                        if (isCanScrollUP) {

                            mLoadListener.loadMore();
                        }
                    }
                }
            }
        }
    };

    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return  true;
        else
            return  false;
    }

    @Override
    public void setData(List data) {
        setFooterAvaible(false);
        super.setData(data);
    }

    @Override
    public void insertData(Collection data) {
        setFooterAvaible(false);
        super.insertData(data);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isShowFooter()){
            IItemView itemView = mItemViewManager.getItemView(getItemViewType(position));
            if(itemView instanceof LoadMoreItemView){
                itemView.onBindViewHolder(holder,position,null);
                return ;
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if(isShowFooter()){
            return mDatas.size()==0?0:mDatas.size()+1;
        }

        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        //如果是最后一个item
        if(isShowFooter()&&(position+1)==getItemCount()){
            return mItemViewManager.getViewType(null, position);
        }
        return super.getItemViewType(position);
    }
}
