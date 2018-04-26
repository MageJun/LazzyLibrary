package com.lw.android.demo.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.lw.android.demo.R;
import com.lw.android.demo.model.PersonalData;
import com.lw.android.demo.presenter.RecyclerViewPresenter;
import com.lw.android.demo.view.adapter.RecyclerViewAdapter;
import com.lw.android.demo.view.divider.RecyclerViewStaggeredGridDivider;
import com.lw.android.demo.view.helper.CustomItemTouchHelperCallBack;

import java.util.List;

public class MainActivity extends BaseActivity implements RecyclerViewer<PersonalData> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private View content;
    private RecyclerViewAdapter mAdapter;
    private RecyclerViewPresenter mPresenter;
    private static final int MSG_UPDATE = 1;
    private static final int MSG_PROGRESS_SHOW = 2;
    private static final int MSG_PROGRESS_HIDE = 3;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_UPDATE:
                    List<PersonalData> datas = (List<PersonalData>) msg.obj;
                    mAdapter.updateData(datas);
                    break;
                case MSG_PROGRESS_SHOW:
                    content.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case MSG_PROGRESS_HIDE:
                    content.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initDate();

    }

    private void initDate() {

        mAdapter = new RecyclerViewAdapter(this);
        //RecyclerView 必须设置Adapter和LayoutManager之后才会生效
        mRecyclerView.setAdapter(mAdapter);
        int spanCount = 3;
        //线性，相当于普通ListView
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        //网格，相当于GridView
        GridLayoutManager gManager = new GridLayoutManager(this,spanCount);
        //瀑布流
        StaggeredGridLayoutManager sManager = new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);
//        sManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//解决滑动时Item互换的问题
        mRecyclerView.setLayoutManager(sManager);
        RecyclerViewStaggeredGridDivider divider = new RecyclerViewStaggeredGridDivider(spanCount);
        divider.setSpace(8);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setPadding(8,0,8,8);

        //添加滑动删除
        ItemTouchHelper.Callback callBack = new CustomItemTouchHelperCallBack(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(mRecyclerView);

        mPresenter = new RecyclerViewPresenter(this);


        requestDate();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress);
        content = findViewById(R.id.content);
    }

    @Override
    public void requestDate() {
        mPresenter.getData();
    }

    @Override
    public void showProgress() {
        Log.i(TAG,"showProgress");
        mHandler.sendEmptyMessage(MSG_PROGRESS_SHOW);
    }

    @Override
    public void hideProgress() {
        Log.i(TAG,"hideProgress");
        mHandler.sendEmptyMessage(MSG_PROGRESS_HIDE);
    }

    @Override
    public void onDataNotifytion(List<PersonalData> datas) {
        Log.i(TAG,"onDataNotifytion");
        Message msg = Message.obtain();
        msg.what = MSG_UPDATE;
        msg.obj = datas;
        mHandler.sendMessage(msg);
    }
}
