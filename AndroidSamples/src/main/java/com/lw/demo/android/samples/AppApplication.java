package com.lw.demo.android.samples;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.android.lw.map.lib.location.service.LocationServiceManager;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.city.helper.ChinaCityHelper;

public class AppApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if(LocationServiceManager.getInstance().isBaiduRemoteProcess(this)){
            return;
        }
        sContext = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        LocationServiceManager.getInstance().startService(this);
        ChinaCityHelper.getInstance(this).init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决方法数超出了65535，导致出现NoClassDefFoundError的为问题
        MultiDex.install(base);
    }
}
