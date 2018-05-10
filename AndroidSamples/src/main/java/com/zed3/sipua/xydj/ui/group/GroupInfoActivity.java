package com.zed3.sipua.xydj.ui.group;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.group.adapter.GroupMemeberListAdapter;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;
import com.zed3.sipua.xydj.ui.group.adapter.OnItemClickListener;
import com.zed3.sipua.xydj.ui.helper.MessageHelper;

import java.util.List;

/**
 * 群组信息界面
 */
public class GroupInfoActivity extends BaseActivity {
    private static final String TAG = GroupInfoActivity.class.getSimpleName();

    @Override
    public boolean isShowTitleCenter() {
        return true;
    }

    @Override
    public boolean isShowTitleLeftBack() {
        return true;
    }

    private RecyclerView mRecyclerView;
    private TextView mMemberNumberTv;
    private PttCustomGrp mGrp;
    private GroupMemeberListAdapter memeberListAdapter;
    private TextView mGroupNameTV,mExpiryDateTV,mMaxMemberTV,mNickNameTV;

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
            setContentView(R.layout.xydj_activity_group_info);
            setTitleCenterText("群组信息");
            mGrp = (PttCustomGrp) getIntent().getExtras().get("grp");
            initView();

            initData();
    }

    private void initData() {
        memeberListAdapter = new GroupMemeberListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        int itemMarginWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_item_margin);
        ItemDividerDecoration decoration = new ItemDividerDecoration(this,LinearLayoutManager.HORIZONTAL);
        decoration.setOffset(itemMarginWidth);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(memeberListAdapter);
        memeberListAdapter.setOnItemClickListener(mItemClickListener);
        if(mGrp!=null){
            int count = calcElementCount();
            List<CustomGroupMemberInfo> members =mGrp.getMember_list();
            mMemberNumberTv.setText(members.size()+"人");
            mGroupNameTV.setText(mGrp.getGroupName());
            String expDate = mGrp.getExpiryDate();
            String expDateFinal = getFinalExpDate(expDate);
            mExpiryDateTV.setText(expDateFinal);
            mMaxMemberTV.setText(mGrp.getMaxMember()+"人");

            if(count<members.size()){
                memeberListAdapter.setData(members.subList(0,count));
            }else{
                memeberListAdapter.setData(members);
            }
        }

    }

    private String getFinalExpDate(String expDate) {
        if ("-1".equals(expDate)) {
            return "长期有效";
        }
        return null;
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.member_list_view);
        mRecyclerView.setNestedScrollingEnabled(false);//不需要滑动
        mMemberNumberTv = findViewById(R.id.member_number_tv);
        mGroupNameTV = findViewById(R.id.group_name);
        mExpiryDateTV = findViewById(R.id.expiry_date);
        mMaxMemberTV = findViewById(R.id.group_maxmember);
        mNickNameTV = findViewById(R.id.nick_name);
    }

    private OnItemClickListener<CustomGroupMemberInfo> mItemClickListener = new OnItemClickListener<CustomGroupMemberInfo>() {
        @Override
        public void onItemClick(View v, int pos) {

        }

        @Override
        public void onDataItemClick(View v, int pos, CustomGroupMemberInfo customGroupMemberInfo) {
            Intent intent = new Intent(GroupInfoActivity.this,GroupMemeberDetailActivity.class);
            intent.putExtra("memberInfo",customGroupMemberInfo);
            startSpecifyActivity(intent);
        }

        @Override
        public void onAddItemClick() {
            Intent intent = new Intent(GroupInfoActivity.this,InviteMemberActivity.class);
            intent.putExtra("grp",mGrp);
            startSpecifyActivity(intent);
        }

        @Override
        public void onSubTraction() {

        }
    };


    private int calcElementCount(){
        int itemWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_item_icon_wh);
        int itemMarginWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_item_margin);
        int paddingWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_info_padding_rl);
        int width = getResources().getDisplayMetrics().widthPixels-paddingWidth*2;

        /**
         * 计算Item的个数，offsetCount=itemCount和-1;
         * 屏幕宽度width
         * item和offset的总量不能大于width
         * offsetCount*offset+itemCount*item<=width
         * offset*itemCount-offset+itemCount*item
         * itemCount = (width+offsett)/(item+offset)
         */
        int itemCount = (width+itemMarginWidth)/(itemWidth+itemMarginWidth);
        int count = itemCount-2;//根据屏幕宽度，计算可以显示的Item的个数，预留加号和减号的位置
        Log.i(TAG,"width = "+width+",itemWidth = "+itemWidth+", itemMarginWidth="+itemMarginWidth+" ,initData count = "+count);
        return count;
    }

    public void onClick(View view){
        switch(view.getId()){
            //群组名称
            case R.id.group_name_line:
                Intent modifyGN = new Intent(this,GroupInfoModifyActivity.class);
                modifyGN.putExtra("grp",mGrp);
                modifyGN.putExtra("type",0);
                startSpecifyActivity(modifyGN);
                break;
            //群口令分享
            case R.id.group_pws_share_line:

                break;
            //群有效时间
            case R.id.expiry_date_line:

                break;
            //群成员上限
            case R.id.group_maxmember_line:

                break;
            //我在本群的昵称
            case R.id.nick_name_line:
                Intent modifyNN = new Intent(this,GroupInfoModifyActivity.class);
                modifyNN.putExtra("grp",mGrp);
                modifyNN.putExtra("type",1);
                startSpecifyActivity(modifyNN);
                break;
            //群成员
            case R.id.member_list_arraw:
                Intent memberListIntent = new Intent(this,GroupMemberListActivity.class);
                memberListIntent.putExtra("grp",mGrp);
                startSpecifyActivity(memberListIntent);
                break;
                //退出群组
            case R.id.exit_group:
                MessageHelper.getInstance().createDialog(this
                        , getResources().getString(R.string.xydj_exit_group_notify_title)
                        , getResources().getString(R.string.xydj_exit_group_notify_creator)
                        ,getResources().getString(R.string.xydj_ok)
                        ,null
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO 退组操作
                            }
                        }
                        ,null);
                break;
        }
    }

    private void startSpecifyActivity(Intent intent) {
        startActivity(intent);
    }
}
