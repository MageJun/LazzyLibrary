package com.zed3.sipua.xydj.ui.group;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.common.widget.recyclerview.decoration.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.group.adapter.FriendShareGroupListAdapter;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;

import java.util.ArrayList;
import java.util.List;

/**
 * 成员详细信息界面
 */

public class GroupMemeberDetailActivity extends BaseActivity {
    private TextView mRemarkTV,mMemberAccount,mMemberNickName,mShareGroupCount;
    private CustomGroupMemberInfo mMemeberInfo;
    private View mFunctionLine,mFriendFunctionLine,mMemberFunctionLine,mPlaceHolder;
    private boolean isSelf = false;
    private boolean isFriend = true;
    private FriendShareGroupListAdapter mAdapter;
    private RecyclerView mListView;
    private boolean isShowShareGroup = false;
    private ImageView mArrowIcon;
    private List<PttCustomGrp> mShareGrps = new ArrayList<PttCustomGrp>();
    private final static int MSG_UPDATE_SHARE_GROUP_LIST = 0;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_UPDATE_SHARE_GROUP_LIST:
                    int count = mShareGrps.size();
                    String number_str = getResources().getString(R.string.xydj_member_share_group_number,count+"");
                    mMemberAccount.setText(number_str);
                    mAdapter.setData(mShareGrps);
                    break;
            }
        }
    };

    @Override
    public boolean isShowTitleLeftBack() {
        return true;
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_group_member_detail);
        Bundle data = getIntent().getExtras();
        if(data!=null){
            Object info = data.get("memberInfo");
            if(info!=null && info instanceof CustomGroupMemberInfo){
                mMemeberInfo = (CustomGroupMemberInfo) info;
                String number = mMemeberInfo.getMemberNum();
            }
        }else{
            return ;
        }
        initView();
        initData();
    }
    private void initView() {
        mRemarkTV = findViewById(R.id.remarks_tv);
        mMemberAccount = findViewById(R.id.account);
        mMemberNickName = findViewById(R.id.nickname);
        mFunctionLine = findViewById(R.id.function_line);
        mListView = findViewById(R.id.group_list);
        mShareGroupCount = findViewById(R.id.share_group_count);
        mFriendFunctionLine = findViewById(R.id.frind_function_line);
        mMemberFunctionLine = findViewById(R.id.member_function_line);
        mArrowIcon = findViewById(R.id.arraw_icon);
        mPlaceHolder = findViewById(R.id.place_holder);
        calcRightDrawableSize();
    }
    private void initData() {
        mAdapter = new FriendShareGroupListAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ItemDividerDecoration decoration = new ItemDividerDecoration(this,LinearLayoutManager.VERTICAL);
        decoration.setOffset(2);
        mListView.addItemDecoration(decoration);
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(layoutManager);
        mShareGrps = createTmpGrps(0);
        mAdapter.setData(mShareGrps);
        if (isSelf) {
            mRemarkTV.setVisibility(View.GONE);
            mFunctionLine.setVisibility(View.GONE);

        } else {
            mRemarkTV.setVisibility(View.VISIBLE);
            mFunctionLine.setVisibility(View.VISIBLE);
            if(isFriend){
                mFriendFunctionLine.setVisibility(View.VISIBLE);
                mMemberFunctionLine.setVisibility(View.GONE);

                updateShareGroup();
            }else{
                mFriendFunctionLine.setVisibility(View.GONE);
                mMemberFunctionLine.setVisibility(View.VISIBLE);
            }
        }
        updateMemeberInfo();
    }
    private  List<PttCustomGrp> createTmpGrps(int memberCount) {
        List<PttCustomGrp> grps = new ArrayList<PttCustomGrp>();
        for (int i=0;i<memberCount;i++){
            PttCustomGrp tmp = new PttCustomGrp();
            tmp.setGroupName("共享群组"+i+"号");
            grps.add(tmp);
        }
        return grps;
    }

    private void updateMemeberInfo() {
        if(mMemeberInfo==null){
            return ;
        }
        mMemberAccount.setText(mMemeberInfo.getMemberNum());
        mMemberNickName.setText(mMemeberInfo.getMemberName());
    }

    private void updateShareGroup() {
       if(isShowShareGroup){
           mArrowIcon.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
          if(mShareGrps.size()>0){
              mListView.setVisibility(View.VISIBLE);
              mPlaceHolder.setVisibility(View.GONE);
          }
       }else{
           mArrowIcon.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
           mListView.setVisibility(View.GONE);
           mPlaceHolder.setVisibility(View.VISIBLE);
       }
    }


    private void calcRightDrawableSize() {
        Drawable rightDrawable = mRemarkTV.getCompoundDrawables()[2];
        if(rightDrawable!=null){
            int ldW = getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            int ldH = getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            rightDrawable.setBounds(0,0,ldW,ldH);
            mRemarkTV.setCompoundDrawables(mRemarkTV.getCompoundDrawables()[0],mRemarkTV.getCompoundDrawables()[1],rightDrawable,mRemarkTV.getCompoundDrawables()[3]);
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            //修改备注
            case R.id.remarks_tv:
                Intent modifyGN = new Intent(this, GroupInfoModifyActivity.class);
                modifyGN.putExtra("member", mMemeberInfo);
                modifyGN.putExtra("type", 2);
                startSpecifyActivity(modifyGN);
                break;
            case R.id.share_group_title:
                isShowShareGroup=!isShowShareGroup;
                updateShareGroup();
                break;
        }
    }


    private void startSpecifyActivity(Intent intent) {
        startActivity(intent);
    }
}
