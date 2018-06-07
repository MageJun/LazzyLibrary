package com.lazzy.common.lib.utils;


import android.graphics.Rect;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View视图帮助类
 */
public class ViewHelper {


    public static void setViewTouchRect(final View view, final int width, final int height){
        View parent = (View) view.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                Rect delegateArea = new Rect();
                view.getHitRect(delegateArea);
                delegateArea.left-=width;
                delegateArea.bottom+=height;
                delegateArea.top-=height;
                delegateArea.right+=width;

                TouchDelegate delegate = new TouchDelegate(delegateArea,view);
                if(View.class.isInstance(view.getParent())){
                    ((View)view.getParent()).setTouchDelegate(delegate);
                }

            }
        });
    }


    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText EditText输入框
     */
    public static void setEditTextInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入换行符
     *
     */
    public static InputFilter getEditTextFilterEnter() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
       return filter;
    }
}
