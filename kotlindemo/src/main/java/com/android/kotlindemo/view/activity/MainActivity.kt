package com.android.kotlindemo.view.activity

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import com.android.kotlindemo.R
import com.android.kotlindemo.model.bean.net.NewsThemeBean
import com.android.kotlindemo.model.service.NewsThemeListManager
import com.android.kotlindemo.presenter.MainActivityPresenter
import com.android.kotlindemo.view.fragment.HomeFragment
import com.android.kotlindemo.view.fragment.ThemeFragment
import com.lazzy.common.lib.utils.ResourceHelper
import com.lazzy.common.lib.utils.ViewHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.naviagtion_header_layout.*

class MainActivity : BaseActivity(),View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.homeline ->{
                switchHomeFragment()
                updateTitleText(ResourceHelper.getStr(R.string.home))
                drawerlayout?.closeDrawers()
            }
            else ->{

            }
        }
    }

    private var mPresenter: MainActivityPresenter?=null
    private var mHomeFragment:HomeFragment?=null
    private var mThemeFragment:ThemeFragment?=null

    override  fun onActivityCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        mPresenter= MainActivityPresenter()
        mPresenter?.bindView(this)
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        var toggle = ActionBarDrawerToggle(this,drawerlayout,toolbar_main,0,0);
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationview?.itemIconTintList=null
        navigationview?.itemTextColor=null

        var statusBar = ViewHelper.getStatusBar();
        navigation_statusbar?.layoutParams?.height = statusBar
        invisibleScrollbar()

        updateThemeListShow();

        initFragment()

        updateTitleText(ResourceHelper.getStr(R.string.home))

        initListener()
    }

    private fun initListener(){
        var homeline = navigationview?.getHeaderView(0)?.findViewById<View>(R.id.homeline)
        homeline?.setOnClickListener(this)
        navigationview?.setNavigationItemSelectedListener( object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var  titleMsg = item?.title
                updateTitleText(titleMsg?.toString()!!)
                var newsThemeBean = NewsThemeListManager.getInstance().getNewsThemeBean()
                if(newsThemeBean!=null){
                    var  themeBeanList = newsThemeBean.others
                    var index = item?.itemId
                    if(themeBeanList?.size!! >index){
                        var themBean = themeBeanList?.get(index)
                        switchThemeFragment(themBean)
                    }
                }
                drawerlayout?.closeDrawers()
                return true
            }
        })
    }

    private fun initFragment() {
        if(mHomeFragment==null){
            mHomeFragment = HomeFragment()
        }
        if(mThemeFragment==null){
            mThemeFragment = ThemeFragment()
        }
        var transaction = supportFragmentManager?.beginTransaction()
        transaction?.add(R.id.content, mHomeFragment!!/*TestHomeFragment()*/)
        transaction?.add(R.id.content, mThemeFragment!!)
        transaction?.hide(mThemeFragment!!)
        transaction?.commit()

        mCurrentFragment = mHomeFragment

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

    private fun updateTitleText(title:String){
        supportActionBar?.title=title
    }

    private var mCurrentFragment: Fragment? = null;

    private fun switchHomeFragment(){
        if(mCurrentFragment == mHomeFragment){
            return
        }
        var  transaction = supportFragmentManager?.beginTransaction()
       /* if(mHomeFragment==null){
            mHomeFragment = HomeFragment();
            transaction?.add(R.id.content,mHomeFragment)
        }*/
        transaction?.show(mHomeFragment!!)
        transaction?.hide(mThemeFragment!!)
        transaction?.commit()
        mCurrentFragment = mHomeFragment
    }

    private fun switchThemeFragment(bean:NewsThemeBean.ThemeBean){
        if(bean == null){
            return
        }
        mThemeFragment?.setThemeBean(bean)
        if(mCurrentFragment == mThemeFragment){
            return
        }
        var  transaction = supportFragmentManager?.beginTransaction()
      /*  if(mThemeFragment == null){
            mThemeFragment = ThemeFragment()
            transaction?.add(R.id.content,mThemeFragment)
        }*/
        transaction?.hide(mHomeFragment!!)
        transaction?.show(mThemeFragment!!)
        transaction?.commit()
        mCurrentFragment = mThemeFragment

    }


}
