package com.android.kotlindemo.presenter

import com.android.kotlindemo.view.IViewer
import org.greenrobot.eventbus.EventBus

open abstract class IBasePresenter {
    var mViewer:IViewer?=null;

    open fun bindView(mViewer:IViewer){
        this.mViewer = mViewer
        EventBus.getDefault().register(this)
    }

    open fun unbindView(){
        mViewer=null
        EventBus.getDefault().unregister(this)
    }

    open fun loadData(vararg argument:Any?){

    }

    open  fun loadMoreData(vararg argument:Any?){

    }

}