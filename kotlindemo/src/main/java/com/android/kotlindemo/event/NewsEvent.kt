package com.android.kotlindemo.event

import com.android.kotlindemo.model.bean.net.INewsBean

class NewsEvent: BaseEvent<INewsBean>() {
    var eventType:EventType?=null

    enum class EventType{
        UNKNOW,
        HOME_NEWS,
        OTEHR_NEWS,
        MORE_NEWS,
        NEWS_CONTENT
    }
}