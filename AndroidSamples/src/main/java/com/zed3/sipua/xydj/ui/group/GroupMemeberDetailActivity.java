package com.zed3.sipua.xydj.ui.group;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;

/**
 * 成员详细信息界面
 */

public class GroupMemeberDetailActivity extends BaseActivity {
    private TextView mRemarkTV,mMemberAccount,mMemberNickName;
    private CustomGroupMemberInfo mMemeberInfo;

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
                }
            }else{
                return ;
            }
            initView();
            initData();
    }

    private void initData() {
        if(mMemeberInfo!=null){
            mMemberAccount.setText(mMemeberInfo.getMemberNum());
            mMemberNickName.setText(mMemeberInfo.getMemberName());
        }
    }

    private void initView() {
        mRemarkTV = findViewById(R.id.remarks_tv);
        mMemberAccount = findViewById(R.id.account);
        mMemberNickName = findViewById(R.id.nickname);
        calcRightDrawableSize();
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

}
