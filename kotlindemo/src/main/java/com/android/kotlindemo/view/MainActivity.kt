package com.android.kotlindemo.view

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.android.kotlindemo.R
import com.android.kotlindemo.model.service.NewsThemeListManager
import com.android.kotlindemo.presenter.MainActivityPresenter
import com.lazzy.common.lib.utils.ViewHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.naviagtion_header_layout.*

class MainActivity : BaseActivity() {

    private var mPresenter: MainActivityPresenter?=null
    override  fun onActivityCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        mPresenter= MainActivityPresenter()
        mPresenter?.bindView(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        var toggle = ActionBarDrawerToggle(this,drawerlayout,toolbar,0,0);
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        var statusBar = ViewHelper.getStatusBar();
        navigation_statusbar?.layoutParams?.height = statusBar
        invisibleScrollbar()

        updateThemeListShow();
    }

    override fun onActivityDestory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        mPresenter?.unbindView()
    }

    override fun dataLoadComplete(any: Any?) {
        updateThemeListShow()
    }


    private fun  invisibleScrollbar(){
        navigationview?.getChildAt(0)?.setVerticalScrollBarEnabled(false)
    }


    private fun updateThemeListShow() {
        var newsThemeBean = NewsThemeListManager.getInstance()?.getNewsThemeBean();
        if(newsThemeBean==null){
            return ;
        }
        var themeList = newsThemeBean.others
        var size = themeList!!.size;
        var  menu = navigationview.getMenu()
        menu.clear()
        for (i in 0..size){
            var title = themeList.get(i).name
            menu.add(i,i,i,title)
        }
    }


}
