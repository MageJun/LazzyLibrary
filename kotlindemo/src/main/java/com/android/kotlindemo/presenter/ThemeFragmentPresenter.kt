package com.android.kotlindemo.presenter

import com.android.kotlindemo.event.NewsEvent
import com.android.kotlindemo.model.service.NewsManager
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ThemeFragmentPresenter:IBasePresenter() {

    override fun loadData(vararg argument:Any?) {
        if(argument ==null||argument[0]==null){
            return ;
        }
        var  themeID = argument[0] as Int
        mViewer?.showProgress()
        NewsManager.getInstance().getThemeNewsByThemeID(themeID)
    }

    override fun loadMoreData(vararg argument: Any?) {
        super.loadMoreData(*argument)
        if(argument ==null||argument[0]==null||argument[1]==null){
            return ;
        }
        var  themeId = argument[0] as Int
        var  storyID = argument[1] as Int
        NewsManager.getInstance().getMoreThemeNewsByThemeID(themeId,storyID)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleHomeNewsEvent(event:NewsEvent){
        when(event?.eventType){
            NewsEvent.EventType.THEME_NEWS ->{
                mViewer?.dataLoadComplete(event?.data)
                mViewer?.hideProgress()
            }
            NewsEvent.EventType.MORE_THEME_NEWS ->{
                mViewer?.moreDataLoadComplete(event?.data)
            }
            else->{

            }
        }
    }
}