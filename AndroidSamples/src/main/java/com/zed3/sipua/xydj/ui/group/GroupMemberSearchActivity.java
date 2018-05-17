package com.zed3.sipua.xydj.ui.group;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.GridDividerDecoration;
import com.zed3.sipua.xydj.ui.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.group.adapter.GroupMemberSearchResultAdapter;
import com.zed3.sipua.xydj.ui.group.adapter.OnItemClickListener;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;
import com.zed3.sipua.xydj.ui.group.helper.GroupMemberSearchPresenter;
import com.zed3.sipua.xydj.ui.group.helper.GroupMemberSearchViewer;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberSearchActivity extends BaseActivity implements GroupMemberSearchViewer<CustomGroupMemberInfo>{

    private PttCustomGrp mGrp;
    private EditText mEditText;
    private TextView mTvNoResult;
    private RecyclerView mRecyclerView;
    private View mSearchResultLine;
    private List<CustomGroupMemberInfo> mResultList = new ArrayList<CustomGroupMemberInfo>();
    private GroupMemberSearchPresenter mPresenter;
    private GroupMemberSearchResultAdapter mAdapter;

    private final static int MSG_SHOW_PROGRESS = 0;
    private final static int MSG_DISMISS_PROGRESS = 1;
    private final static int MSG_UPDATE_SEARCH_RESULTS = 2;

    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_DISMISS_PROGRESS:

                    break;
                case MSG_SHOW_PROGRESS:

                    break;
                case MSG_UPDATE_SEARCH_RESULTS:
                    updateSearchResultShow();
                    mAdapter.setData(mResultList);
                    break;
            }
        }
    };

    private OnItemClickListener<CustomGroupMemberInfo> mItemClickListener = new OnItemClickListener<CustomGroupMemberInfo>() {
        @Override
        public void onItemClick(View v, int pos) {

        }

        @Override
        public void onDataItemClick(View v, int pos, CustomGroupMemberInfo customGroupMemberInfo) {
            Intent intent = new Intent(GroupMemberSearchActivity.this,GroupMemeberDetailActivity.class);
            intent.putExtra("memberInfo",customGroupMemberInfo);
            startActivity(intent);
        }

        @Override
        public void onAddItemClick() {

        }

        @Override
        public void onSubTraction() {

        }
    };

    @Override
    public boolean isShowTitleLeftBack() {
        return true;
    }

    @Override
    public boolean isShowTitleCenter() {
        return true;
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_group_member_search);
        initView();
        intiData();

        updateSearchResultShow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach(this);
        mPresenter.unBindData();
    }

    private void intiData() {
        Object obj = getIntent().getExtras().get("grp");
        if(obj!=null && obj instanceof PttCustomGrp){
            mGrp = (PttCustomGrp) obj;
        }
        mPresenter = new GroupMemberSearchPresenter();
        mPresenter.attach(this);
        mPresenter.bindData(mGrp);
        mAdapter = new GroupMemberSearchResultAdapter();
        ItemDividerDecoration decoration = new ItemDividerDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setOffset(2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(mItemClickListener);

    }

    private void initView() {
        setTitleCenterText("搜索好友");
        mEditText = findViewById(R.id.group_memeber_search);
        calcLeftDrawableSize();
        mSearchResultLine = findViewById(R.id.search_result_line);
        mRecyclerView = findViewById(R.id.search_result_listview);
        mTvNoResult = findViewById(R.id.tv_no_result);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(mEditText.getText().toString()))
                    updateSearchResultShow();
            }
        });
    }

    private void updateSearchResultShow(){
        String searchKey = mEditText.getText().toString();
        if(TextUtils.isEmpty(searchKey)){
            mSearchResultLine.setVisibility(View.GONE);
        }else{
            mSearchResultLine.setVisibility(View.VISIBLE);
        }

        if(mResultList.size()==0){
            mTvNoResult.setVisibility(View.VISIBLE);
        }else{
            mTvNoResult.setVisibility(View.GONE);
        }
    }

    private void calcLeftDrawableSize() {
        Drawable leftDrawable = mEditText.getCompoundDrawables()[0];
        if(leftDrawable!=null){
            int ldW = getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            int ldH = getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            leftDrawable.setBounds(0,0,ldW,ldH);
            mEditText.setCompoundDrawables(leftDrawable,mEditText.getCompoundDrawables()[1],mEditText.getCompoundDrawables()[2],mEditText.getCompoundDrawables()[3]);
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_search:
                mPresenter.search(mEditText.getText().toString());
                break;
        }
    }

    @Override
    public void showProgress() {
        mHander.sendEmptyMessage(MSG_SHOW_PROGRESS);
    }

    @Override
    public void dismissProgress() {
        mHander.sendEmptyMessage(MSG_DISMISS_PROGRESS);
    }

    @Override
    public void onSearchResult(List<CustomGroupMemberInfo> results) {
        mResultList.clear();
        mResultList.addAll(results);
        mHander.sendEmptyMessage(MSG_UPDATE_SEARCH_RESULTS);
    }
}
