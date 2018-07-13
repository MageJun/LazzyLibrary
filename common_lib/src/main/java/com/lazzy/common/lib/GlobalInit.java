package com.lazzy.common.lib;

import android.content.Context;

public class GlobalInit {

    public static Context sContext;
    public static void init(Context context){
        sContext = context;
    }

    public static Context appContext(){
        return sContext;
    }
}
