package com.android.kotlindemo.presenter

import com.android.kotlindemo.view.IViewer

open abstract class IBasePresenter {
    var mViewer:IViewer?=null;

    open fun bindView(mViewer:IViewer){}

    open fun unbindView(){
        mViewer=null
    }

}