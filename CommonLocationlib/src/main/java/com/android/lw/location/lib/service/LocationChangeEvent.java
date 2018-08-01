package com.android.lw.location.lib.service;


import com.baidu.location.BDLocation;

/**
 * 位置变化Event事件
 */
public class LocationChangeEvent {
    private BDLocation bdLocation;


  /*  public LocationChangeEvent(BDLocation bdLocation, LatLng latLng) {
        this.bdLocation = bdLocation;
        this.latLng = latLng;
    }*/

    public LocationChangeEvent(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }


    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }



    @Override
    public String toString() {
        return "LocationChangeEvent{" +
                "bdLocation=" + bdLocation +
                '}';
    }
}
