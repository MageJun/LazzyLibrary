package com.city.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lazzy.common.lib.widget.recyclerview.adapter.OnItemClickListener;
import com.lazzy.common.lib.widget.sidebar.LetterSideBarAutoAdapt;
import com.city.bean.CityData;
import com.city.adapter.CitySearchListAdapter;
import com.city.bean.Province;
import com.city.helper.ChinaCityHelper;
import com.lw.demo.android.samples.R;
import com.common.widget.recyclerview.decoration.GroupItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CitySearchActivity extends AppCompatActivity {

    private List<CityData> totalCityDatas;
    private LetterSideBarAutoAdapt mSideBar;
    private RecyclerView mListView;
    private GroupItemDecoration mDecoration;
    private List<CityData> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        initView();
        initData();
    }

    private void initView() {
        mSideBar = findViewById(R.id.sidebar);
        mListView = findViewById(R.id.list_view);
        mDecoration = new GroupItemDecoration(this, LinearLayout.VERTICAL);
        mDecoration.setOffset(4);
        mDecoration.setTitleTextSize(14);
        mDecoration.setTitleHight(30);
        mDecoration.setTitleTextColor(R.color.xydj_group_member_detail_text_color);
        mDecoration.setTitleBgColor(R.color.xydj_gray_2);
        mDecoration.setColor(getResources().getColor(R.color.xydj_gray_2));
        mDecoration.setStickTitle(true);
        mDecoration.setOffset(2);
        //手动设置RecyclerView的LayouParams属性，默认都是WRAP_CONTENT,这样在嵌套RecyclerView时宽度会显示不完整
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                return super.generateDefaultLayoutParams();
            }
        };
        mListView.addItemDecoration(mDecoration);
        mListView.setLayoutManager(linearLayoutManager);
    }
    private void initData(){
        mSideBar.setLetters(ChinaCityHelper.getInstance(this).getAllCityLetters());
        CitySearchListAdapter mAdapter = new CitySearchListAdapter();
        List<CityData> totalCitys = getTotalCityDatas();
        CityData searchBean = new CityData();
        searchBean.setmType(CityData.CityDataType.SEARCH);
        CityData hisBean = getHisCityDatas(3);



        mDatas = new ArrayList<>();
        mDatas.add(searchBean);
        mDatas.addAll(totalCitys);
//        mDatas.add(totalCitys.get(0));
        mDatas.add(hisBean);
        Collections.sort(mDatas,new Comparator<CityData>() {
            @Override
            public int compare(CityData o1, CityData o2) {
                Log.i("CompareTrace","o1 = "+o1.getmType()+",o2 = "+o2.getmType());
                if(o1.getmType()== CityData.CityDataType.SEARCH){
                    return -1;
                }else if(o1.getmType() == CityData.CityDataType.HISCITY){
                    if(o2.getmType()== CityData.CityDataType.TOTALCITY)
                         return -1;
                    else{
                        return 1;
                    }
                }else if(o2.getmType() == CityData.CityDataType.SEARCH){
                    return 1;
                }else if(o2.getmType() == CityData.CityDataType.HISCITY){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        CitySearchListAdapter adapter = new CitySearchListAdapter();
        mListView.setAdapter(adapter);
        adapter.setData(mDatas);
        adapter.setItemClickListener(new OnItemClickListener<CityData>() {
            @Override
            public void onItemClick(View view, int pos, CityData data, Object obj) {
                Toast.makeText(CitySearchActivity.this,"pos = "+pos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int pos, CityData data, Object obj) {

            }
        });
        mDecoration.setData(mDatas);
        mSideBar.setSelectChangedListener(mChangedListener);
        mListView.addOnScrollListener(mScrollListener);
    }

    private LetterSideBarAutoAdapt.OnIndexSelectChanged mChangedListener = new LetterSideBarAutoAdapt.OnIndexSelectChanged() {
        @Override
        public void onSelectChanged(int lastIndex, int curIndex,String letter) {
            if(mDatas!=null&&mDatas.size()>curIndex){
                int pos = findCurPos(letter);
                if(pos!=-1){
                    mListView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mListView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        }
    };

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(RecyclerView.SCROLL_STATE_IDLE == newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int pos = lm.findFirstVisibleItemPosition();
                if (pos > -1 && pos < mDatas.size()) {
                    String tag = mDatas.get(pos).getTag();
                    mSideBar.setCurrentLetter(tag);
                }
            }
        }
    };
    private int findCurPos(String letter) {
        for (int i = 0;i<mDatas.size();i++){
            if(letter.equals(mDatas.get(i).getTag())){
                return i;
            }
        }
        return -1;
    }

    private CityData getHisCityDatas(int count){
        CityData data = new CityData();
        data.setmType(CityData.CityDataType.HISCITY);
        List<Province.City> mCitys = ChinaCityHelper.getInstance(this).getAllCitys();
        Random random = new Random();
        for (int i = 0;i<count;i++){
            int index = random.nextInt(mCitys.size()-6);
            data.addCity(mCitys.get(index));
        }
        Log.i("CityTrace", "mCitys = " + data.getmCitys().size());
        return data;
    }

    private List<CityData> getTotalCityDatas() {
        List<CityData> datas = new ArrayList<>();
        List<Province.City> mCitys = ChinaCityHelper.getInstance(this).getAllCitys();
        Log.i("CityTrace", "mCitys = " + mCitys.size());
        for (Province.City city :
                mCitys) {
            CityData bean = new CityData();
            bean.addCity(city);
            bean.setmType(CityData.CityDataType.TOTALCITY);
            datas.add(bean);
        }

        Log.i("CityTrace", "Total CityData = " + datas.size());
        return datas;
    }
}
