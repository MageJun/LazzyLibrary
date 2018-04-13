package com.lw.android.demo.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.lw.android.demo.R;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initDate();


    }

    private void initDate() {

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
