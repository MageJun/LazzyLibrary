package com.android.lw.map.lib.location.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MapLocationService extends Service {
    public static final String TAG = MapLocationService.class.getSimpleName();
    private LocationBind mBind;
    private LocationClient mLocClient;
    private static final int REQUEST_LOCATION = 0;
    private static final int SHOW_CITY_DIALOG = 1;
    private boolean isNotifyCityChange = false;
    private BDLocationListener mLocationListener;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what ){
                case REQUEST_LOCATION:
                    requestLocation();
                    break;
                case SHOW_CITY_DIALOG:
                    break;
            }
            return false;
        }
    });
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind  ");
        return mBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate  ");
        mBind = new LocationBind();
        initLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy  ");
        stopLocation();

    }

    private void initLocation() {
        // 开启定位图层
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(mListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5*60*1000);//定位间隔5分钟
        //必须设置，否则请求定位时返回的数据会包JSON解析异常
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
    }

    private void setLocationOption(LocationClientOption option){
        if(mLocClient!=null&&option!=null){
            mLocClient.setLocOption(option);
        }
    }

    private void stopLocation(){
        if(mLocClient!=null){
            mLocClient.stop();
            mLocClient.unRegisterLocationListener(mListener);
            mLocClient = null;
        }
    }


    private void requestLocation(){
        Log.i(TAG,"requestLocation  ");
        if(!mLocClient.isStarted()){
            mLocClient.start();
        }else{
            mLocClient.requestLocation();
        }
    }



    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
                //检查定位到的城市，和当前用户所选城市是否在同一个，如果不在同一个地方，就提示用户是否切换到当前城市
            Log.i(TAG,"onReceiveLocation  ");
            if(mLocationListener!=null){
                mLocationListener.onReceiveLocation(bdLocation);
            }
        }
    };


    public  class LocationBind extends Binder {

        public void setLocationListener(BDLocationListener listener){
            mLocationListener = listener;
        }
        public void requestLocation(){
            mHandler.sendEmptyMessage(REQUEST_LOCATION);
        }

        public void setLocationOption(LocationClientOption option){
            setLocationOption(option);
        }

    }
}
