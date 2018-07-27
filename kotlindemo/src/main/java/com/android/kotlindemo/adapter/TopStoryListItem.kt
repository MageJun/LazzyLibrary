package com.android.kotlindemo.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.android.kotlindemo.R
import com.android.kotlindemo.model.bean.net.StoryBean
import com.lazzy.common.lib.utils.ResourceHelper
import com.lazzy.common.lib.utils.ViewHelper
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

class TopStoryListItem : BaseItemView<StoryBean>() {

    private var mData = ArrayList<StoryBean>()

    private var mViewPagerAdapter: PagerAdapter =object : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view = LayoutInflater.from(container?.context).inflate(R.layout.viewpager_item_img,container,false)
            var url = mData?.get(position)?.image
            val img = view.findViewById<ImageView>(R.id.img)
            ViewHelper.setImgview(img,url)
            container?.addView(view)
            view?.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    mItemClickListener?.onItemClick(v,position,mData?.get(position),null)
                }

            });
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
    private var mCircleNavigator:CircleNavigator?=null
 /*   private val mNavigatorAdapter=object :CommonNavigatorAdapter(){
        override fun getCount(): Int {
            return mData?.size
        }

        override fun getTitleView(context: Context?, index: Int): IPagerTitleView? {
            return null
        }

        override fun getIndicator(context: Context?): IPagerIndicator? {
            val circlePageIndicator = CircleNavigator(context)
            circlePageIndicator?.circleColor=ResourceHelper.getColor(R.color.xydj_color_white)
            circlePageIndicator?.circleCount=mData?.size
            return circlePageIndicator as IPagerIndicator
        }

    }
    */

    override fun getItemView(parent: ViewGroup, viewType: Int): View? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_top_story, parent, false)
        var viewPager = view.findViewById<ViewPager>(R.id.titleViewPager)
        var indicator = view.findViewById<MagicIndicator>(R.id.indicator)
        viewPager?.adapter = mViewPagerAdapter
        mCircleNavigator = CircleNavigator(parent.context)
        mCircleNavigator?.radius = ResourceHelper.getDimen(3)
        mCircleNavigator?.circleColor = ResourceHelper.getColor(R.color.colorAccent)
        mCircleNavigator?.circleSpacing = ResourceHelper.getDimen(5)
        indicator?.navigator = mCircleNavigator
        ViewPagerHelper.bind(indicator,viewPager)
        return view
    }

    override fun onBindVH(holder: BaseViewHolder, position: Int, data: StoryBean?) {
        var listData = data?.extra?.getParcelableArrayList<StoryBean>("data")
        if(listData!=null){
            mData?.clear()
            mData?.addAll(listData)
            mViewPagerAdapter?.notifyDataSetChanged()

            mCircleNavigator?.circleCount = mData?.size
            mCircleNavigator?.notifyDataSetChanged()
        }
    }

    override fun getItemViewType(): Int {
        return ItemViewManager.obtainItemType()
    }

    override fun isForViewType(storyBean: StoryBean, position: Int): Boolean {
        return storyBean.mode === StoryBean.Mode.VIEWPAGER
    }
}
