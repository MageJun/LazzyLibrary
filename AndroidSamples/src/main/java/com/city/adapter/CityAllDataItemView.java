package com.city.adapter;

import android.support.annotation.ColorInt;
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
import com.zed3.sipua.xydj.ui.friend.bean.FrindInfo;
import com.zed3.sipua.xydj.ui.friend.helper.GroupItemDecoration;
import com.zed3.sipua.xydj.ui.group.helper.GroupMemberListDecoration;
import com.zed3.sipua.xydj.ui.helper.SpellHelperUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityAllDataItemView extends BaseItemView<CityListDataBean> {

    private BaseRecycleViewAdapter mAdapter;
    private GroupItemDecoration mDecoration;
    private LinearLayoutManager mLM;

    public  CityAllDataItemView(){
      /*  mAdapter = new BaseRecycleViewAdapter() {
            @Override
            public void onCreateMulitTypeItemView() {
                InnerItemView itemView = new InnerItemView();
                addItemView(itemView);
            }
        };
//        mDecoration = new ItemDividerDecoration(AppApplication.sContext,LinearLayout.VERTICAL);
        mDecoration = new GroupItemDecoration(AppApplication.sContext,LinearLayout.VERTICAL);
        mDecoration.setOffset(4);
        mDecoration.setTitleTextSize(14);
        mDecoration.setTitleHight(30);
        mDecoration.setTitleTextColor(R.color.xydj_group_member_detail_text_color);
        mDecoration.setTitleBgColor(R.color.xydj_gray_2);
        mDecoration.setColor(AppApplication.sContext.getResources().getColor(R.color.xydj_gray_2));
        mDecoration.setStickTitle(true);
        mLM = new LinearLayoutManager(AppApplication.sContext);
        mDecoration.setOffset(5);*/
    }

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_total_data_item,parent,false);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, CityListDataBean data) {
        List<Province.City> citys = data.getmCitys();
        Province.City city = citys.get(0);
        holder.setText(R.id.city_name,city.getName());
       /* RecyclerView listView = holder.getView(R.id.list_view2);
        listView.setLayoutManager(mLM);
        listView.setAdapter(mAdapter);
        listView.addItemDecoration(mDecoration);
        List<Province.City> cityList = data.getmCitys();
        //默认升序
        Collections.sort(cityList, new Comparator<Province.City>() {
            @Override
            public int compare(Province.City o1, Province.City o2) {
                //#放到最后的位置
                //其它非字母字符，放到后面
                // 获取ascii值,65～90为26个大写英文字母，97～122号为26个小写英文字母，其余为一些标点符号、运算符号
                int lhs_ascii = o1.getTag().charAt(0);
                int rhs_ascii = o2.getTag().charAt(0);
                String o1_spellname = SpellHelperUtils.getAllFirstLetter(o1.getName());
                String o2_spellname = SpellHelperUtils.getAllFirstLetter(o2.getName());
                if("#".equalsIgnoreCase(o1.getTag())){
                    if("#".equalsIgnoreCase(o2.getTag())){
                        return o1_spellname.compareTo(o2_spellname);
                    }else{
                        return 1;
                    }
                }else if (lhs_ascii < 65 || lhs_ascii > 90)
                    return 1;
                else if (rhs_ascii < 65 || rhs_ascii > 90)
                    return -1;
                else
                    return o1_spellname.compareTo(o2_spellname);
            }
        });
        mAdapter.setData(data.getmCitys());
        mDecoration.setData(data.getmCitys());*/
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
