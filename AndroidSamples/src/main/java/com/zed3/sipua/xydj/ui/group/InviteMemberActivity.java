package com.zed3.sipua.xydj.ui.group;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.BaseActivity;
import com.zed3.sipua.xydj.ui.group.helper.GroupViewer;
import com.zed3.sipua.xydj.ui.group.helper.InvitePresenter;

public class InviteMemberActivity extends BaseActivity implements GroupViewer {

    private InvitePresenter mPresenter;

    private static final int MSG_REQUEST_PWD_SUCCESS = 1;
    private static final int MSG_REQUEST_PWD_FAILED = 2;
    private static final int MSG_QUIT = 3;
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
                }

        }
    };

    private void createAndShowAlertDialog(final String pwd) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.xydj_custom_dialog_share_pwd,null);
        TextView title = contentView.findViewById(R.id.tv_title);
        TextView msg = contentView.findViewById(R.id.tv_message);
        Button btn_cancel= contentView.findViewById(R.id.dialog_cancel);
        Button btn_ok = contentView.findViewById(R.id.dialog_ok);
        title.setText(R.string.xydj_group_pwd);
        msg.setText(pwd);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        //替换布局
        window.setContentView(contentView);
        //修改window窗口宽高
        window.setLayout((int) getResources().getDimension(R.dimen.xydj_custom_dialog_width),
                (int) getResources().getDimension(R.dimen.xydj_custom_dialog_height));



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mHandler.sendEmptyMessage(MSG_QUIT);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ClipboardManager clipboardManager = (ClipboardManager) InviteMemberActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("text",pwd);
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(InviteMemberActivity.this,"口令分享成功",Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(MSG_QUIT);
            }
        });
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
