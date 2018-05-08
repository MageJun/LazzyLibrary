package com.zed3.sipua.ui.group.helper;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lw.demo.adnroid.samples.R;

public class GroupManager {

    public static void createNewGroup(Context context){
        createAndShowAlertDialog(context);
    }

    private static void createAndShowAlertDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.xydj_custom_dialog_create_group,null);
//        TextView title = contentView.findViewById(R.id.tv_title);
//        TextView msg = contentView.findViewById(R.id.tv_message);
//        title.setText(R.string.xydj_group_pwd);
        Button btn_cancel= contentView.findViewById(R.id.dialog_cancel);
        Button btn_ok = contentView.findViewById(R.id.dialog_ok);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        //替换布局
        window.setContentView(contentView);
        //修改window窗口宽高
        window.setLayout((int) context.getResources().getDimension(R.dimen.xydj_custom_dialog_width),
                (int) context.getResources().getDimension(R.dimen.xydj_custom_dialog_height));



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
