package com.lazzy.common.lib.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lazzy.common.lib.R;

public class MessageHelper {
    private static MessageHelper sInstance;

    private MessageHelper(){}

    public static MessageHelper getInstance(){
        if(sInstance==null){
            sInstance = new MessageHelper();
        }
        return sInstance;
    }

    public void createDialog(Context context, String title, String msg,String btnOk,String btnCancel, View.OnClickListener okListener, View.OnClickListener cancelListener){
        showDialog(context,title,msg,btnOk,btnCancel,okListener,cancelListener);
    }
    private void showDialog(Context context, String titleStr, String message,String btnOk,String btnCancel, final View.OnClickListener okListener, final View.OnClickListener cancelListener){
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            View contentView = inflater.inflate(R.layout.xydj_custom_dialog_share_pwd,null);
            TextView title = contentView.findViewById(R.id.tv_title);
            TextView msg = contentView.findViewById(R.id.tv_message);
            Button btn_cancel= contentView.findViewById(R.id.dialog_cancel);
            Button btn_ok = contentView.findViewById(R.id.dialog_ok);
            title.setText(titleStr);
            msg.setText(message);
            if(!TextUtils.isEmpty(btnCancel))
            btn_cancel.setText(btnCancel);
            if(!TextUtils.isEmpty(btnOk))
            btn_ok.setText(btnOk);


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final AlertDialog dialog = builder.create();
            dialog.show();
            Window window = dialog.getWindow();
            //替换布局
            window.setContentView(contentView);
            //修改window窗口宽高
            window.setLayout((int)context.getResources().getDimension(R.dimen.xydj_custom_dialog_width),
                    (int) context.getResources().getDimension(R.dimen.xydj_custom_dialog_height));


            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (cancelListener != null) {
                        cancelListener.onClick(v);
                    }
                }

            });


            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if(okListener!=null) {
                        okListener.onClick(v);
                    }
                }
            });

        }
    }
}
