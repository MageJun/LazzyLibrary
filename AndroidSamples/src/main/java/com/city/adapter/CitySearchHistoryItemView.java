package com.city.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.common.widget.recyclerview.adapter.BaseItemView;
import com.common.widget.recyclerview.adapter.BaseViewHolder;
import com.common.widget.recyclerview.adapter.ItemViewManager;

/**
 * 城市搜索历史记录的ItemView
 */
public class CitySearchHistoryItemView extends BaseItemView<CityListDataBean> {
    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, CityListDataBean data) {

    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(CityListDataBean cityListDataBean, int position) {
        if(cityListDataBean.getmType()== CityListDataBean.CityDataType.HISCITY){
            return true;
        }else{
            return false;
        }
    }
}
