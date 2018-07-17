package com.android.kotlindemo.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.android.kotlindemo.R
import com.android.kotlindemo.adapter.StoryListItem
import com.android.kotlindemo.adapter.TopStoryListItem
import com.android.kotlindemo.model.bean.net.HomeNewsBean
import com.android.kotlindemo.model.bean.net.StoryBean
import com.android.kotlindemo.presenter.HomeFragmentPresenter
import com.android.kotlindemo.view.IViewer
import com.android.kotlindemo.view.activity.NewsContentActivity
import com.lazzy.common.lib.utils.L
import com.lazzy.common.lib.utils.ResourceHelper
import com.lazzy.common.lib.utils.ViewHelper
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseRecycleViewAdapter
import com.lazzy.common.lib.widget.recyclerview.adapter.OnItemClickListener
import com.lazzy.common.lib.widget.recyclerview.decoration.ItemDividerDecoration
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment(),IViewer {
    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun moreDataLoadComplete(any: Any?) {
        var tmp = any as HomeNewsBean
        mDate = tmp?.date
        mListAdapter?.insertData(tmp?.stories)
    }

    override fun dataLoadComplete(any: Any?) {
        isRefreshing = false
        mSwipeRefreshLayout?.isRefreshing=false//取消刷新
        mData=any as HomeNewsBean
        L.i(mData?.toString())
        val tmpData = ArrayList<StoryBean>()
        var topData = createTopData()
        tmpData?.add(topData)
        tmpData?.addAll(mData?.stories!!)
        mListAdapter?.setData(tmpData)
        mDate = mData?.date
    }

    private fun createTopData():StoryBean {
        val topData = StoryBean()
        topData.mode = StoryBean.Mode.VIEWPAGER
        val bundle = Bundle()
        bundle?.putParcelableArrayList("data", mData?.top_stories)
        topData?.extra = bundle
        return topData
    }

    private var mHomePresenter:HomeFragmentPresenter?=null
    private var mData:HomeNewsBean?=null
    private var mListView:RecyclerView?=null
    private var mListAdapter:BaseRecycleViewAdapter<StoryBean>?=null
    private var mSwipeRefreshLayout:SwipeRefreshLayout?=null
    private var isRefreshing = false;
    private var mDate:String?=null
    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        super.onFragmentCreate(savedInstanceState)
        mHomePresenter = HomeFragmentPresenter()
    }

    override fun onFragmentViewDestory() {
        super.onFragmentViewDestory()
        mHomePresenter?.unbindView()
    }

    override fun onFragmentViewCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var  view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        mHomePresenter?.bindView(this)
        mHomePresenter?.loadData()
        return view
    }

    private fun initView(view:View) {
        mListView=view.findViewById(R.id.listview)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        mSwipeRefreshLayout?.setColorSchemeColors(ResourceHelper.getColor(R.color.colorAccent))
        mListAdapter =MyAdapter()
        var lm = LinearLayoutManager(context)
        var decoration=ItemDividerDecoration(context,LinearLayout.VERTICAL)
        decoration.setOffset(ResourceHelper.getDimen(15))
        mListView?.addItemDecoration(decoration)
        mListView?.layoutManager = lm
        mListView?.adapter = mListAdapter

        mSwipeRefreshLayout?.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                Toast.makeText(context,"shuaxin",Toast.LENGTH_SHORT).show()
                isRefreshing = true
                mHomePresenter?.loadData()
            }

        })

        mListView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isSlideToBottom(mListView)){
                    mHomePresenter?.loadMoreData(mDate)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        mListAdapter?.setItemClickListener(object :OnItemClickListener<StoryBean>{
            override fun onItemClick(view: View?, pos: Int, data: StoryBean?, obj: Any?) {
                var intent = Intent(activity,NewsContentActivity::class.java)
                intent.putExtra("news_id",data?.id)
                activity?.startActivity(intent)
            }

            override fun onItemLongClick(view: View?, pos: Int, data: StoryBean?, obj: Any?) {
            }

        })

    }

    private class MyAdapter:BaseRecycleViewAdapter<StoryBean>(){
        override fun onCreateMulitTypeItemView() {
            addItemView(TopStoryListItem())
            addItemView(StoryListItem())
        }
    }

    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) true else false
    }

}