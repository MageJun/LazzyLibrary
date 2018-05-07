package com.zed3.sipua.ui.groupinviteinfo.helper;

import com.zed3.sipua.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap;
import com.zed3.sipua.ui.groupinviteinfo.bean.GroupInviteReceiveDataMap.GroupInviteReceiveData;
import com.zed3.sipua.ui.model.GlobalService;
import com.zed3.sipua.ui.model.IGroupInviteService;
import com.zed3.sipua.ui.presenter.BasePresenter;

import java.util.List;

public class GroupInvitePresenter extends BasePresenter<GroupInviteInfoViewer> implements IGroupInviteService.GroupInviteDataHandleListener<GroupInviteReceiveData> {

    private GroupInviteInfoViewer mViewer;
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
        if(!checkViewer()){
            return ;
        }
        mViewer.showProgress();
        mGropuInviteService.getReceiveGroupInviteList(new IGroupInviteService.GroupInviteRequestListener<List<GroupInviteReceiveDataMap>>() {
            @Override
            public void onSuccess(List<GroupInviteReceiveDataMap> data) {
                if(!checkViewer()){
                    return ;
                }
                mViewer.dismissProgress();
                mViewer.dataLoadComplete(data);
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    @Override
    public void attach(GroupInviteInfoViewer groupInviteInfoViewer) {
            this.mViewer = groupInviteInfoViewer;
    }

    @Override
    public void detach(GroupInviteInfoViewer groupInviteInfoViewer) {
        mGropuInviteService.quit();
        this.mViewer = null;
    }

    public interface  DataHandleListener<T>{
        void onDeleteSuccess(T data,int pos);
    }


    public void delete(GroupInviteReceiveDataMap.GroupInviteReceiveData data,int pos){
        if(!checkViewer()){
            return ;
        }
        mViewer.showProgress();
        mGropuInviteService.deleteData(data,pos);
    }

    @Override
    public void onDeleteSuccess(GroupInviteReceiveData data,int pos) {
        if(!checkViewer()){
            return ;
        }
        mViewer.dismissProgress();
        mDataHandleListener.onDeleteSuccess(data,pos);
    }


    private boolean checkViewer(){
        return mViewer!=null;
    }


}
