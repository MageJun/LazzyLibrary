package com.lw.demo.android.samples;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lazzy.common.lib.widget.recyclerview.adapter.SingleSelectRecyclerViewAdapter;
import com.lw.demo.android.samples.adapter.SelectSingleItemView;

import java.util.ArrayList;
import java.util.List;

public class SingleSelectListActivity extends AppCompatActivity {
    private SingleSelectRecyclerViewAdapter<DemoData> mAdapter;
    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select_list);
        mListView = findViewById(R.id.list_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mAdapter = new SingleSelectRecyclerViewAdapter<DemoData>() {
            @Override
            public int initSelectPos() {
                return 0;
            }

            @Override
            public void onCreateMulitTypeItemView() {
                addItemView(new SelectSingleItemView());
            }
        };
        mListView.setLayoutManager(lm);
        mListView.setAdapter(mAdapter);
        mAdapter.setData(createTmpData(20));
    }


    private List<DemoData> createTmpData(int count){
        List<DemoData> tmpDatas = new ArrayList<>();
        for (int i =0;i<count;i++){
            DemoData data = new DemoData();
            data.setText("单项选择测试数据"+i+"号数据");
            tmpDatas.add(data);
        }

        return tmpDatas;
    }


    public static class DemoData{
        boolean isSelect;
        String text;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
