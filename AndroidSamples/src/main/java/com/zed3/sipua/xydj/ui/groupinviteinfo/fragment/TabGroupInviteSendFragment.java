package com.zed3.sipua.xydj.ui.groupinviteinfo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.group.helper.PopupWindowHelper;
import com.zed3.sipua.xydj.ui.groupinviteinfo.adapter.GroupInviteSendAdapter;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;
import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteSendDataMap;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.OnItemLongClickListener;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.RecyclerViewOnItemTouchListener;

import java.util.ArrayList;
import java.util.List;

public class TabGroupInviteSendFragment extends Fragment {

    public static final String TAG = TabGroupInviteSendFragment.class.getSimpleName();
    private RecyclerView mListView;
    private GroupInviteSendAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xydj_fragment_group_sendinvite,container,false);
        mListView = view.findViewById(R.id.datalist);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        {
            super.onActivityCreated(savedInstanceState);
            mAdapter = new GroupInviteSendAdapter();
            mLayoutManager = new LinearLayoutManager(getActivity());
            mListView.setLayoutManager(mLayoutManager);
            mListView.addOnItemTouchListener(new RecyclerViewOnItemTouchListener());
            mAdapter.setItemLongClickListener(new OnItemLongClickListener<GroupInviteSendDataMap.GroupInviteSendData>() {
                @Override
                public void onItemLongClick(View v, GroupInviteSendDataMap.GroupInviteSendData groupInviteSendData, int pos) {
                    PopupWindowHelper.newInstance().showPopupWindow(getActivity(),v,null);
                }
            });
            mListView.setAdapter(mAdapter);
            ItemDividerDecoration divider = new ItemDividerDecoration(getActivity(), LinearLayout.VERTICAL);
            divider.setOffset(2);
            mListView.addItemDecoration(divider);
            List<GroupInviteSendDataMap> data = createSendTempData(4);
            mAdapter.setData(data);
        }
    }

    private List<GroupInviteSendDataMap> createSendTempData(int count){
        List<GroupInviteSendDataMap> mMaps = new ArrayList<GroupInviteSendDataMap>();
        for (int i = 0;i<count;i++){
            String mapName = "分组 "+i;
            if(i==0){
                mapName = "本周";
            }else if(i == 1){
                mapName = "上周";
            }else if(i == 2){
                mapName = "上月";
            }
            GroupInviteSendDataMap map = new GroupInviteSendDataMap();
            List<GroupInviteSendDataMap.GroupInviteSendData> datas = new ArrayList<GroupInviteSendDataMap.GroupInviteSendData>();
            map.setmMapName(mapName);
            for (int j=0;j<6;j++){
                String groupName = mapName+"——群组 "+j+"号";
                String inviter = "好友"+j;
                String inviteInfo ="您邀请您"+inviter+ "加入"+groupName+"群组";
                GroupInviteSendDataMap.GroupInviteSendData data = new GroupInviteSendDataMap.GroupInviteSendData();
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
