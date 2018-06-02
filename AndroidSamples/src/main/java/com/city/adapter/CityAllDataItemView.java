package com.city.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.city.bean.CityData;
import com.city.bean.Province;
import com.common.widget.recyclerview.adapter.BaseItemView;
import com.common.widget.recyclerview.adapter.BaseRecycleViewAdapter;
import com.common.widget.recyclerview.adapter.BaseViewHolder;
import com.common.widget.recyclerview.adapter.ItemViewManager;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.friend.helper.GroupItemDecoration;

import java.util.List;

public class CityAllDataItemView extends BaseItemView<CityData> {

    private BaseRecycleViewAdapter mAdapter;
    private GroupItemDecoration mDecoration;
    private LinearLayoutManager mLM;

    public  CityAllDataItemView(){
    }

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_total_data_item,parent,false);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, CityData data) {
        List<Province.City> citys = data.getmCitys();
        Province.City city = citys.get(0);
        holder.setText(R.id.city_name,city.getName());
    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(CityData cityData, int position) {
        if(cityData.getmType() == CityData.CityDataType.TOTALCITY){
            return true;
        }else{
            return false;
        }
    }
}
