package com.zed3.sipua.xydj.ui.groupinviteinfo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lw.demo.android.samples.R;
import com.common.widget.recyclerview.decoration.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.groupinviteinfo.adapter.GroupInviteReceiveAdapter;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap.GroupInviteReceiveData;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.GroupInvitePresenter;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.GroupInviteInfoViewer;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.OnItemClickListener;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.RecyclerViewOnItemTouchListener;

import java.util.ArrayList;
import java.util.List;

public class TabGroupInviteInfoReceiveFragment extends Fragment implements GroupInviteInfoViewer<List<GroupInviteReceiveDataMap>>,OnItemClickListener<GroupInviteReceiveData>,GroupInvitePresenter.DataHandleListener<GroupInviteReceiveData>{

    public static final String TAG = TabGroupInviteInfoReceiveFragment.class.getSimpleName();
    private List<GroupInviteReceiveDataMap> mData=new ArrayList<GroupInviteReceiveDataMap>();
    private static final int MSG_PROGRESS_HIDE = 0;
    private static final int MSG_PROGRESS_SHOW = 1;
    private static final int MSG_DATA_UPDATE = 2;
    private static final int MSG_DELETE_DATA = 3;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case MSG_DATA_UPDATE:
                    List<GroupInviteReceiveDataMap> list = (List<GroupInviteReceiveDataMap>) msg.obj;
                    mData.clear();
                    mData.addAll(list);
                    mAdapter.setData(list);
                    break;
                case MSG_PROGRESS_HIDE:

                    break;
                case MSG_PROGRESS_SHOW:

                    break;
                case MSG_DELETE_DATA:
                    mAdapter.deleteData(msg.arg1);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RecyclerView mListView;
    private GroupInviteReceiveAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private GroupInvitePresenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xydj_groupinvite_fragment_content,container,false);
        mListView = view.findViewById(R.id.datalist);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new GroupInviteReceiveAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mListView.setLayoutManager(mLayoutManager);
        mAdapter.setListener(this);
        mListView.setAdapter(mAdapter);
        mListView.addOnItemTouchListener(new RecyclerViewOnItemTouchListener());
        ItemDividerDecoration divider = new ItemDividerDecoration(getActivity(), LinearLayout.VERTICAL);
        divider.setOffset(2);
        mListView.addItemDecoration(divider);
        mPresenter = new GroupInvitePresenter();
        mPresenter.attach(this);
        mPresenter.setDataHandleListener(this);
        mPresenter.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach(this);
    }

    @Override
    public void showProgress() {
        mHandler.sendEmptyMessage(MSG_PROGRESS_SHOW);
    }

    @Override
    public void dismissProgress() {
        mHandler.sendEmptyMessage(MSG_PROGRESS_HIDE);
    }

    @Override
    public void dataLoadComplete(List<GroupInviteReceiveDataMap> obj) {
        Message msg = Message.obtain();
        msg.what = MSG_DATA_UPDATE;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onItemClick(View view, int pos,GroupInviteReceiveData data) {
        mPresenter.delete(data,pos);
    }

    @Override
    public void onDeleteSuccess(GroupInviteReceiveData data,int pos) {
        Message msg = Message.obtain();
        msg.obj = data;
        msg.what =MSG_DELETE_DATA;
        msg.arg1 = pos;
        mHandler.sendMessage(msg);
    }
}
