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
import com.common.widget.sidebar.LetterSideBar;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.friend.helper.GroupItemDecoration;
import com.zed3.sipua.xydj.ui.helper.SpellHelperUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CitySearchActivity extends AppCompatActivity {

    private List<CityListDataBean> totalCityDatas;
    private LetterSideBar mSideBar;
    private RecyclerView mListView;
    private GroupItemDecoration mDecoration;
    private List<CityListDataBean> mDatas;

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
        CitySearchListAdapter mAdapter = new CitySearchListAdapter();
        List<CityListDataBean> totalCitys = getTotalCityDatas();
        CityListDataBean searchBean = new CityListDataBean();
        searchBean.setmType(CityListDataBean.CityDataType.SEARCH);

        mDatas = new ArrayList<>();
        mDatas.add(searchBean);
        mDatas.addAll(totalCitys);
        Collections.sort(mDatas,new Comparator<CityListDataBean>() {
            @Override
            public int compare(CityListDataBean o1, CityListDataBean o2) {
                if(o1.getmType()== CityListDataBean.CityDataType.SEARCH){
                    return -1;
                }else if(o1.getmType() == CityListDataBean.CityDataType.HISCITY){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        CitySearchListAdapter adapter = new CitySearchListAdapter();
        mListView.setAdapter(adapter);
        adapter.setData(mDatas);
        mDecoration.setData(mDatas);
        mSideBar.setSelectChangedListener(mChangedListener);
        mListView.addOnScrollListener(mScrollListener);
    }

    private LetterSideBar.OnIndexSelectChanged mChangedListener = new LetterSideBar.OnIndexSelectChanged() {
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

    public List<CityListDataBean> getTotalCityDatas() {
        List<CityListDataBean> datas = new ArrayList<>();
        List<Province.City> mCitys = ChinaCityHelper.getInstance(this).getAllCitys();
        Log.i("CityTrace", "mCitys = " + mCitys.size());
        for (Province.City city :
                mCitys) {
            CityListDataBean bean = new CityListDataBean();
            bean.addCity(city);
            bean.setmType(CityListDataBean.CityDataType.TOTALCITY);
            datas.add(bean);
        }

        Log.i("CityTrace", "Total CityListDataBean = " + datas.size());
        return datas;
    }
}
