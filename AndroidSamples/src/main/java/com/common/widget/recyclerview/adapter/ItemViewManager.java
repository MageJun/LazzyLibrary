package com.common.widget.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * IItemView管理类，用来增、删指定类型的ItemView，返回ItemViewType，itemViewTypeCount数据等操作
 * 如果添加任何类型的情况下，会使用默认的item类型
 * @param <T>
 */
public class ItemViewManager<T> {
    private SparseArray<IItemView> mItemViews;
    private DefaultItemView mDefaultItemView;
    private static int ITEM_VIEW_TYPE = 1;

    public ItemViewManager(){
        mItemViews = new SparseArray<>();
        mDefaultItemView = new DefaultItemView();
    }

    public void addItemView(IItemView itemView){
        mItemViews.put(itemView.getItemViewType(),itemView);
    }
    public void removeItemView(IItemView itemView){
        mItemViews.delete(itemView.getItemViewType());
    }
    public IItemView getItemView(int viewType){
        return mItemViews.get(viewType,mDefaultItemView);
    }

    public int getItemTypeCount(){
        return mItemViews.size()==0?1:mItemViews.size();
    }

    public int getViewType(T t,int pos){
        int viewType = mDefaultItemView.getItemViewType();
        for (int i = mItemViews.size()-1;i>=0;i--){
           int type = mItemViews.keyAt(i);
            IItemView itemView = mItemViews.get(type);
            if(itemView.isForViewType(t,pos)){
                return type;
            }
        }
        return viewType;
    }

    public static int obtainItemType(){
        return ITEM_VIEW_TYPE++;
    }


    /**
     * 默认的ItemView样式，使用simple_list_item_1
     */
    private static class DefaultItemView extends BaseItemView<String>{

        @Override
        public View getItemView(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
            return view;
        }

        @Override
        public void onBindVH(@NonNull BaseViewHolder holder, int position, String data) {
            if(!TextUtils.isEmpty(data)){
                holder.setText(android.R.id.text1,data);
            }
        }

        @Override
        public int getItemViewType() {
            return obtainItemType();
        }
    }

}
