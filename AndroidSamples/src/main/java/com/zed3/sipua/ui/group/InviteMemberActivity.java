package com.zed3.sipua.ui.group;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.ui.BaseActivity;
import com.zed3.sipua.ui.group.helper.GroupViewer;
import com.zed3.sipua.ui.group.helper.InvitePresenter;

public class InviteMemberActivity extends BaseActivity implements GroupViewer {

    private InvitePresenter mPresenter;

    private static final int MSG_REQUEST_PWD_SUCCESS = 1;
    private static final int MSG_REQUEST_PWD_FAILED = 2;
    private static final int MSG_QUIT = 3;
    private static final int MSG_SHARE_PWD = 4;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_REQUEST_PWD_SUCCESS:
                            String pwd = (String) msg.obj;
                            createAndShowAlertDialog(pwd);
                        break;
                    case MSG_REQUEST_PWD_FAILED:

                        break;
                    case MSG_QUIT:
                        finish();
                        break;
                    case MSG_SHARE_PWD:
                        Toast.makeText(InviteMemberActivity.this,"口令分享成功",Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessage(MSG_QUIT);
                        break;
                }

        }
    };

    private void createAndShowAlertDialog(String pwd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("本群口令");
        builder.setMessage(pwd);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHandler.sendEmptyMessage(MSG_QUIT);
            }
        });
        builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHandler.sendEmptyMessage(MSG_SHARE_PWD);
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_invite_member);

        mPresenter = new InvitePresenter();
        mPresenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach(this);
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
        mPresenter.requestGroupPWD();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    public void requestGroupSharedPWDSuccess(String pwd){
            Message msg = Message.obtain();
            msg.what = MSG_REQUEST_PWD_SUCCESS;
            msg.obj = pwd;
            mHandler.sendMessage(msg);
    }
}
