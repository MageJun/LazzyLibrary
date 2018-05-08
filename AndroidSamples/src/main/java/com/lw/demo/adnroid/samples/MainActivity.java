package com.lw.demo.adnroid.samples;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.LinearLayout;

import com.zed3.sipua.xydj.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.adapter.FriendsListAdapter;
import com.zed3.sipua.xydj.ui.bean.FrindInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

        private FriendsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_draglistdemo);

        RecyclerView listView = findViewById(R.id.contentList);

        mAdapter = new FriendsListAdapter();
        int direction = LinearLayout.VERTICAL;
        LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setOrientation(direction);

        listView.setLayoutManager(lManager);

        listView.setAdapter(mAdapter);

        ItemDividerDecoration decoration = new ItemDividerDecoration(this, direction);
        decoration.setOffset(8);
        decoration.setColor(getResources().getColor(R.color.commonui_color_item1));

        listView.addItemDecoration(decoration);
        SimpleItemAnimator animator = (SimpleItemAnimator) listView.getItemAnimator();
        animator.setChangeDuration(0);//动画执行时间改为0，解决默认动画的闪屏动画问题
        animator.setSupportsChangeAnimations(false);
        mAdapter.addData(createData(20));
        mAdapter.setListener(new FriendsListAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mAdapter.delete(position);
            }
        });
    }

    private List<FrindInfo> createData(int count){
        List<FrindInfo> infos = new ArrayList<FrindInfo>();
        for (int i = 0;i<count;i++){
            FrindInfo info = new FrindInfo();
            info.setName("Name "+i+" 明");
            infos.add(info);
        }
        return infos;
    }


}
