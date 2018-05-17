package com.zed3.sipua.xydj.ui.groupinviteinfo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.groupinviteinfo.fragment.TabGroupInviteInfoReceiveFragment;
import com.zed3.sipua.xydj.ui.groupinviteinfo.fragment.TabGroupInviteSendFragment;

public class GroupInviteInfoActivity extends BaseActivity {

    private TabGroupInviteInfoReceiveFragment mReceiveFragment;
    private TabGroupInviteSendFragment mSendFragment;

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
            setContentView(R.layout.xydj_activity_group_invite_info);

            initView();

            receiveTV.performClick();
    }
    private TextView receiveTV,sendTv;

    private void initView() {
        receiveTV = findViewById(R.id.receve_id);
        sendTv = findViewById(R.id.send_id);
    }

    private void setSelection(int pos){
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction =manager.beginTransaction();
        hideFragments(transaction);


        switch (pos){
            case 0:
                if(mReceiveFragment==null){
                    mReceiveFragment= new TabGroupInviteInfoReceiveFragment();
                    transaction.add(R.id.group_invite_content,mReceiveFragment,R.id.receve_id+ TabGroupInviteInfoReceiveFragment.TAG);
                }
                transaction.show(mReceiveFragment);
                break;
            case 1:
                if(mSendFragment==null){
                    mSendFragment= new TabGroupInviteSendFragment();
                    transaction.add(R.id.group_invite_content,mSendFragment,R.id.send_id+""+TabGroupInviteSendFragment.TAG);
                }
                transaction.show(mSendFragment);

                break;
        }

        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction){
        if(mReceiveFragment!=null){
            transaction.hide(mReceiveFragment);
        }
        if(mSendFragment!=null){
            transaction.hide(mSendFragment);
        }
    }



    public void onClick(View view){
        sendTv.setSelected(false);
        receiveTV.setSelected(false);
        switch (view.getId()){
            case R.id.receve_id:
                setSelection(0);
                receiveTV.setSelected(true);
                break;
            case R.id.send_id:
                setSelection(1);
                sendTv.setSelected(true);
                break;
        }
    }

}
