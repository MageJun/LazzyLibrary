package com.android.kotlindemo.event

import com.android.kotlindemo.model.bean.net.INewsBean

class NewsEvent: BaseEvent<INewsBean>() {
    var eventType:EventType?=null

    enum class EventType{
        UNKNOW,
        HOME_NEWS,
        THEME_NEWS,
        MORE_NEWS,
        MORE_THEME_NEWS,
        NEWS_CONTENT
    }
}