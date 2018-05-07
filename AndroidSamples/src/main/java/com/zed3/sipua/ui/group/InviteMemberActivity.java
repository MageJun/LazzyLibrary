package com.zed3.sipua.ui.group;

import android.os.Bundle;
import android.view.View;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.ui.BaseActivity;

public class InviteMemberActivity extends BaseActivity {
    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_invite_member);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.invite_share_pwd:
                requestGroupPwd();
                break;
        }
    }

    /**
     * 向服务器请求群口令
     */
    private void requestGroupPwd() {

    }

}
