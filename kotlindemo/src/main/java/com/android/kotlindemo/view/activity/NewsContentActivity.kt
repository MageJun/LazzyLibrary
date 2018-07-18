package com.android.kotlindemo.view.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.android.kotlindemo.R
import com.android.kotlindemo.model.bean.net.NewsContentBean
import com.android.kotlindemo.presenter.NewsContentPresenter
import com.android.kotlindemo.utils.HtmlUtils
import com.lazzy.common.lib.utils.L
import com.lazzy.common.lib.utils.ResourceHelper
import com.lazzy.common.lib.utils.ViewHelper
import kotlinx.android.synthetic.main.activity_news_content.*
import android.view.KeyEvent.KEYCODE_BACK



class NewsContentActivity : BaseActivity() {
    var mData: NewsContentBean? = null
    var mPresenter: NewsContentPresenter? = null

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_content)
    }

    override fun onActivityDestory() {
        mPresenter?.unbindView()
    }

    override fun initView() {
        var statusBar = ViewHelper.getStatusBar()
        toolbar?.layoutParams?.height = (ResourceHelper.getDimen(50) + statusBar)
        toolbar?.setPadding(0, statusBar, 0, 0)

        appbarLayout?.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            /**
             * 第二个参数是AppBarLayout的Y轴偏移量，
             * 当AppBarLayout处于扩展状态时，verticalOffset=0;
             * 当AppBarLayout处于折叠状态时，verticalOffset=-appBarLayout.getTotalScrollRange()，注意是负的;
             * 当处于滚动状态时，verticalOffset 从0 到 负的appBarLayout.getTotalScrollRange()之间变化，所以这里使用Math.abs来取绝对值。
             * 当向上滚动时，Math.abs(verticalOffset*1.0f)越来越大，
             * 向下滚动时，Math.abs(verticalOffset*1.0f)越来越小，
             * 通过Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange() 计算偏移量的百分比来改变Toolbar背景透明度
             */
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                var percent = Math.abs(verticalOffset*1.0f)/(appBarLayout?.totalScrollRange!!)
                L.i("onOffsetChanged verticalOffset = "+percent)
               /* if(percent==1.0f){
                    appBarLayout?.visibility= View.GONE
                    return
                }else{
                    appBarLayout?.visibility= View.VISIBLE
                }*/

                //监听滚动状态，改变toolBar的颜色
                toolbar?.setBackgroundColor(changeAlpha(ResourceHelper.getColor(R.color.colorAccent),1.0f-Math.abs(verticalOffset*1.0f)/(appBarLayout?.totalScrollRange!!)))

            }

        })
        mPresenter = NewsContentPresenter()
        mPresenter?.bindView(this)
        mPresenter?.loadData(intent?.extras?.get("news_id"))
    }

    /** 根据百分比改变颜色透明度  */
    fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction)?.toInt()
        return Color.argb(alpha, red, green, blue)
    }

    override fun dataLoadComplete(any: Any?) {
        mData = any as NewsContentBean
//        var imgView = titleimg
        ViewHelper.setImgview(titleimg, mData?.image)

        webview.settings.javaScriptEnabled = true
        //屏幕自适应
//        webview.settings.setUseWideViewPort(true);
//        webview.settings.setLoadWithOverviewMode(true);

//        webview.loadData(mData?.body,"text/html","utf-8")
//        webview?.settings?.allowContentAccess = true
//        webview?.settings?.domStorageEnabled = true
        var data = HtmlUtils.createHtmlData(mData?.body, mData?.css, mData?.js);
        webview?.webViewClient=object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Toast.makeText(this@NewsContentActivity,"加载完成！",Toast.LENGTH_SHORT).show()
                updateSection()
            }
        }
        webview?.loadData(data, HtmlUtils.MIME_TYPE, HtmlUtils.ENCODING)
    }

    fun updateSection(){
        if(mData?.section!=null){
            section_layout?.visibility=View.VISIBLE
            ViewHelper.setImgview(section_img,mData?.section?.thumbnail)
            ViewHelper.setText(section_name,ResourceHelper.getStr(R.string.section_name,mData?.section?.name))

        }else{
            section_layout?.visibility=View.GONE
        }
    }



   override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview?.canGoBack()!!) {
            webview?.goBack()
            return true
        } else {
            onBackPressed()
        }
        return super.onKeyDown(keyCode, event)
    }


}
