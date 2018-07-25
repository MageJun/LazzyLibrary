package com.android.kotlindemo.model.bean.net

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class StoryBean:Parcelable {

    /**
     * 新闻内容类
     *
     * title : 中国古代家具发展到今天有两个高峰，一个两宋一个明末（多图）
     * ga_prefix : 052321
     * images : ["http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"]
     * image:top_story 用到
     * type : 0
     * id : 3930445
     *
     */

    var title: String? = null
    var ga_prefix: String? = null
    var type: Int = 0
    var id: Int = 0
    var images: List<String>? = null
    var image: String? = null
    var multipic: Boolean = false
    var mode:Mode=Mode.NORMAL
    var extra:Bundle?=null
    override fun toString(): String {
        return "StoryBean(title=$title, ga_prefix=$ga_prefix, type=$type, id=$id, images=$images, image=$image, isMultipic=$multipic)"
    }

    enum class Mode{
        NORMAL,
        IMG,
        VIEWPAGER
    }
}
