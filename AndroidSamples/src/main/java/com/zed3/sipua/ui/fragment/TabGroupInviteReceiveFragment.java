package com.zed3.sipua.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.ui.ItemDividerDecoration;
import com.zed3.sipua.ui.adapter.GroupInviteReceiveAdapter;
import com.zed3.sipua.ui.bean.GroupInviteReceiveDataMap;

import java.util.ArrayList;
import java.util.List;

public class TabGroupInviteReceiveFragment extends Fragment {

    public static final String TAG = TabGroupInviteReceiveFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private RecyclerView mListView;
    private GroupInviteReceiveAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xydj_groupinvite_fragment_content,container,false);
        mListView = view.findViewById(R.id.datalist);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new GroupInviteReceiveAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mAdapter);
        ItemDividerDecoration divider = new ItemDividerDecoration(getActivity(), LinearLayout.VERTICAL);
        divider.setOffset(2);
        mListView.addItemDecoration(divider);
        mAdapter.setData(createTempData(4));
    }

    private List<GroupInviteReceiveDataMap> createTempData(int count){
        List<GroupInviteReceiveDataMap> mMaps = new ArrayList<GroupInviteReceiveDataMap>();
        for (int i = 0;i<count;i++){
            String mapName = "分组 "+i;
            GroupInviteReceiveDataMap map = new GroupInviteReceiveDataMap();
            List<GroupInviteReceiveDataMap.GroupInviteReceiveData> datas = new ArrayList<GroupInviteReceiveDataMap.GroupInviteReceiveData>();
            map.setmMapName(mapName);
            for (int j=0;j<6;j++){
                String groupName = mapName+"——群组 "+j+"号";
                String inviter = "好友"+j;
                String inviteInfo =inviter+ "邀请您进入"+groupName;
                GroupInviteReceiveDataMap.GroupInviteReceiveData data = new GroupInviteReceiveDataMap.GroupInviteReceiveData();
                data.setGroupName(groupName);
                data.setInviteUser(inviter);
                data.setInviteInfo(inviteInfo);
                datas.add(data);

            }
            map.setmDatas(datas);
            mMaps.add(map);
        }

        return mMaps;
    }

}
