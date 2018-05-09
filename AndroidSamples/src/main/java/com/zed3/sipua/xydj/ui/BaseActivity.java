package com.zed3.sipua.xydj.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lw.demo.adnroid.samples.R;

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

    public boolean isShowTitileRight(){
        return false;
    }

    public void setTitleCenterText(int rid){
        TextView tv = title.findViewById(R.id.title_text);
        tv.setText(rid);
    }

    public void setTitleCenterText(String text){
        TextView tv = title.findViewById(R.id.title_text);
        tv.setText(text);
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
        if(isShowTitle()){
            title = findViewById(R.id.title_layout);
            View left_back =  title.findViewById(R.id.title_left_back);
            View left_menu = title.findViewById(R.id.title_left_menu);
            View center = title.findViewById(R.id.title_center);
            left_back.setVisibility(View.GONE);
            left_menu.setVisibility(View.GONE);
            center.setVisibility(View.INVISIBLE);

            if(isShowTitleLeftBack()){
                left_back.setVisibility(View.VISIBLE);
            }else if(isShowTitleLeftMenu()){
                left_menu.setVisibility(View.VISIBLE);
            }

            if(isShowTitleCenter()){
                center.setVisibility(View.VISIBLE);
            }else{
                center.setVisibility(View.INVISIBLE);
            }


        }

    }

    protected void onTitleLeftBackOnClick(){
        finish();
    }
    protected void onTitleLeftMenuOnClick(){
        finish();
    }

    public void onTitleClick(View view){
        switch (view.getId()){
            case R.id.title_left_back:
                onTitleLeftBackOnClick();
                break;
            case R.id.title_left_menu:
                onTitleLeftMenuOnClick();
                break;
        }
    }
}
