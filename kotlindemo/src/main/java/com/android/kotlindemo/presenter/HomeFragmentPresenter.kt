package com.android.kotlindemo.presenter

import com.android.kotlindemo.event.NewsEvent
import com.android.kotlindemo.model.service.NewsManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragmentPresenter:IBasePresenter() {

    override fun loadData(vararg argument:Any?) {
        NewsManager.getInstance().getHomeNews()
    }

    override fun loadMoreData(vararg argument: Any?) {
        super.loadMoreData(*argument)
        if(argument ==null){
            return ;
        }
        var  date = argument[0] as String
        NewsManager.getInstance().getBerforeNews(date)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleHomeNewsEvent(event:NewsEvent){
        when(event?.eventType){
            NewsEvent.EventType.HOME_NEWS ->{
                mViewer?.dataLoadComplete(event?.data)
            }
            NewsEvent.EventType.MORE_NEWS ->{
                mViewer?.moreDataLoadComplete(event?.data)
            }
            else->{

            }
        }
    }
}