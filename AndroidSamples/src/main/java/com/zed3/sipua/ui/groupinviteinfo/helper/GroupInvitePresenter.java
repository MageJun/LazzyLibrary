package com.zed3.sipua.ui.groupinviteinfo.helper;

import android.support.annotation.NonNull;

import com.zed3.sipua.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;
import com.zed3.sipua.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap.GroupInviteReceiveData;
import com.zed3.sipua.ui.model.GlobalService;
import com.zed3.sipua.ui.model.IGroupInviteService;
import com.zed3.sipua.ui.presenter.BasePresenter;

import java.util.List;

public class GroupInvitePresenter extends BasePresenter<GroupInviteViewer> implements IGroupInviteService.GroupInviteDataHandleListener<GroupInviteReceiveData> {

    private GroupInviteViewer mViewer;
    private IGroupInviteService mGropuInviteService;
    private DataHandleListener mDataHandleListener;

    public GroupInvitePresenter () {
        mGropuInviteService = GlobalService.getGroupInviteService();
        mGropuInviteService.setDataHandleListener(this);
    }

    public void setDataHandleListener(DataHandleListener listener){
        this.mDataHandleListener = listener;
    }


    public void loadData(){
        mViewer.showProgress();
        mGropuInviteService.getReceiveGroupInviteList(new IGroupInviteService.GroupInviteRequestListener() {
            @Override
            public void onSuccess(List<GroupInviteReceiveDataMap> data) {
                mViewer.dismissProgress();
                mViewer.dataLoadComplete(data);
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    @Override
    public void attach(GroupInviteViewer groupInviteViewer) {
            this.mViewer = groupInviteViewer;
    }

    @Override
    public void detach(GroupInviteViewer groupInviteViewer) {
        this.mViewer = null;
        mGropuInviteService.quit();
    }

    public interface  DataHandleListener<T>{
        void onDeleteSuccess(T data,int pos);
    }


    public void delete(GroupInviteReceiveDataMap.GroupInviteReceiveData data,int pos){
        mViewer.showProgress();
        mGropuInviteService.deleteData(data,pos);
    }

    @Override
    public void onDeleteSuccess(GroupInviteReceiveData data,int pos) {
        mViewer.dismissProgress();
        mDataHandleListener.onDeleteSuccess(data,pos);
    }


}
