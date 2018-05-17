package com.zed3.sipua.xydj.ui.group;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;
import com.zed3.sipua.xydj.ui.group.helper.GroupMemberSearchPresenter;
import com.zed3.sipua.xydj.ui.group.helper.GroupMemberSearchViewer;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberSearchActivity extends BaseActivity implements GroupMemberSearchViewer<CustomGroupMemberInfo>{

    private PttCustomGrp mGrp;
    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private View mSearchResultLine;
    private List<CustomGroupMemberInfo> mResultList = new ArrayList<CustomGroupMemberInfo>();
    private GroupMemberSearchPresenter mPresenter;

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

                    break;
            }
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
    }

    private void initView() {
        setTitleCenterText("搜索好友");
        mEditText = findViewById(R.id.group_memeber_search);
        calcLeftDrawableSize();
        mSearchResultLine = findViewById(R.id.search_result_line);
        mRecyclerView = findViewById(R.id.search_result_listview);
    }

    private void updateSearchResultShow(){
        String searchKey = mEditText.getText().toString();
        if(TextUtils.isEmpty(searchKey)){
            mSearchResultLine.setVisibility(View.GONE);
        }else{
            mSearchResultLine.setVisibility(View.VISIBLE);
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
