package com.zed3.sipua.xydj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.ui.groupinviteinfo.GroupInviteInfoActivity;

public class UserInfoActivity extends BaseActivity {

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_userinfo);
    }


    public void onClick(View v){
        switch (v.getId()){

            case R.id.icon_name:

                break;
            case R.id.group_msg:
                Intent intent = new Intent(this,GroupInviteInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.friends_invite_infos:

                break;
            case R.id.ptt_setting:

                break;
            case R.id.update_vip:

                break;
            case R.id.settings:

                break;

        }
    }


}
