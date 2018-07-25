package com.android.kotlindemo.model.service;


import android.os.Bundle;
import android.text.TextUtils;

import com.android.kotlindemo.event.NewsEvent;
import com.android.kotlindemo.model.bean.net.HomeNewsBean;
import com.android.kotlindemo.model.bean.net.INewsBean;
import com.android.kotlindemo.model.bean.net.NewsContentBean;
import com.android.kotlindemo.model.bean.net.ThemeNewsBean;
import com.android.kotlindemo.utils.ZhiHuRiBaoAPI;
import com.android.kotlindemo.utils.net.MyCallBack;
import com.android.kotlindemo.utils.net.XUtil;
import com.google.gson.Gson;
import com.lazzy.common.lib.utils.L;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻管理类
 * 用来获取首页新闻和其它类别的新闻
 */
public class NewsManager {

    private static volatile NewsManager sInstance = null;

    private NewsManager() {
    }

    public static NewsManager getInstance() {
        if (sInstance == null) {
            synchronized (NewsManager.class) {
                sInstance = new NewsManager();
            }
        }
        return sInstance;
    }

    /**
     * 获取首页最新新闻
     */
    public void getHomeNews(){
        Map<String,String> params = new HashMap<>();
        XUtil.Get(ZhiHuRiBaoAPI.Companion.getNEWS_LATEST_URL(),params,new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                if(!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    HomeNewsBean bean = gson.fromJson(result,HomeNewsBean.class);
                    dispatchEvent(null,bean,null, NewsEvent.EventType.HOME_NEWS);
                }
            }
        });
    }

    /**
     * 获取首页置顶日期前一天的新闻
     * @param date
     */
    public void getBerforeNews(String date){
        if(TextUtils.isEmpty(date)){
            return;
        }
        String url = String.format(ZhiHuRiBaoAPI.Companion.getNEWS_BEFORE(),date);
        L.i("url = "+url);
        Map<String,String> params = new HashMap<>();
        XUtil.Get(url,params,new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                if(!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    HomeNewsBean bean = gson.fromJson(result,HomeNewsBean.class);
                    dispatchEvent(null,bean,null, NewsEvent.EventType.MORE_NEWS);
                }
            }
        });
    }

    public void getNewsContent(String newsId){
        if(TextUtils.isEmpty(newsId)){
            return;
        }
        String url = String.format(ZhiHuRiBaoAPI.Companion.getNEWS_BODY_URL(),newsId);
        L.i("url = "+url);
        Map<String,String> params = new HashMap<>();
        XUtil.Get(url,params,new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                if(!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    NewsContentBean bean = gson.fromJson(result,NewsContentBean.class);
                    dispatchEvent(null,bean,null, NewsEvent.EventType.NEWS_CONTENT);
                }
            }
        });
    }

    /**
     * 根据ThemeID获取相关新闻
     * @param themeId
     */
    public void getThemeNewsByThemeID(int themeId){
        Map<String,String> params = new HashMap<>();
        String url = String.format(ZhiHuRiBaoAPI.Companion.getNEWS_LIST_BY_THEME(),themeId);
        L.i("url = "+url);
        XUtil.Get(url,params,new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                if(!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    ThemeNewsBean bean = gson.fromJson(result,ThemeNewsBean.class);
                    dispatchEvent(null,bean,null, NewsEvent.EventType.THEME_NEWS);
                }
            }
        });
    }

    public void getMoreThemeNewsByThemeID(int themeID,int lastStoryID){
        Map<String,String> params = new HashMap<>();
        String url = String.format(ZhiHuRiBaoAPI.Companion.getNEWS_LIST_BY_THEME_BEFORE(),themeID,lastStoryID);
        L.i("url = "+url);
        XUtil.Get(url,params,new MyCallBack<String>(){

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                if(!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    ThemeNewsBean bean = gson.fromJson(result,ThemeNewsBean.class);
                    dispatchEvent(null,bean,null, NewsEvent.EventType.MORE_THEME_NEWS);
                }
            }
        });
    }


    private void dispatchEvent(Bundle extras, INewsBean bean, List<INewsBean> collection, NewsEvent.EventType eventType){
        NewsEvent event = new NewsEvent();
        event.setEventType(eventType);
        event.setCollections(collection);
        event.setData(bean);
        event.setExtras(extras);
        EventBus.getDefault().post(event);
    }
}
