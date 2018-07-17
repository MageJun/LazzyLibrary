package com.android.kotlindemo.presenter

import com.android.kotlindemo.event.NewsEvent
import com.android.kotlindemo.model.service.NewsManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NewsContentPresenter:IBasePresenter() {

    override fun loadData(vararg argument:Any?) {
        if(null == argument!!){
            return
        }
        NewsManager.getInstance().getNewsContent(argument[0].toString())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleNewsEvent(event:NewsEvent){
        when(event?.eventType){
            NewsEvent.EventType.NEWS_CONTENT ->{
                mViewer?.dataLoadComplete(event?.data)
            }
            else->{

            }
        }
    }
}