package com.zed3.sipua.ui.model;

import com.zed3.sipua.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;

import java.util.List;

public interface IGroupInviteService {

    void getReceiveGroupInviteList(GroupInviteRequestListener listener);
    void deleteData(GroupInviteReceiveDataMap.GroupInviteReceiveData data,int pos);
    void setDataHandleListener(GroupInviteDataHandleListener listener);

    public interface GroupInviteRequestListener{
        void onSuccess(List<GroupInviteReceiveDataMap> data);
        void onFailed(int code);
    }

    public interface GroupInviteDataHandleListener<T>{
        void onDeleteSuccess(T data,int pos);
    }


}
