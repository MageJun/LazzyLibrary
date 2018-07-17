package com.android.kotlindemo.view

interface IViewer {

    open fun showProgress();
    open fun hideProgress();
    open  fun dataLoadComplete(any: Any?)
    open  fun moreDataLoadComplete(any:Any?)
}