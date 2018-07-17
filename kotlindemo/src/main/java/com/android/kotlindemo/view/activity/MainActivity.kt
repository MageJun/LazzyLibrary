package com.android.kotlindemo.view.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBarDrawerToggle
import com.android.kotlindemo.R
import com.android.kotlindemo.model.service.NewsThemeListManager
import com.android.kotlindemo.presenter.MainActivityPresenter
import com.android.kotlindemo.view.fragment.HomeFragment
import com.lazzy.common.lib.utils.ViewHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.naviagtion_header_layout.*

class MainActivity : BaseActivity() {

    private var mPresenter: MainActivityPresenter?=null
    private var mHomeFragment:HomeFragment?=null

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

        navigationview?.itemIconTintList=null
        navigationview?.itemTextColor=null

        var statusBar = ViewHelper.getStatusBar();
        navigation_statusbar?.layoutParams?.height = statusBar
        invisibleScrollbar()

        updateThemeListShow();

        initFragment()
    }

    private fun initFragment() {
        if(mHomeFragment==null){
            mHomeFragment = HomeFragment()
        }
        var transaction = supportFragmentManager?.beginTransaction()
        transaction?.add(R.id.content,mHomeFragment/*TestHomeFragment()*/)
        transaction?.commit()

    }

    override fun onActivityDestory() {
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
        menu.removeGroup(R.id.news_theme_list)
        for (i in 0..(size-1)){
            var title = themeList.get(i).name
            menu.add(R.id.news_theme_list,i,i,title)
        }
    }


}
