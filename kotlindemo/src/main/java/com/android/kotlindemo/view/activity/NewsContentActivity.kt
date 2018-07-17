package com.android.kotlindemo.view.activity

import android.os.Bundle
import com.android.kotlindemo.R
import com.android.kotlindemo.model.bean.net.NewsContentBean
import com.android.kotlindemo.presenter.NewsContentPresenter
import com.android.kotlindemo.utils.HtmlUtils
import com.lazzy.common.lib.utils.ViewHelper
import kotlinx.android.synthetic.main.activity_news_content.*

class NewsContentActivity : BaseActivity() {
    var mData:NewsContentBean?=null
    var mPresenter:NewsContentPresenter?=null

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_content)
    }

    override fun onActivityDestory() {
        mPresenter?.unbindView()
    }

    override fun initView() {
        mPresenter = NewsContentPresenter()
        mPresenter?.bindView(this)
        mPresenter?.loadData(intent?.extras?.get("news_id"))
    }

    override fun dataLoadComplete(any: Any?) {
        mData = any as NewsContentBean
//        var imgView = titleimg
        ViewHelper.setImgview(titleimg,mData?.image)

        webview.settings.javaScriptEnabled = true
        //屏幕自适应
//        webview.settings.setUseWideViewPort(true);
//        webview.settings.setLoadWithOverviewMode(true);

//        webview.loadData(mData?.body,"text/html","utf-8")
//        webview?.settings?.allowContentAccess = true
//        webview?.settings?.domStorageEnabled = true
        var data = HtmlUtils.createHtmlData(mData?.body, mData?.css, mData?.js);
        webview?.loadData(data,HtmlUtils.MIME_TYPE,HtmlUtils.ENCODING)
//        webview?.loadDataWithBaseURL(mData?.css?.get(0),mData?.body,"text/html",null,null)
    }


}
