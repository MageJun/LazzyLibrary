package com.android.kotlindemo.presenter

import com.android.kotlindemo.view.MainActivity
import com.android.kotlindemo.view.IViewer

class TestMainPresenter : TestBasePresenter() {
    private var viewer: MainActivity? = null

    override fun bindView(viewer: IViewer) {
        super.bindView(viewer)
        this.viewer = viewer as MainActivity
    }
}
