package com.android.kotlindemo.model.bean.net

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NewsContentBean : INewsBean,Parcelable {

    /**
     *
     * 新闻内容页
     *
     * body :内容html   <div class="main-wrap content-wrap">
     *
     * image_source : 图片来源   《辩护人》
     * title :  标题   《我不是药神》的导演说，他想拍这样的电影
     * image :文章内显示的图片url   https://pic3.zhimg.com/v2-a0b031ec7ce54ad32793f3c88c94e75e.jpg
     * share_url :分享链接   http://daily.zhihu.com/story/9689806
     * js : JS []
     * id : 9689806
     * ga_prefix :用来做google处理  071721
     * images : 列表显示的图片 ["https://pic1.zhimg.com/v2-fdf4735ae290adac71b29d3b053f39c4.jpg"]
     * type : 类型 0
     * section : 栏目的信息{"thumbnail": "https://pic1.zhimg.com/v2-fdf4735ae290adac71b29d3b053f39c4.jpg","name":"放映机","id":28}
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
    </div> */

    var body: String? = null
    var image_source: String? = null
    var title: String? = null
    var image: String? = null
    var share_url: String? = null
    var id: Int = 0
    var ga_prefix: String? = null
    var type: Int = 0
    var section: SectionBean? = null
    var js: List<String>? = null
    var images: List<String>? = null
    var css: List<String>? = null

    @Parcelize
    class SectionBean:Parcelable{
        /**
         * thumbnail :栏目缩略图 https://pic1.zhimg.com/v2-fdf4735ae290adac71b29d3b053f39c4.jpg
         * name : 栏目名 放映机
         * id : 28
         */

        var thumbnail: String? = null
        var name: String? = null
        var id: Int = 0
    }
}
