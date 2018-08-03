package com.android.lw.map.lib.location.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LocationServiceManager {

    private LocationServiceManager(){
    }
    private static final String TAG = "LocationServiceManager";
    private static LocationServiceManager sInstance;
    private MapLocationService.LocationBind mLocationService;
    private Context mContext;
    private boolean isRunning = false;
    public static LocationServiceManager getInstance(){
        if(sInstance == null){
            sInstance = new LocationServiceManager();
        }
        return sInstance;
    }

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation!=null){
                EventBus.getDefault().postSticky(new LocationChangeEvent(bdLocation));
            }
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isRunning = true;
            mLocationService = (MapLocationService.LocationBind) service;
            mLocationService.setLocationListener(mLocationListener);
            mLocationService.requestLocation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void startService(Context context) {
        mContext = context;
        if (isBaiduRemoteProcess(mContext)) return;
        Intent service = new Intent(mContext, MapLocationService.class);
        mContext.bindService(service, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public boolean isBaiduRemoteProcess(Context context) {
        String processName = getProcessName();
        String packageName = context.getPackageName();
        if((packageName+":remote").equals(processName)){
            Log.i(TAG,"startService return baidu.process");
            return true;
        }
        return false;
    }

    public void stopService(){
        mContext.unbindService(mServiceConnection);
        isRunning = false;
    }

    public void setLocationClientOption(LocationClientOption option){
        if(!isRunning){
            return;
        }
        if(mLocationService!=null){
            mLocationService.setLocationOption(option);
        }
    }

    public void requestLocation(){
        if(!isRunning){
            return;
        }
        if(mLocationService!=null){
            mLocationService.requestLocation();
        }
    }

    /**
     * get process name for diffrent initializations
     *
     * @return process name
     */
    public String getProcessName() {
        File cmdFile = new File("/proc/self/cmdline");

        if (cmdFile.exists() && !cmdFile.isDirectory()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cmdFile)));
                String procName = reader.readLine();

                if (!TextUtils.isEmpty(procName))
                    return procName.trim();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mContext.getApplicationInfo().processName;
    }
}
