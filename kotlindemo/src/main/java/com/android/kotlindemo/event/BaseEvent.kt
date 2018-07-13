package com.android.kotlindemo.event

import android.os.Bundle

open abstract class BaseEvent<T> {
    var collections: List<T>?=null
    var data: T?=null
    var extras: Bundle?=null



    override fun toString(): String {
        return "BaseEvent{" +
                "collections=" + collections +
                ", data=" + data +
                ", extras=" + extras +
                '}'.toString()
    }
}
