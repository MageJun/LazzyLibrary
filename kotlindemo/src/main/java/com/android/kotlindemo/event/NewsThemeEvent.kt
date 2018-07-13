package com.android.kotlindemo.event

import com.android.kotlindemo.model.bean.net.NewsThemeBean

class NewsThemeEvent: BaseEvent<NewsThemeBean>() {
    private var eventType:EventType=EventType.UNKNOWN
    public fun getEventType()=eventType
    public fun setEventType(eventType: EventType){
        this.eventType = eventType
    }
    enum class EventType{
        GET_THEMES,
        UNKNOWN
    }
}