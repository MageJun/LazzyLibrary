package com.zed3.sipua.xydj.ui.group.helper;

import com.zed3.sipua.xydj.ui.group.InviteMemberActivity;
import com.zed3.sipua.xydj.ui.model.GlobalService;
import com.zed3.sipua.xydj.ui.model.IGroupInviteService;
import com.zed3.sipua.xydj.ui.presenter.BasePresenter;

public class InvitePresenter extends BasePresenter<GroupViewer> {

    private GroupViewer mGroupViewer;
    private IGroupInviteService mService;

    public InvitePresenter(){
        mService = GlobalService.getGroupInviteService();
    }
    @Override
    public void attach(GroupViewer viewer) {
            this.mGroupViewer = viewer;
    }

    @Override
    public void detach(GroupViewer viewer) {
        this.mGroupViewer = null;
    }

    /**
     * 请求群口令
     */
    public void requestGroupPWD(){
        if(!checkViewer()){
            return ;
        }
        mService.getGroupInvitePWD(new IGroupInviteService.GroupInviteRequestListener<String>() {
            @Override
            public void onSuccess(String data) {
                if(!checkViewer()){
                    return ;
                }
                mGroupViewer.dismissProgress();

                if(mGroupViewer instanceof InviteMemberActivity){
                    ((InviteMemberActivity)mGroupViewer).requestGroupSharedPWDSuccess(data);
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    private boolean checkViewer(){
        return mGroupViewer!=null;
    }

}
