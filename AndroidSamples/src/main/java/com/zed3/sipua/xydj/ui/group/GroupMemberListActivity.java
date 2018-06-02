package com.zed3.sipua.xydj.ui.group;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.common.widget.recyclerview.decoration.GridDividerDecoration;
import com.zed3.sipua.xydj.ui.group.adapter.GroupMemeberListAdapter;
import com.zed3.sipua.xydj.ui.group.adapter.OnItemClickListener;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;

import java.util.List;

public class GroupMemberListActivity extends BaseActivity {

    private static final String TAG = GroupMemberListActivity.class.getSimpleName();
    private EditText mEditText;
    private PttCustomGrp mGrp;
    private RecyclerView mRecyclerView;
    private GroupMemeberListAdapter memeberListAdapter;

    @Override
    public boolean isShowTitleCenter() {
        return true;
    }

    @Override
    public boolean isShowTitleLeftBack() {
        return true;
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_group_member_list);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Object data = getIntent().getExtras().get("grp");
            if(data!=null &&data instanceof PttCustomGrp){
                mGrp = (PttCustomGrp)data;
            }
        }

        initView();
        
        initData();
    }

    private void initData() {
        int count =calcElementCount();
        GridLayoutManager layoutManager = new GridLayoutManager(this,count);
        int itemMarginWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_item_margin);
        GridDividerDecoration decoration = new GridDividerDecoration(this, LinearLayoutManager.HORIZONTAL,false);
        decoration.setOffset(itemMarginWidth/2);
        decoration.setSpanCount(count);
        memeberListAdapter = new GroupMemeberListAdapter(this);
        memeberListAdapter.setOnItemClickListener(mItemClickListener);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(memeberListAdapter);
        mRecyclerView.addItemDecoration(decoration);
        updateAndShowGrpInfo();
    }

    private void updateAndShowGrpInfo() {
        if(mGrp!=null){
            List<CustomGroupMemberInfo> memberInfos = mGrp.getMember_list();
            int memberCount = 0;
            if(memberInfos!=null){
                memberCount = memberInfos.size();
                memeberListAdapter.setData(memberInfos);
            }
            setTitleCenterText("群成员（"+memberCount+"）");
        }
    }

    private void initView() {
        mEditText = findViewById(R.id.group_memeber_list_edit);
        calcLeftDrawableSize();

        mRecyclerView = findViewById(R.id.member_list_view);
        
    }

    private OnItemClickListener<CustomGroupMemberInfo> mItemClickListener = new OnItemClickListener<CustomGroupMemberInfo>() {
        @Override
        public void onItemClick(View v, int pos) {

        }

        @Override
        public void onDataItemClick(View v, int pos, CustomGroupMemberInfo customGroupMemberInfo) {
            Intent intent = new Intent(GroupMemberListActivity.this,GroupMemeberDetailActivity.class);
            intent.putExtra("memberInfo",customGroupMemberInfo);
            startSpecifyActivity(intent);
        }

        @Override
        public void onAddItemClick() {
            Intent intent = new Intent(GroupMemberListActivity.this,InviteMemberActivity.class);
            intent.putExtra("grp",mGrp);
            startSpecifyActivity(intent);
        }

        @Override
        public void onSubTraction() {

            Intent intent = new Intent(GroupMemberListActivity.this,GroupMemeberDeleteActivity.class);
            intent.putExtra("grp",mGrp);
            startActivityForResult(intent,10086);
        }
    };

    private void startSpecifyActivity(Intent intent) {
        startActivity(intent);
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
        int count = itemCount;
        Log.i(TAG,"width = "+width+",itemWidth = "+itemWidth+", itemMarginWidth="+itemMarginWidth+" ,initData count = "+count);
        return count;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10086){
            if(data!=null){
                PttCustomGrp customGrp = (PttCustomGrp) data.getExtras().get("grp");
                if(customGrp!=null){
                    mGrp = customGrp;
                    updateAndShowGrpInfo();
                }

            }
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.group_memeber_list_edit:
                Intent searchIntent = new Intent(this, GroupMemberSearchActivity.class);
                searchIntent.putExtra("grp",mGrp);
                startSpecifyActivity(searchIntent);
                break;
        }
    }

}
