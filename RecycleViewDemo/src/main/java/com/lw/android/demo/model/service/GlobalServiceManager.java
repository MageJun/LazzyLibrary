package com.lw.android.demo.model.service;

/**
 * Created by zed on 2018/4/14.
 */

public class GlobalServiceManager {

    public static  IPersonService getIPersonService(){
       return  PersonServiceFactory.getInstance().newPersonServiceInstance();
    }

}
