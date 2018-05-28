package com.lw.demo.android.samples.sharetran;

import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lw.demo.android.samples.R;

import java.util.ArrayList;
import java.util.List;

public class ShareTranstractionTargetActivity extends AppCompatActivity {

    private RecyclerView mHistoryListView,mLocListView;
    private SearchResultAdapter mHistoryAdapter,mLocAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_show_where_start);
        View view = findViewById(R.id.content);
        View title = findViewById(R.id.title_view);
        ViewCompat.setTransitionName(title,"title");
        ViewCompat.setTransitionName(view,"edt_title");

        initView();

        initData();
    }

    private void initData() {
        mHistoryAdapter = new SearchResultAdapter();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);

        mHistoryListView.setLayoutManager(layoutManager2);
        mHistoryListView.setAdapter(mHistoryAdapter);


        List<SearchResultAdapter.DataInfo> hisDatas = createTmpHistory(4);
        List<SearchResultAdapter.DataInfo> locDatas = createTmpLoc(15);
        SearchResultAdapter.DataInfo line = new SearchResultAdapter.DataInfo();
        line.setType(SearchResultAdapter.DataInfo.Type.LINE);
        hisDatas.add(line);
        hisDatas.addAll(locDatas);
        mHistoryAdapter.addData(hisDatas);
    }

    private void initView() {
        mHistoryListView = findViewById(R.id.list_view_history);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }


    private List<SearchResultAdapter.DataInfo> createTmpHistory(int count){
        List<SearchResultAdapter.DataInfo> infos = new ArrayList<>();
        for (int i = 0;i<count;i++){
            SearchResultAdapter.DataInfo info = new SearchResultAdapter.DataInfo();
            info.setTitle("兰福建"+i+"号居");
            info.setDetail("海淀大道鼓楼街"+i+"号，帝国兰福建"+i+"号居");
            info.setType(SearchResultAdapter.DataInfo.Type.HISTORY);
            infos.add(info);
        }
        return infos;
    }

    private List<SearchResultAdapter.DataInfo> createTmpLoc(int count){
        List<SearchResultAdapter.DataInfo> infos = new ArrayList<>();
        for (int i = 0;i<count;i++){
            SearchResultAdapter.DataInfo info = new SearchResultAdapter.DataInfo();
            info.setTitle("华容道"+i+"号尊");
            info.setDetail("昌平区纳洛鼓楼街"+i+"号，华容道"+i+"号尊");
            info.setType(SearchResultAdapter.DataInfo.Type.LOCINFO);
            infos.add(info);
        }
        return infos;
    }


}
