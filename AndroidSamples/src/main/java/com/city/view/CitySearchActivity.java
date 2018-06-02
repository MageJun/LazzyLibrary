package com.city.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.city.adapter.CityListDataBean;
import com.city.adapter.CitySearchListAdapter;
import com.city.bean.Province;
import com.city.helper.ChinaCityHelper;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.ItemDividerDecoration;

import java.util.ArrayList;
import java.util.List;

public class CitySearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        RecyclerView listView = findViewById(R.id.list_view);
        CitySearchListAdapter mAdapter = new CitySearchListAdapter();
        List<Province.City> mCitys = ChinaCityHelper.getInstance(this).getAllCitys();
        CityListDataBean totalBean = new CityListDataBean();
        totalBean.setmCitys(mCitys);
        totalBean.setmType(CityListDataBean.CityDataType.TOTALCITY);

        Log.i("CityTrace","mCitys = "+mCitys.size());
        CityListDataBean searchBean = new CityListDataBean();
        searchBean.setmType(CityListDataBean.CityDataType.SEARCH);

        List<CityListDataBean> datas = new ArrayList<>();
        datas.add(searchBean);
        datas.add(totalBean);

        ItemDividerDecoration decoration = new ItemDividerDecoration(this, LinearLayout.VERTICAL);
        decoration.setOffset(2);
        //手动设置RecyclerView的LayouParams属性，默认都是WRAP_CONTENT,这样在嵌套RecyclerView时宽度会显示不完整
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                return params;
            }
        };
        listView.addItemDecoration(decoration);
        listView.setLayoutManager(linearLayoutManager);
        CitySearchListAdapter adapter = new CitySearchListAdapter();
        listView.setAdapter(adapter);
        adapter.setData(datas);
    }
}
