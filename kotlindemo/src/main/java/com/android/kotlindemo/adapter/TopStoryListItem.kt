package com.android.kotlindemo.adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.android.kotlindemo.R
import com.android.kotlindemo.model.bean.net.StoryBean
import com.lazzy.common.lib.utils.ViewHelper
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager

class TopStoryListItem : BaseItemView<StoryBean>() {

    private var mData = ArrayList<StoryBean>()

    private var mViewPagerAdapter: PagerAdapter =object : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view = LayoutInflater.from(container?.context).inflate(R.layout.viewpager_item_img,container,false)
            var url = mData?.get(position)?.image
            val img = view.findViewById<ImageView>(R.id.img)
            ViewHelper.setImgview(img,url)
            container?.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container?.removeView(`object`as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view==`object`
        }

        override fun getCount(): Int {
            return mData?.size!!
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }

    }

    override fun getItemView(parent: ViewGroup, viewType: Int): View? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_top_story, parent, false)
        var viewPager = view.findViewById<ViewPager>(R.id.titleViewPager)
        viewPager?.adapter = mViewPagerAdapter
        return view
    }

    override fun onBindVH(holder: BaseViewHolder, position: Int, data: StoryBean?) {
        var listData = data?.extra?.getParcelableArrayList<StoryBean>("data")
        if(listData!=null){
            mData?.clear()
            mData?.addAll(listData)
            mViewPagerAdapter?.notifyDataSetChanged()
        }
    }

    override fun getItemViewType(): Int {
        return ItemViewManager.obtainItemType()
    }

    override fun isForViewType(storyBean: StoryBean, position: Int): Boolean {
        return storyBean.mode === StoryBean.Mode.VIEWPAGER
    }
}
