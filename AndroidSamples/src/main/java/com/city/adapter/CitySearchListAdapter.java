package com.city.adapter;

import com.city.bean.CityData;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseRecycleViewAdapter;

public class CitySearchListAdapter extends BaseRecycleViewAdapter<CityData> {
    @Override
    public void onCreateMulitTypeItemView() {
        CitySearchItemView sItemView = new CitySearchItemView();
        CitySearchHistoryItemView hItemView = new CitySearchHistoryItemView();
        CityAllDataItemView tItemView = new CityAllDataItemView();

        addItemView(sItemView);
        addItemView(hItemView);
        addItemView(tItemView);
    }
}
