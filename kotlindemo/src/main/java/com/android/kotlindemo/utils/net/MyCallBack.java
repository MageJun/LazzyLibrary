package com.android.kotlindemo.utils.net;

import com.lazzy.common.lib.utils.L;

import org.xutils.common.Callback;

public class MyCallBack<String> implements Callback.CommonCallback<String> {

    private static final java.lang.String TAG = MyCallBack.class.getSimpleName();

    @Override
    public void onSuccess(String result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
        L.i(TAG,"onSuccess result = "+result);

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


}