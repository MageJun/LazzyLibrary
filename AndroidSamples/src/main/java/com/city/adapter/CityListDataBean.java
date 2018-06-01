package com.city.adapter;

import com.city.bean.Province;

import java.util.ArrayList;
import java.util.List;

public class CityListDataBean {

    /**
     *城市数据类型
     */
    public enum CityDataType{
        SEARCH,//搜索框
        LOCCITY,//当前城市
        HISCITY,//搜索记录
        TOTALCITY;//全部城市数据
    }

    private CityDataType mType;

    private List<Province.City> mCitys = new ArrayList<>();

    public CityDataType getmType() {
        return mType;
    }

    public void setmType(CityDataType mType) {
        this.mType = mType;
    }

    public List<Province.City> getmCitys() {
        return mCitys;
    }

    public void setmCitys(List<Province.City> mCitys) {
        this.mCitys = mCitys;
    }

    @Override
    public String toString() {
        return "CityListDataBean{" +
                "mType=" + mType +
                ", mCitys=" + mCitys +
                '}';
    }
}
