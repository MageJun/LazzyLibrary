package com.android.kotlindemo.utils.net;



import android.content.Context;

import com.android.kotlindemo.AppApplication;
import com.lazzy.common.lib.utils.L;

import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by gaowuchao on 2016/8/18.
 */
public class XUtil {
    /**
     * 发送get请求
     *
     * @param <T>
     */
    public static <T> Cancelable Get(String url, Map<String, String> map, CommonCallback<T> callback) {
        L.e("get: " + url);
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(15000);
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
                L.e(" param:<" + entry.getKey() + "," + entry.getValue() + ">");
            }
        }

        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     *
     * @param <T>
     */
    public static <T> Cancelable Post(String url, Map<String, Object> map, CommonCallback<T> callback) {
        L.e("post: " + url);
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(7000);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
                L.e(" param:<" + entry.getKey() + "," + entry.getValue() + ">");
            }
        }
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    public static void init(AppApplication context){
        x.Ext.init(context);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
    }

}