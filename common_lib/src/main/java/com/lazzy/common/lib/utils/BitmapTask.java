package com.lazzy.common.lib.utils;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class BitmapTask implements Runnable {
    public final static String KEY_INDEX ="index" ;
    public final static String KEY_URL ="url" ;
    private int index;
    private String url;
    private Bundle data;
    private Handler.Callback callBack;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(callBack!=null){
                msg.setData(data);
                callBack.handleMessage(msg);
            }
            return false;
        }
    });

    public BitmapTask(String url){
        this.url = url;
        data = new Bundle();
        data.putString(KEY_URL,url);
    }

    public BitmapTask(int index, String url){
        this.index = index;
        this.url = url;
        data = new Bundle();
        data.putInt(KEY_INDEX,index);
        data.putString(KEY_URL,url);
    }
    public void setCallBack(Handler.Callback callBack){
        this.callBack = callBack;
    }


    @Override
    public void run() {
        BitmapFactory.Options options = ViewHelper.getBitmapOptions(url);
        if(options!=null){
           Message msg = Message.obtain();
           msg.obj = options;
           handler.sendMessage(msg);
        }
    }
}
