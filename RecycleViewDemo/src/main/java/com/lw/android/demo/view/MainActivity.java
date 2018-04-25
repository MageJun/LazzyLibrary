package com.lw.android.demo.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.lw.android.demo.R;
import com.lw.android.demo.model.PersonalData;
import com.lw.android.demo.presenter.RecyclerViewPresenter;
import com.lw.android.demo.view.adapter.RecyclerViewAdapter;

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
        //线性，相当于普通ListView
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        //网格，相当于GridView
        GridLayoutManager gManager = new GridLayoutManager(this,3);
        //瀑布流
        StaggeredGridLayoutManager sManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(sManager);

        mPresenter = new RecyclerViewPresenter(this);

        requestDate();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
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
