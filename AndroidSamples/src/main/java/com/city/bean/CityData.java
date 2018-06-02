package com.city.bean;

import com.common.widget.recyclerview.decoration.GroupItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CityData implements GroupItemDecoration.ItemDecorationData{

    @Override
    public String getTag() {
        if(mType == CityDataType.TOTALCITY&&mCitys!=null&&mCitys.size()>0){
            if(mCitys.get(0)!=null)
                return mCitys.get(0).getTag();
        }
        return null;
    }

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
    public void addCity(Province.City city){
        mCitys.add(city);
    }

    @Override
    public String toString() {
        return "CityData{" +
                "mType=" + mType +
                ", mCitys=" + mCitys +
                '}';
    }
}
