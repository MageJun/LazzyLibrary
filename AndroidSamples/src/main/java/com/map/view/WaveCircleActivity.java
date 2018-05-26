package com.map.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lw.demo.android.samples.R;

public class WaveCircleActivity extends AppCompatActivity {

    private WaveView mWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wave_circle);
        setContentView(R.layout.custom_map_marker_with_wavecircle);
        mWaveView = findViewById(R.id.wave_view);
        mWaveView.setColor(Color.parseColor("#BFE2CE"));
        mWaveView.setSpeed(1*1000);
        mWaveView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWaveView.stop();
    }
}
