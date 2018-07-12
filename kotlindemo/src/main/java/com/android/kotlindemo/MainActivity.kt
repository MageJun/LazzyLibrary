package com.android.kotlindemo

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import com.android.kotlindemo.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun initView() {
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        var toggle = ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close);
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()


    }
}
