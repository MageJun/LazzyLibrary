package com.lazzy.common.lib.widget.recyclerview.helper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.lazzy.common.lib.widget.sidebar.LetterSideBarAutoAdapt;

import java.util.List;

public class RecyclerViewSideBarHelper {

    private RecyclerView mListView;
    private LetterSideBarAutoAdapt mSideBar;
    private List<? extends  ILetterData> mData;
    public RecyclerViewSideBarHelper(){}

    public void  bindSideBar(RecyclerView listView, LetterSideBarAutoAdapt sideBar, List<? extends  ILetterData> datas){
        this.mListView = listView;
        this.mSideBar = sideBar;
        this.mData = datas;
        mListView.addOnScrollListener(mScrollListener);
        mSideBar.setSelectChangedListener(mChangedListener);
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(RecyclerView.SCROLL_STATE_IDLE == newState){
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if(firstItemPosition==-1){
                        return ;
                    }
                    Log.i("FriendListTrace","mScrollListener firstItemPosition"+firstItemPosition);
                    String letter = mData.get(firstItemPosition).getLetter();
                    if(mSideBar!=null){
                        mSideBar.setCurrentLetter(letter);
                    }
                }
            }
        }
    };

    private LetterSideBarAutoAdapt.OnIndexSelectChanged mChangedListener = new LetterSideBarAutoAdapt.OnIndexSelectChanged() {
        @Override
        public void onSelectChanged(int lastIndex, int curIndex,String letter) {
            if(mData!=null&&mData.size()>curIndex){
                int pos = findCurPos(letter);
                if(pos!=-1){
                    mListView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager)  mListView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        }
    };

    private int findCurPos(String letter) {
        for (int i = 0;i<mData.size();i++){
            if(letter.equals(mData.get(i).getLetter())){
                return i;
            }
        }
        return -1;
    }

}
