package com.zed3.sipua.xydj.ui.group.helper;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.common.utils.AnimUtil;

public class GroupManager {

    private static GroupManager sIntance;

    private GroupManager(){}

    public static GroupManager getIntance(){
        if(sIntance==null){
            sIntance = new GroupManager();
        }
        return sIntance;
    }

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


    private PopupWindow mPopupWindow;
    private AnimUtil animUtil;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private OnPopupWindowClickListener mPopupWindowListener;

    public interface  OnPopupWindowClickListener{
        void onDelClickListener(View view);
        void onCancelClickListener(View view);
    }
    public void showPopupWindow(final Activity context,View view,OnPopupWindowClickListener listener){
        mPopupWindowListener = listener;
        if(mPopupWindow==null){
            View pop = LayoutInflater.from(context).inflate(R.layout.xydj_popupwindow_layout,null);
            View del = pop.findViewById(R.id.popup_del);
            View cancel = pop.findViewById(R.id.popup_cancel);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPopupWindow!=null){
                        mPopupWindow.dismiss();
                    }
                    if(mPopupWindowListener!=null){
                        mPopupWindowListener.onDelClickListener(v);
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPopupWindow!=null){
                        mPopupWindow.dismiss();
                    }
                    if(mPopupWindowListener!=null){
                        mPopupWindowListener.onCancelClickListener(v);
                    }
                }
            });
            mPopupWindow = new PopupWindow(pop, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP)
                mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.xydj_popupwindo_bg,null));
            else{
                mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.xydj_popupwindo_bg));
            }
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setAnimationStyle(R.style.XYDJPopupWindow);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    toggleBright(context);
                }
            });
        }
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toggleBright(context);
    }

    private void toggleBright(final Activity context) {
        if (animUtil == null) {
            animUtil = new AnimUtil();
        }
        //三个参数分别为： 起始值 结束值 时长  那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(0.5f, 1f, 350);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                //此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (1.5f - progress);//三目运算，应该挺好懂的。
                backgroundAlpha(context,bgAlpha);//在此处改变背景，这样就不用通过Handler去刷新了。
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                //在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }

    /***
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp =context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public boolean checkIsUserFriend(CustomGroupMemberInfo info){
        return false;
    }

}
