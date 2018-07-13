package com.android.kotlindemo

import android.app.Application
import android.content.Context
import com.android.kotlindemo.utils.net.XUtil
import com.lazzy.common.lib.GlobalInit

class AppApplication: Application() {
    companion object {
       private var sContext:AppApplication?=null

        fun appContext()=sContext!!
    }
    override fun onCreate() {
        super.onCreate()
        sContext = this
        GlobalInit.init(sContext)
        XUtil.init(this)
    }

}