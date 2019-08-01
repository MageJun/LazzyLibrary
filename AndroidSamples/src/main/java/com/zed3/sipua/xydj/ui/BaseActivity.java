package com.zed3.sipua.xydj.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lw.demo.android.samples.R;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void onActivityCreate( Bundle savedInstanceState);

    private View title;
    private View mTitleLeft;
    private View mTitleRight;

    public boolean isShowTitle(){
        return true;
    }

    public boolean isShowTitleCenter(){
        return false;
    }
    public boolean isShowTitleLeftBack(){
        return false;
    }
    public boolean isShowTitleLeftMenu(){
        return false;
    }
    public boolean isShowTitleLeftCancel(){
        return false;
    }
    public boolean isShowTitleRightOk(){
        return false;
    }

    public boolean isShowTitileRight(){
        return false;
    }

    public boolean isShowLeftBtn(){return false;}

    public boolean isShowRightBtn(){return false;}

    public void setTitleCenterText(int rid){
        if(!isShowTitle()){
            return ;
        }
        TextView tv = title.findViewById(R.id.title_text);
        tv.setText(rid);
    }

    public void setTitleCenterText(String text){
        if(!isShowTitle()){
            return ;
        }
        TextView tv = title.findViewById(R.id.title_text);
        tv.setText(text);
    }

    public void setLeftBtnText(String text){
        if(!isShowTitle()){
            return ;
        }
        Button tv = title.findViewById(R.id.title_btn_left);
        tv.setText(text);
    }

    public void setLeftBtnText(int rid){
        if(!isShowTitle()){
            return ;
        }
        Button tv = title.findViewById(R.id.title_btn_left);
        tv.setText(rid);
    }


    public void setLeftCancelText(String text){
        if(!isShowTitle()){
            return ;
        }
        TextView tv = title.findViewById(R.id.title_left_cancel);
        tv.setText(text);
    }

    public void setLeftCancelText(int rid){
        if(!isShowTitle()){
            return ;
        }
        TextView tv = title.findViewById(R.id.title_left_cancel);
        tv.setText(rid);
    }
    public void setRightBtnText(String text){
        if(!isShowTitle()){
            return ;
        }
        Button tv = title.findViewById(R.id.title_btn_right);
        tv.setText(text);
    }
    public void setRightBtnText(int rid){
        if(!isShowTitle()){
            return ;
        }
        Button tv = title.findViewById(R.id.title_btn_right);
        tv.setText(rid);
    }

    public void setRightOkText(String text){
        if(!isShowTitle()){
            return ;
        }
        TextView tv = title.findViewById(R.id.title_right_ok);
        tv.setText(text);
    }
    public void setRightOkText(int rid){
        if(!isShowTitle()){
            return ;
        }
        TextView tv = title.findViewById(R.id.title_right_ok);
        tv.setText(rid);
    }



    @Override
    public void setContentView(int layoutResID) {
       int  childLayoutResId = layoutResID;

        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.content_parent);

        getLayoutInflater().inflate(childLayoutResId, linearLayout);

    }

    private void setActivityContentView(int layoutResID){
        super.setContentView(layoutResID);
    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityContentView(R.layout.xydj_base_activity);

        initTitleView();

        onActivityCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            }else{
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
//
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    protected  void initTitleView(){
        title = findViewById(R.id.title_layout);
        if(isShowTitle()){
            View left_back =  title.findViewById(R.id.title_left_back);
            View left_menu = title.findViewById(R.id.title_left_menu);
            View center = title.findViewById(R.id.title_text);
            View left_btn =title.findViewById(R.id.title_btn_left);
            View right_btn= title.findViewById(R.id.title_btn_right);
            View left_cancel = title.findViewById(R.id.title_left_cancel);
            View right_ok = title.findViewById(R.id.title_right_ok);
            left_back.setVisibility(View.GONE);
            left_menu.setVisibility(View.GONE);
            left_btn.setVisibility(View.GONE);
            right_btn.setVisibility(View.GONE);
            left_cancel.setVisibility(View.GONE);
            right_ok.setVisibility(View.GONE);
            center.setVisibility(View.INVISIBLE);

            if(isShowTitleLeftBack()){
                left_back.setVisibility(View.VISIBLE);
            }else if(isShowTitleLeftMenu()){
                left_menu.setVisibility(View.VISIBLE);
            }else if(isShowLeftBtn()){
                left_btn.setVisibility(View.VISIBLE);
            }else if(isShowTitleLeftCancel()){
                left_cancel.setVisibility(View.VISIBLE);
            }

            if(isShowRightBtn()){
                right_btn.setVisibility(View.VISIBLE);
            }else if(isShowTitleRightOk()){
                right_ok.setVisibility(View.VISIBLE);
            }

            if(isShowTitleCenter()){
                center.setVisibility(View.VISIBLE);
            }else{
                center.setVisibility(View.INVISIBLE);
            }


        }else{
            title.setVisibility(View.GONE);
        }

    }

    protected void onTitleLeftBackOnClick(){
        finish();
    }
    protected void onTitleLeftMenuOnClick(){
        finish();
    }
    protected  void onTitleLeftBtnClick(){}
    protected  void onTitleRightBtnClick(){}
    protected  void onTitleLeftCancelClick(){}
    protected  void onTitleRightOkClick(){}
    public void onTitleClick(View view){
        switch (view.getId()){
            case R.id.title_left_back:
                onTitleLeftBackOnClick();
                break;
            case R.id.title_left_menu:
                onTitleLeftMenuOnClick();
                break;
            case R.id.title_btn_left:
                onTitleLeftBtnClick();
                break;
            case R.id.title_btn_right:
                onTitleRightBtnClick();
                break;
            case R.id.title_left_cancel:
                onTitleLeftCancelClick();
                break;
            case R.id.title_right_ok:
                onTitleRightOkClick();
                break;
        }
    }


}
