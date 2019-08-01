package com.lw.demo.android.samples;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SlidingUpPannelActivity extends AppCompatActivity {
    private SlidingUpPannelLayoutCustom mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_up_pannel);
        mLayout = findViewById(R.id.sliding_layout);
        mLayout.setOverlayed(true);
        mLayout.setMainCanTouch(false);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.request_loc_icon:
                Toast.makeText(this,"请求定位",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
