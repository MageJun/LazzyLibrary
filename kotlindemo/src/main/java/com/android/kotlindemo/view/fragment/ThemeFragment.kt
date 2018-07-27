package com.android.kotlindemo.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.android.kotlindemo.R
import com.android.kotlindemo.adapter.StoryListItem
import com.android.kotlindemo.adapter.TopStoryListItem2
import com.android.kotlindemo.model.bean.net.HomeNewsBean
import com.android.kotlindemo.model.bean.net.NewsThemeBean
import com.android.kotlindemo.model.bean.net.StoryBean
import com.android.kotlindemo.model.bean.net.ThemeNewsBean
import com.android.kotlindemo.presenter.ThemeFragmentPresenter
import com.android.kotlindemo.view.IViewer
import com.android.kotlindemo.view.activity.NewsContentActivity
import com.lazzy.common.lib.utils.L
import com.lazzy.common.lib.utils.ResourceHelper
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseRecycleViewAdapter
import com.lazzy.common.lib.widget.recyclerview.adapter.OnItemClickListener
import com.lazzy.common.lib.widget.recyclerview.decoration.ItemDividerDecoration

class ThemeFragment: BaseFragment(),IViewer {

    private var mThemeBean:NewsThemeBean.ThemeBean?=null
    private var mProgressLine:View?=null

    public fun setThemeBean(themeBean:NewsThemeBean.ThemeBean){
        this.mThemeBean = themeBean
        if(mThemeBean!=null&&mThemePresenter!=null){
            mThemePresenter?.loadData(mThemeBean?.id)
        }
    }

    override fun showProgress() {
        mProgressLine?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        mProgressLine?.visibility = View.GONE
    }

    override fun moreDataLoadComplete(any: Any?) {
        var tmp = any as ThemeNewsBean
        mLastStoryID = tmp?.stories?.size?.minus(1)?.let { tmp?.stories?.get(it)?.id }
        mListAdapter?.insertData(tmp?.stories)
    }

    override fun dataLoadComplete(any: Any?) {
        isRefreshing = false
        mSwipeRefreshLayout?.isRefreshing=false//取消刷新
        mData=any as ThemeNewsBean
        L.i(mData?.toString())
        val tmpData = ArrayList<StoryBean>()
        var topData = createTopData()
        tmpData?.add(topData)
        tmpData?.addAll(mData?.stories!!)
        mListAdapter?.setData(tmpData)
        mLastStoryID = mData?.stories?.size?.minus(1)?.let { mData?.stories?.get(it)?.id }
    }

    private fun createTopData():StoryBean {
        val topData = StoryBean()
        topData.mode = StoryBean.Mode.IMG
        topData?.image = mData?.background
        val bundle = Bundle()
        bundle?.putParcelableArrayList("editors", mData?.editors)
        topData?.extra = bundle
        return topData
    }

    private var mThemePresenter:ThemeFragmentPresenter?=null
    private var mData:ThemeNewsBean?=null
    private var mListView:RecyclerView?=null
    private var mListAdapter:BaseRecycleViewAdapter<StoryBean>?=null
    private var mSwipeRefreshLayout:SwipeRefreshLayout?=null
    private var isRefreshing = false;
    private var mLastStoryID:Int?=null
    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        super.onFragmentCreate(savedInstanceState)
        mThemePresenter = ThemeFragmentPresenter()
    }

    override fun onFragmentViewDestory() {
        super.onFragmentViewDestory()
        mThemePresenter?.unbindView()
    }

    override fun onFragmentViewCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var  view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        mThemePresenter?.bindView(this)
        mThemePresenter?.loadData(mThemeBean?.id)
        return view
    }

    private fun initView(view:View) {
        mListView=view.findViewById(R.id.listview)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        mSwipeRefreshLayout?.setColorSchemeColors(ResourceHelper.getColor(R.color.colorAccent))
        mListAdapter = MyAdapter()
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
                mThemePresenter?.loadData(mThemeBean?.id)
            }

        })

        mListView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(isSlideToBottom(mListView)){
                    mThemePresenter?.loadMoreData(mThemeBean?.id,mLastStoryID)
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
        mProgressLine = view?.findViewById(R.id.progress_line)
    }

    private class MyAdapter:BaseRecycleViewAdapter<StoryBean>(){
        override fun onCreateMulitTypeItemView() {
            addItemView(TopStoryListItem2())
            addItemView(StoryListItem())
        }
    }

    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) true else false
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }

}