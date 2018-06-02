package com.city.adapter;

import com.common.widget.recyclerview.adapter.BaseRecycleViewAdapter;

public class CitySearchListAdapter extends BaseRecycleViewAdapter<CityListDataBean> {
    @Override
    public void onCreateMulitTypeItemView() {
        CitySearchItemView sItemView = new CitySearchItemView();
        CitySearchHistoryItemView hItemView = new CitySearchHistoryItemView();
        CityAllDataItemView tItemView = new CityAllDataItemView();

        addItemView(sItemView);
//        addItemView(hItemView);
        addItemView(tItemView);
    }
}
