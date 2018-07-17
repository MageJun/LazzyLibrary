package com.android.kotlindemo.adapter

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

class StoryListItem : BaseItemView<StoryBean>() {

    override fun getItemView(parent: ViewGroup, viewType: Int): View {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_story, parent, false)
        return LayoutInflater.from(parent.context).inflate(R.layout.item_news_story, parent, false)
    }

    override fun onBindVH(holder: BaseViewHolder, position: Int, data: StoryBean) {
        val img = holder.getView<ImageView>(R.id.img)
        val imags = data.images
        if (imags != null && imags.size > 0) {
            ViewHelper.setImgview(img, imags[0])
        }
        holder.setText(R.id.title, data.title)
        holder?.itemView?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                mItemClickListener?.onItemClick(v,position,data,null)

            }

        });
    }

    override fun getItemViewType(): Int {
        return ItemViewManager.obtainItemType()
    }

    override fun isForViewType(t: StoryBean?, position: Int): Boolean {
        return t?.mode==StoryBean.Mode.NORMAL
    }
}
