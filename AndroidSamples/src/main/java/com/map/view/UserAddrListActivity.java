package com.map.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lw.demo.android.samples.R;
import com.map.adapter.UserAddrListAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAddrListActivity extends AppCompatActivity {

    private RecyclerView mListView;
    private UserAddrListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_addr_list);
        initView();
        initData();
    }

    private void initView() {
        mListView = findViewById(R.id.addr_list);
    }

    private void initData() {
        mAdapter= new UserAddrListAdapter();
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mListView.setAdapter(mAdapter);
        mListView.setLayoutManager(lm);
        mAdapter.setData(createTmpData(20));
    }

    private List<UserAddrListAdapter.Data> createTmpData(int count){
        List<UserAddrListAdapter.Data> datas = new ArrayList<>();
        for (int i = 0;i<count;i++){
            UserAddrListAdapter.Data data = new UserAddrListAdapter.Data();
            data.setName("小明第"+i+"号");
            data.setTel("1888"+i+"45852"+i);
            data.setAddr("华容道"+i+"号局");
            data.setAddrDetail("海淀区中关村知识产权大街"+i+"号路口华容道"+i+"号局");
            datas.add(data);
        }
        return datas;
    }
}
