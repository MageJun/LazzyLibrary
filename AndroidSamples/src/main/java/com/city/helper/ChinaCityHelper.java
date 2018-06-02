package com.city.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.city.bean.Province;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChinaCityHelper {

    private static final String TAG = ChinaCityHelper.class.getSimpleName();

    private static final String CHINA_CITY_NAME = "china_city_data.json";

    private ChinaCityHelper(Context context){
        this.mContext = context;
        loadData();
    }

    private static ChinaCityHelper sInstance;

    private List<Province> mProvinceList;

    private Context mContext;
    private void loadData(){
        if(mContext!=null){
            long startTime = System.currentTimeMillis();
            String json = getJson(CHINA_CITY_NAME);
            if(!TextUtils.isEmpty(json)){
                Gson gson = new Gson();
                Type jsonType = new TypeToken<List<Province>>(){}.getType();
                List<Province> results = gson.fromJson(json,jsonType);
                Log.i(TAG,"json parser results = "+results);
                mProvinceList = results;
            }
            long endTime = System.currentTimeMillis();
            Log.i(TAG,"jsoon parser delay time = "+(endTime-startTime));
        }
    }

    private  String getJson(String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = mContext.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }



    public static ChinaCityHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new ChinaCityHelper(context);
        }
        return sInstance;
    }


    public List<Province> getAllProvinces(){
        return mProvinceList;
    }

    public List<Province.City> getAllCitys(){
        List<Province.City> citys = new ArrayList<>();
        if(mProvinceList!=null){
            for (Province province :
                    mProvinceList) {
                citys.addAll(province.getCityList());
            }
        }
        return citys;
    }


}
