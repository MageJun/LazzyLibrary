package com.zed3.sipua.xydj.ui.group.helper;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lw.demo.adnroid.samples.R;

public class GroupManager {

    public static void createNewGroup(Context context, Handler.Callback callback){
        createAndShowAlertDialog(context,callback);
    }

    private static void createAndShowAlertDialog(final Context context,final Handler.Callback callback) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.xydj_custom_dialog_create_group,null);
        Button btn_cancel= contentView.findViewById(R.id.dialog_cancel);
        Button btn_ok = contentView.findViewById(R.id.dialog_ok);

        final EditText editText = contentView.findViewById(R.id.edt_group_name);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        //替换布局
        window.setContentView(contentView);
        //添加这个Flag，无法弹出输入法
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
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
                String groupName  = editText.getText().toString();
                if (TextUtils.isEmpty(groupName)) {
                    Toast.makeText(context, "组名不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }
                dialog.dismiss();
                Toast.makeText(context, "groupName = "+groupName, Toast.LENGTH_SHORT).show();
                //TODO 走创建自建组的流程

            }
        });
    }
}
