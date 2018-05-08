package com.zed3.sipua.xydj.ui.model;

import com.zed3.sipua.xydj.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;

public interface IGroupInviteService {

    void getReceiveGroupInviteList(GroupInviteRequestListener listener);
    void getGroupInvitePWD(GroupInviteRequestListener listener);
    void deleteData(GroupInviteReceiveDataMap.GroupInviteReceiveData data,int pos);
    void setDataHandleListener(GroupInviteDataHandleListener listener);
    void quit();

     interface GroupInviteRequestListener<T>{
        void onSuccess(T data);
        void onFailed(int code);
    }

     interface GroupInviteDataHandleListener<T>{
        void onDeleteSuccess(T data,int pos);
    }


}
