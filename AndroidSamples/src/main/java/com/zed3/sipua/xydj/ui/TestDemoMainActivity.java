package com.zed3.sipua.xydj.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lw.demo.android.samples.R;
import com.lw.demo.android.samples.SlidingUpPannelActivity;
import com.lw.demo.android.samples.ViewPagerActivity;
import com.lw.demo.android.samples.ViewPagerActivity2;
import com.lw.demo.android.samples.sharetran.ShareTranstraction;
import com.map.view.MapViewActivity;
import com.map.view.WaveCircleActivity;
import com.zed3.sipua.xydj.ui.friend.FriendListActivity;
import com.zed3.sipua.xydj.ui.group.GroupInfoActivity;
import com.zed3.sipua.xydj.ui.group.GroupMemberSearchActivity;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;
import com.zed3.sipua.xydj.ui.group.helper.GroupManager;

import java.util.ArrayList;
import java.util.List;

public class TestDemoMainActivity extends BaseActivity {

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_test_demo_main);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.create_new_group:
                GroupManager.createNewGroup(this,null);
                break;
            case R.id.show_group_member:
                Intent groupInfoIntent = new Intent(this, GroupInfoActivity.class);
                PttCustomGrp tmpGrp = createTmpGrp("自建组1号",60);
                groupInfoIntent.putExtra("grp",tmpGrp);
                startSpecifyActivity(groupInfoIntent);
                break;
            case R.id.create_bottom_pop:
                GroupManager.getIntance().showPopupWindow(this, findViewById(R.id.create_bottom_pop), new GroupManager.OnPopupWindowClickListener() {
                    @Override
                    public void onDelClickListener(View view) {
                        Toast.makeText(TestDemoMainActivity.this,"删除", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelClickListener(View view) {
                        Toast.makeText(TestDemoMainActivity.this,"取消", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.member_search:
                Intent searchIntent = new Intent(this, GroupMemberSearchActivity.class);
                searchIntent.putExtra("grp",createTmpGrp("自建组1号",1000));
                startSpecifyActivity(searchIntent);
                break;
            case R.id.view_pager:
                Intent viewPagerIntent = new Intent(this, ViewPagerActivity2.class);
                startSpecifyActivity(viewPagerIntent);
                break;
            case R.id.call_tel:
                Intent telIntent = new Intent(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:"+"10086");
                telIntent.setData(uri);
                telIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startSpecifyActivity(telIntent);
                break;
            case R.id.call_msg:
                Intent msgIntent = new Intent(Intent.ACTION_SENDTO);
                Uri uri2= Uri.parse("smsto:"+"10086121");
                msgIntent.setData(uri2);
                msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startSpecifyActivity(msgIntent);
                break;
            case R.id.user:
                Intent userIntent = new Intent(this,UserInfoActivity.class);
                startSpecifyActivity(userIntent);
                break;
            case R.id.map:
                Intent mapIntent = new Intent(this,MapViewActivity.class);
                startSpecifyActivity(mapIntent);
                break;
            case R.id.wave_circle:
                Intent waveIntent = new Intent(this,WaveCircleActivity.class);
                startSpecifyActivity(waveIntent);
                break;
            case R.id.sliding_up:
                Intent sldUpIntent = new Intent(this,SlidingUpPannelActivity.class);
                startSpecifyActivity(sldUpIntent);
                break;
            case R.id.share_transaction:
                Intent stIntent = new Intent(this,ShareTranstraction.class);
                startSpecifyActivity(stIntent);
                break;

            case R.id.friends_list:
                Intent frIntent = new Intent(this,FriendListActivity.class);
                startSpecifyActivity(frIntent);
                break;
        }
    }

    private PttCustomGrp createTmpGrp(String groupName,int memberCount) {
        PttCustomGrp tmp = new PttCustomGrp();
        tmp.setGroupName(groupName);
        tmp.setMaxMember(500);
        tmp.setExpiryDate("-1");
        List<CustomGroupMemberInfo> memberInfoList = new ArrayList<CustomGroupMemberInfo>();
        for (int i=0;i<memberCount;i++){
            CustomGroupMemberInfo info = new CustomGroupMemberInfo();
            if(i == 0){
                info.setMemberName("创建者");
                info.setMemberNum("1888"+i);
                info.setMemberStatus("3");
                tmp.setGroupCreatorName(info.getMemberName());
                tmp.setGroupCreatorNum(info.getMemberNum());
            }else{
                info.setMemberName("测试组员"+1+"号");
                info.setMemberNum("1888"+i);
                int status= i%4;
                info.setMemberStatus(status+"");
            }
            memberInfoList.add(info);
        }

        tmp.setMember_list(sortOnline(memberInfoList));
        return tmp;
    }

    private List<CustomGroupMemberInfo> sortOnline(
            List<CustomGroupMemberInfo> parseListInfo) {
        List<CustomGroupMemberInfo> newlist = new ArrayList<CustomGroupMemberInfo>();// 排序后的集合
        if (parseListInfo != null && parseListInfo.size() > 1) {
            List<CustomGroupMemberInfo> onLineMembers = new ArrayList<CustomGroupMemberInfo>();
            List<CustomGroupMemberInfo> notOnLineMembers = new ArrayList<CustomGroupMemberInfo>();
            for (int i = 0; i < parseListInfo.size(); i++) {
                CustomGroupMemberInfo groupInfo = parseListInfo.get(i);
                if (groupInfo.getMemberStatus().equals("0")) {// 离线
                    notOnLineMembers.add(groupInfo);
                } else {
                    onLineMembers.add(groupInfo);
                }
            }
            // 将不在线的成员添加到在线成员的集合后面
            if (notOnLineMembers.size() > 0) {
                onLineMembers.addAll(notOnLineMembers);
            }
            newlist = new ArrayList<CustomGroupMemberInfo>(onLineMembers);
        } else {
            newlist = parseListInfo;
        }
        return newlist;
    }

    private void startSpecifyActivity(Intent intent) {
        startActivity(intent);
    }
}
