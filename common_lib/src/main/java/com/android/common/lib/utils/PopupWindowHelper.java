package com.android.common.lib.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android.common.lib.R;

public class PopupWindowHelper {


    private PopupWindow mPopupWindow;
    private AnimUtil animUtil;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private OnPopupWindowClickListener mPopupWindowListener;

    public interface  OnPopupWindowClickListener{
        void onDelClickListener(View view);
        void onCancelClickListener(View view);
    }

    private PopupWindowHelper(){}

    public static PopupWindowHelper newInstance(){
        return new PopupWindowHelper();
    }

    public void showPopupWindow(final Activity context, View view, OnPopupWindowClickListener listener){
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
            //设置SelectPicPopupWindow弹出窗体的背景
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP)
                mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.xydj_popupwindo_bg,null));
            else{
                mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.xydj_popupwindo_bg));
            }
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setAnimationStyle(R.style.PopupWindowStyle);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    toggleBright(context,true);
                }
            });
        }
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toggleBright(context,false);
    }

    private void toggleBright(final Activity context,final boolean isBright) {
        AnimUtil animUtil= new AnimUtil();
        //三个参数分别为： 起始值 结束值 时长  那么整个动画回调过来的值就是从0.5f--1f的
        if(isBright)
            animUtil.setValueAnimator(0.5f, 1f, 350);
        else{
            animUtil.setValueAnimator(1f,0.5f,350);
        }
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                backgroundAlpha(context,progress);//在此处改变背景，这样就不用通过Handler去刷新了。
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

}
