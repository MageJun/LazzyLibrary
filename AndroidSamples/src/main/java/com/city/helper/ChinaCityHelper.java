package com.city.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.city.bean.Province;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.common.utils.SpellHelperUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChinaCityHelper {

    private static final String TAG = ChinaCityHelper.class.getSimpleName();

    private static final String CHINA_CITY_NAME = "china_city_data.json";

    private static final String[] FILTER_CITY_NAME = {"县","省直辖县级行政区划","自治区直辖县级行政区划"};



    private ChinaCityHelper(Context context){
        this.mContext = context;
    }

    private static ChinaCityHelper sInstance;

    private List<Province> mProvinceList;
    private List<Province.City> mCityList;
    private List<String> mCityNameLetters = new ArrayList<>();

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


    public void init(){
        initInner();
    }

    private void initInner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (ChinaCityHelper.this){
                    loadData();
                    initCityList();
                    sortAndinitSpellName();
                }
            }
        }).start();

    }

    /**
     * 初始化城市集合
     */
    private void initCityList() {
        List<String> filterList = Arrays.asList(FILTER_CITY_NAME);
        mCityList = new ArrayList<>();
        if(mProvinceList!=null){
            for (Province province :
                    mProvinceList) {
                mCityList.addAll(province.getCityList());
            }
        }
       for (int i = mCityList.size()-1;i>=0;i--){
          if( filterList.contains(mCityList.get(i).getName()))
            mCityList.remove(i);
       }
    }

    /**
     * 获取城市名拼音字母，并按字母进行排序
     */
    private void sortAndinitSpellName() {
        if(mCityList!=null){
            for (Province.City city :
                    mCityList) {
                String spellName = SpellHelperUtils.converterToSpell(city.getName());
                city.setSpellName(spellName);
                if(!mCityNameLetters.contains(city.getTag())){
                    mCityNameLetters.add(city.getTag());
                }
            }

            Collections.sort(mCityNameLetters, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if("#".equals(o1)){
                        return 1;
                    }else{
                        return o1.compareTo(o2);
                    }
                }
            });

            Collections.sort(mCityList, new Comparator<Province.City>() {
                @Override
                public int compare(Province.City o1, Province.City o2) {
                    {
                        //#放到最后的位置
                        //其它非字母字符，放到后面
                        // 获取ascii值,65～90为26个大写英文字母，97～122号为26个小写英文字母，其余为一些标点符号、运算符号
                        if(TextUtils.isEmpty(o1.getTag())){
                            return 1;
                        }
                        if(TextUtils.isEmpty(o2.getTag())){
                            return -1;
                        }
                        int lhs_ascii = o1.getTag().charAt(0);
                        int rhs_ascii = o2.getTag().charAt(0);
                        String o1_spellname = o1.getSpellName();
                        String o2_spellname = o2.getSpellName();
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
                }
            });
        }
    }


    public static ChinaCityHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new ChinaCityHelper(context);
        }
        return sInstance;
    }


    public synchronized  List<Province> getAllProvinces(){
        return mProvinceList;
    }

    public synchronized List<Province.City> getAllCitys(){
        return mCityList;
    }

    public synchronized List<String> getAllCityLetters(){return mCityNameLetters;}


}
