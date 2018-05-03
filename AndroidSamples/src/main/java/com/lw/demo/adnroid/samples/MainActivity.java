package com.lw.demo.adnroid.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draglayout);
    }

    public void onClick(View v){


        switch (v.getId()){
            case R.id.del:
                Toast.makeText(this,"删除", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
