package com.zed3.sipua.ui.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.zed3.sipua.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;

import java.util.ArrayList;
import java.util.List;

public class GroupInviteServiceImpl implements IGroupInviteService {

    private HandlerThread mHandThread;
    private Handler mHandler;
    private GroupInviteDataHandleListener mDataHandleListener;
    private static final int MSG_GET_DATA_LIST= 0;
    private static final int MSG_DATA_HANDLE_DEL = 1;

    private Handler.Callback mCallBack = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MSG_GET_DATA_LIST:
                    GroupInviteRequestListener listener = (GroupInviteRequestListener) msg.obj;
                    getReceiveGroupInviteListInner(listener);
                    break;
                case MSG_DATA_HANDLE_DEL:
                    GroupInviteReceiveDataMap.GroupInviteReceiveData data = (GroupInviteReceiveDataMap.GroupInviteReceiveData) msg.obj;
                    int pos = msg.arg1;
                    //TODO 执行数据库删除操作，暂无
                    //默认删除成功
                    if(mDataHandleListener!=null){
                        mDataHandleListener.onDeleteSuccess(data,pos);
                    }
                    break;
            }
            return false;
        }
    };

    public GroupInviteServiceImpl(){
        mHandThread = new HandlerThread("GroupInviteServiceImpl Thread");
        mHandThread.start();
        mHandler = new Handler(mHandThread.getLooper(),mCallBack);
    }


    @Override
    public void getReceiveGroupInviteList(GroupInviteRequestListener listener) {
        Message msg = Message.obtain();
        msg.what = MSG_GET_DATA_LIST;
        msg.obj = listener;
        mHandler.sendMessage(msg);
    }

    private void getReceiveGroupInviteListInner(GroupInviteRequestListener listener){
        List<GroupInviteReceiveDataMap> list = createTempData(4);
        listener.onSuccess(list);
    }

    @Override
    public void deleteData(GroupInviteReceiveDataMap.GroupInviteReceiveData data,int pos) {
        Message msg = Message.obtain();
        msg.what =MSG_DATA_HANDLE_DEL;
        msg.obj = data;
        msg.arg1 = pos;
        mHandler.sendMessage(msg);
    }

    @Override
    public void setDataHandleListener(GroupInviteDataHandleListener listener) {
        this.mDataHandleListener = listener;
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
