package com.android.kotlindemo.presenter

import com.android.kotlindemo.event.NewsThemeEvent
import com.android.kotlindemo.view.activity.MainActivity
import com.android.kotlindemo.view.IViewer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivityPresenter : IBasePresenter(){

    private var viewer: MainActivity?=null;

    override fun bindView(mViewer: IViewer) {
        super.bindView(mViewer)
        viewer = mViewer as MainActivity
    }

    override fun unbindView() {
        super.unbindView()
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun handleNewsThemeEvent(event:NewsThemeEvent){
        var type = event?.getEventType();
        when(type){
            NewsThemeEvent.EventType.GET_THEMES ->{
                viewer?.dataLoadComplete(event?.data)
            }
        }
    }

}
