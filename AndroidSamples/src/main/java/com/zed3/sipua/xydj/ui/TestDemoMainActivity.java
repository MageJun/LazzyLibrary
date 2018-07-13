package com.zed3.sipua.xydj.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.city.helper.ChinaCityHelper;
import com.city.view.CitySearchActivity;
import com.lazzy.common.lib.utils.ViewHelper;
import com.lw.demo.android.samples.R;
import com.lw.demo.android.samples.SingleSelectListActivity;
import com.lw.demo.android.samples.SlidingUpPannelActivity;
import com.lw.demo.android.samples.ViewPagerActivity;
import com.lw.demo.android.samples.ViewPagerActivity2;
import com.lw.demo.android.samples.customviews.TimePickerBuilderExtend;
import com.lw.demo.android.samples.customviews.TimePickerViewExtend;
import com.lw.demo.android.samples.sharetran.ShareTranstraction;
import com.map.view.MapViewActivity;
import com.map.view.WaveCircleActivity;
import com.zed3.sipua.xydj.ui.friend.FriendListActivity;
import com.zed3.sipua.xydj.ui.group.GroupInfoActivity;
import com.zed3.sipua.xydj.ui.group.GroupMemberSearchActivity;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;
import com.zed3.sipua.xydj.ui.group.helper.GroupManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            case R.id.city_list:
                ChinaCityHelper.getInstance(this);
                Intent cityIntent = new Intent(this, CitySearchActivity.class);
                startSpecifyActivity(cityIntent);
                break;
            case R.id.single_list:
                Intent singleIntent = new Intent(this, SingleSelectListActivity.class);
                startSpecifyActivity(singleIntent);
                break;
            case R.id.time_picker:
                showTimePicker();
                break;
        }
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
    int mode = 0;//0 取车时间 1 还车时间
    int defaultDelayDay = 2;//默认租车时间间隔
    Date pickupCarDate;
    Date backCarDate;
    private TextView pickupCarTime;
    private TextView backCarTime;
    private TimePickerViewExtend pvTime;
    private TextView pickerTitle;
    private void showTimePicker() {
        pickupCarDate = new Date(System.currentTimeMillis());
        backCarDate = new Date(pickupCarDate.getTime()+defaultDelayDay*24*60*60*1000);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(System.currentTimeMillis());
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(System.currentTimeMillis());
        endCalendar.set(Calendar.DAY_OF_YEAR,1);
        endCalendar.set(Calendar.HOUR_OF_DAY,0);
        endCalendar.set(Calendar.MINUTE,0);
        endCalendar.set(Calendar.SECOND,0);
        endCalendar.set(Calendar.MILLISECOND,0);
        endCalendar.add(Calendar.YEAR,1);
        endCalendar.add(Calendar.MILLISECOND,-1);
        //                                    pickupCarTime.setSelected(true);
//                                    tv_pickupCar.setSelected(true);
//                                    backCarTime.setSelected(true);
//                                    tv_backCar.setSelected(true);
//                                tv_pickupCar.setSelected(false);
//                                pickupCarTime.setSelected(false);
//                                tv_backCar.setSelected(false);
//                                backCarTime.setSelected(false);
        pvTime = new TimePickerBuilderExtend(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(TestDemoMainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        }).setType(new boolean[]{false,false,true,true,true,false})
                .setRangDate(startCalendar,endCalendar)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                            updateSelectTime(date);
                    }
                })
                .setLabel(null,null,"",null,null,null)
                .setLayoutRes(R.layout.pickerview_time_extend, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tv_pickupCar = v.findViewById(R.id.tv_pickup_car);
                        final TextView tv_backCar = v.findViewById(R.id.tv_back_car);
                        pickupCarTime = v.findViewById(R.id.tv_pickup_car_time);
                        backCarTime = v.findViewById(R.id.tv_back_car_time);
                        pickerTitle = v.findViewById(R.id.tvTitle);
                        final LinearLayout pickUpCarLayout = v.findViewById(R.id.pickup_car_line);
                        final LinearLayout backCarLayout = v.findViewById(R.id.back_car_line);
                        pickUpCarLayout.setSelected(true);
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()){
                                    case R.id.pickup_car_line:
                                        tabChecked(0);
                                        break;
                                    case R.id.back_car_line:
                                        tabChecked(1);
                                        break;
                                }
                            }

                            private void tabChecked(int index) {
                                Calendar calendar = Calendar.getInstance();
                                mode = index;
                                initTabStatus();
                                if (index == 0) {
                                    pickUpCarLayout.setSelected(true);
//                                    pickupCarTime.setSelected(true);
//                                    tv_pickupCar.setSelected(true);
                                    calendar.setTime(pickupCarDate);
                                } else if (index == 1) {
                                    backCarLayout.setSelected(true);
                                    calendar.setTime(backCarDate);
//                                    backCarTime.setSelected(true);
//                                    tv_backCar.setSelected(true);
                                }
                                pvTime.setDate(calendar);
                            }

                            private void initTabStatus() {
                                pickUpCarLayout.setSelected(false);
                                backCarLayout.setSelected(false);
//                                tv_pickupCar.setSelected(false);
//                                pickupCarTime.setSelected(false);
//                                tv_backCar.setSelected(false);
//                                backCarTime.setSelected(false);
                            }
                        };
                        pickUpCarLayout.setOnClickListener(listener);
                        backCarLayout.setOnClickListener(listener);
//                        pickUpCarLayout.performClick();
                    }
                })
                .build();

        pvTime.show();
    }

    @SuppressLint("StringFormatMatches")
    private void updateSelectTime(Date date) {
        if(mode==0){
            pickupCarDate = date;
            pickupCarTime.setText(dateFormat.format(pickupCarDate));
        }else if(mode==1){
            backCarDate = date;
            backCarTime.setText(dateFormat.format(backCarDate));
        }

        int day = differentDays(pickupCarDate,backCarDate);

        pickerTitle.setText(getResources().getString(R.string.time_picker_title,day));
    }

    private  int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //不同年
        {
            int timeDistance = 0 ;
            int min = Math.min(year1,year2);
            int max =  Math.max(year1,year2);
            for(int i = min ; i < max; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + Math.abs((day2-day1)) ;
        }
        else //同一年
        {
            return  Math.abs((day2-day1));
        }
    }



    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
    private String getTime(Date date) {

        return format.format(date);
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
