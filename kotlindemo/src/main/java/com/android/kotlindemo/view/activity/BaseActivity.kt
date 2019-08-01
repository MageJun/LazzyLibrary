package com.android.kotlindemo.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.android.kotlindemo.view.IViewer

open  abstract class BaseActivity: AppCompatActivity(), IViewer {

    internal abstract  fun onActivityCreate(savedInstanceState: Bundle?)
    internal abstract fun onActivityDestory()
    internal abstract fun initView()

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun dataLoadComplete(any: Any?) {

    }

    override fun moreDataLoadComplete(any: Any?) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityCreate(savedInstanceState)
        initStatusBar();
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onActivityDestory()
    }

    fun initStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val decorView = window.decorView
                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = option
                window.statusBarColor = Color.TRANSPARENT
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }



}