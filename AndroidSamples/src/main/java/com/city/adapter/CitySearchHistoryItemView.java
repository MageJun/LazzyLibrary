package com.city.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.city.bean.CityData;
import com.city.bean.Province;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder;
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager;
import com.lw.demo.android.samples.R;

import java.util.List;

/**
 * 城市搜索历史记录的ItemView
 * 同时最多显示三个数据
 */
public class CitySearchHistoryItemView extends BaseItemView<CityData> {

    public CitySearchHistoryItemView(){
    }

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View veiw = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_search_list_item,parent,false);
        return veiw;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, CityData data) {
        List<Province.City> cityList = data.getmCitys();
        if(cityList.size()==0){
            holder.setViewVisible(R.id.search_his_line,View.GONE);
        }else{
            holder.setViewVisible(R.id.search_his_line,View.VISIBLE);
            holder.setViewVisible(R.id.his_tv1,View.GONE);
            holder.setViewVisible(R.id.his_tv2,View.GONE);
            holder.setViewVisible(R.id.his_tv3,View.GONE);
            if(cityList.size()>0){
                holder.setViewVisible(R.id.his_tv1,View.VISIBLE);
                holder.setText(R.id.his_tv1,cityList.get(0).getName());
            }
            if(cityList.size()>1){
                holder.setViewVisible(R.id.his_tv2,View.VISIBLE);
                holder.setText(R.id.his_tv2,cityList.get(1).getName());
            }

            if(cityList.size()>2){
                holder.setViewVisible(R.id.his_tv3,View.VISIBLE);
                holder.setText(R.id.his_tv3,cityList.get(2).getName());
            }
        }

    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(CityData cityData, int position) {
        if(cityData.getmType()== CityData.CityDataType.HISCITY){
            return true;
        }else{
            return false;
        }
    }
}
