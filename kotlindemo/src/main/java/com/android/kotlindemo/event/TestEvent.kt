package com.android.kotlindemo.event

class TestEvent {

    var type: EventType? = null

    enum class EventType {
        GETLIST
    }
}
