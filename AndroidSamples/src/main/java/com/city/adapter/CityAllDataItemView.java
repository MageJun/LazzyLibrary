package com.city.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.city.bean.CityData;
import com.city.bean.Province;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseRecycleViewAdapter;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder;
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager;
import com.lazzy.common.lib.widget.recyclerview.decoration.GroupItemDecoration;
import com.lw.demo.android.samples.R;

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
    public void onBindVH(@NonNull BaseViewHolder holder, final int position, CityData data) {
        List<Province.City> citys = data.getmCitys();
        Province.City city = citys.get(0);
        holder.setText(R.id.city_name,city.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener!=null){
                    mItemClickListener.onItemClick(v,position,null,null);
                }
            }
        });
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
