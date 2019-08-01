package com.android.kotlindemo

import android.app.Application
import android.text.TextUtils
import com.android.kotlindemo.utils.net.XUtil
import com.android.lw.map.lib.location.service.LocationServiceManager
import com.lazzy.common.lib.GlobalInit
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class AppApplication: Application() {
    companion object {
       private var sContext:AppApplication?=null

        fun appContext()=sContext!!

    }
    override fun onCreate() {
        super.onCreate()
        sContext = this
        if (LocationServiceManager.getInstance().isBaiduRemoteProcess(this)) {
            //百度地图的进程不进行应用用到的初始化操作
            return
        }
        GlobalInit.init(sContext)
        XUtil.init(this)
        LocationServiceManager.getInstance().startService(this)
//        LocationServiceManager.getInstance().requestLocation()
    }

    /**
     * get process name for diffrent initializations
     *
     * @return process name
     */
    fun getProcessNameInner(): String {
        val cmdFile = File("/proc/self/cmdline")

        if (cmdFile.exists() && !cmdFile.isDirectory) {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(InputStreamReader(FileInputStream(cmdFile)))
                val procName = reader.readLine()

                if (!TextUtils.isEmpty(procName))
                    return procName.trim { it <= ' ' }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
        return applicationInfo.processName
    }

}