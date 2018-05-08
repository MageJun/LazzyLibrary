package com.zed3.sipua.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.ui.group.helper.GroupManager;

public class TestDemoMainActivity extends BaseActivity {

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_test_demo_main);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.create_new_group:
                GroupManager.createNewGroup(this);
                break;
        }
    }
}
