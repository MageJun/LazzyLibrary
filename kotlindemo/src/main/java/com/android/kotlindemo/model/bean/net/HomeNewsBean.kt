package com.android.kotlindemo.model.bean.net

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class HomeNewsBean:Parcelable,INewsBean {


    /**
     * 首页新闻体
     *
     * date : 20180717
     * stories : [{"images":["https://pic3.zhimg.com/v2-dd0eb0d5392dbb976acde9f5cf067472.jpg"],"type":0,"id":9689953,"ga_prefix":"071713","title":"吉林一药企狂犬疫苗记录造假，对普通人会造成多大影响？"},{"images":["https://pic1.zhimg.com/v2-161f832c212050c6a3ee2f540fa9663c.jpg"],"type":0,"id":9689957,"ga_prefix":"071713","title":"全国多地暴雨都快淹成海了，你千万得当心水里暗藏的危险"},{"images":["https://pic2.zhimg.com/v2-9ce0761df8dba590188532d7c6b5a689.jpg"],"type":0,"id":9689744,"ga_prefix":"071712","title":"大误 · 你更喜欢妈妈还是奶奶？"},{"images":["https://pic2.zhimg.com/v2-ec6f4b91a9974bc0bf36fdc9be01fa9d.jpg"],"type":0,"id":9689765,"ga_prefix":"071711","title":"泰迪，你为什么要对见到的一切生物（和非生物）前前后后啊\u2026\u2026"},{"images":["https://pic4.zhimg.com/v2-ee68a30eb31ebcf8d7d4de2b7e47ef9f.jpg"],"type":0,"id":9689386,"ga_prefix":"071710","title":"为了让我们都快乐，不如你请我吃饭，我请你吃饭吧"},{"images":["https://pic3.zhimg.com/v2-e9d13eac79f4619333b1a69b6d170566.jpg"],"type":0,"id":9689751,"ga_prefix":"071709","title":"一具尸体都成白骨了，法医怎么知道死因的？"},{"images":["https://pic4.zhimg.com/v2-789e68b72eefb854e93051381de2cd0b.jpg"],"type":0,"id":9689795,"ga_prefix":"071708","title":"中国式买手诞生记"},{"images":["https://pic3.zhimg.com/v2-d3cfc93c3be0d70128aa54ca3309f5d6.jpg"],"type":0,"id":9689786,"ga_prefix":"071707","title":"在工厂睡了快三个月，马斯克如何帮助特斯拉走出「生产地狱」？"},{"images":["https://pic2.zhimg.com/v2-2f2a2ec4fee3a65064449b20a7af3d19.jpg"],"type":0,"id":9689821,"ga_prefix":"071707","title":"「华帝夺冠套餐」是赚是赔我不知道，但华帝会计估计想辞职吧"},{"images":["https://pic1.zhimg.com/v2-9255ad29c7930e58bda059767ba3b5c4.jpg"],"type":0,"id":9689759,"ga_prefix":"071707","title":"25 岁时做些什么，在职业生涯后半段才不会后悔？"},{"images":["https://pic4.zhimg.com/v2-2a5f231d91b0973d277e72afd93ced97.jpg"],"type":0,"id":9689812,"ga_prefix":"071706","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic4.zhimg.com/v2-b68b12be2b2b333b881e43da549a7f7b.jpg","type":0,"id":9689957,"ga_prefix":"071713","title":"全国多地暴雨都快淹成海了，你千万得当心水里暗藏的危险"},{"image":"https://pic3.zhimg.com/v2-e80237b5190eeb8d73cca7b2296f1842.jpg","type":0,"id":9689765,"ga_prefix":"071711","title":"泰迪，你为什么要对见到的一切生物（和非生物）前前后后啊\u2026\u2026"},{"image":"https://pic1.zhimg.com/v2-85d16779f427d345e1a9459660e08940.jpg","type":0,"id":9689759,"ga_prefix":"071707","title":"25 岁时做些什么，在职业生涯后半段才不会后悔？"},{"image":"https://pic4.zhimg.com/v2-3f147ed7e7c27f311763de4d3adba41b.jpg","type":0,"id":9689751,"ga_prefix":"071709","title":"一具尸体都成白骨了，法医怎么知道死因的？"},{"image":"https://pic3.zhimg.com/v2-ec56d22668d548bdcff6a305ffc518c6.jpg","type":0,"id":9689386,"ga_prefix":"071710","title":"为了让我们都快乐，不如你请我吃饭，我请你吃饭吧"}]
     */

    var date: String? = null
    var stories: List<StoryBean>? = null
    var top_stories: ArrayList<StoryBean>? = null
    override fun toString(): String {
        return "HomeNewsBean(date=$date, stories=$stories, top_stories=$top_stories)"
    }
}
