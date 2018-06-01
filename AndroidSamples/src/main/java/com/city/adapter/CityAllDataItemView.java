package com.city.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.city.bean.Province;
import com.common.widget.recyclerview.adapter.BaseItemView;
import com.common.widget.recyclerview.adapter.BaseRecycleViewAdapter;
import com.common.widget.recyclerview.adapter.BaseViewHolder;
import com.common.widget.recyclerview.adapter.ItemViewManager;
import com.lw.demo.android.samples.AppApplication;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.GridDividerDecoration;
import com.zed3.sipua.xydj.ui.ItemDividerDecoration;

public class CityAllDataItemView extends BaseItemView<CityListDataBean> {

    private BaseRecycleViewAdapter mAdapter;
    private ItemDividerDecoration mDecoration;
    private LinearLayoutManager mLM;

    public  CityAllDataItemView(){
        mAdapter = new BaseRecycleViewAdapter() {
            @Override
            public void onCreateMulitTypeItemView() {
                InnerItemView itemView = new InnerItemView();
                addItemView(itemView);
            }
        };
        mDecoration = new ItemDividerDecoration(AppApplication.sContext,LinearLayout.VERTICAL);
        mLM = new LinearLayoutManager(AppApplication.sContext);
        mDecoration.setOffset(5);
    }

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_search_list_item,null);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, CityListDataBean data) {
        RecyclerView listView = holder.getView(R.id.list_view2);
        listView.setLayoutManager(mLM);
        listView.setAdapter(mAdapter);
        listView.addItemDecoration(mDecoration);
        mAdapter.setData(data.getmCitys());
    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(CityListDataBean cityListDataBean, int position) {
        if(cityListDataBean.getmType() == CityListDataBean.CityDataType.TOTALCITY){
            return true;
        }else{
            return false;
        }
    }

    public static class InnerItemView extends BaseItemView<Province.City>{

        @Override
        public View getItemView(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_total_data_item,parent,false);
            return view;
        }


        @Override
        public void onBindVH(@NonNull BaseViewHolder holder, int position, Province.City data) {
            holder.setText(R.id.city_name,data.getName());
        }

        @Override
        public int getItemViewType() {
            return ItemViewManager.obtainItemType();
        }
    }
}
