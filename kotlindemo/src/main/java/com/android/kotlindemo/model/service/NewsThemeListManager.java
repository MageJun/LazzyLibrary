package com.android.kotlindemo.model.service;

import android.os.Bundle;

import com.android.kotlindemo.event.NewsThemeEvent;
import com.android.kotlindemo.model.bean.net.NewsThemeBean;
import com.android.kotlindemo.utils.ZhiHuRiBaoAPI;
import com.android.kotlindemo.utils.net.MyCallBack;
import com.android.kotlindemo.utils.net.XUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NewsThemeListManager {

    private static volatile NewsThemeListManager sInstance = null;

    private NewsThemeBean newsThemeBean;


    private NewsThemeListManager() {
    }

    public static NewsThemeListManager getInstance() {
        if (sInstance == null) {
            synchronized (NewsThemeListManager.class) {
                sInstance = new NewsThemeListManager();
            }
        }
        return sInstance;
    }

    public void getNewsThemeList(){
        XUtil.Get(ZhiHuRiBaoAPI.Companion.getNEWS_THEMES_LIST(),null,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Gson gson = new Gson();
                NewsThemeBean bean = gson.fromJson(result,NewsThemeBean.class);
                if(bean!=null){
                    newsThemeBean = bean;
                    dispatchEvent(null,newsThemeBean,null, NewsThemeEvent.EventType.GET_THEMES);
                }
            }
        });
    }

    public NewsThemeBean getNewsThemeBean(){
        if(newsThemeBean==null){
            getNewsThemeList();
        }
        return newsThemeBean;
    }

    private void dispatchEvent(Bundle extra, NewsThemeBean bean, List<NewsThemeBean> list, NewsThemeEvent.EventType type){
        NewsThemeEvent event = new NewsThemeEvent();
        event.setEventType(type);
        event.setData(bean);
        event.setExtras(extra);
        event.setCollections(list);
        EventBus.getDefault().postSticky(event);
    }


}
