package com.zed3.sipua.xydj.ui.group.helper;

import android.text.TextUtils;

import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;
import com.zed3.sipua.xydj.ui.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberSearchPresenter extends BasePresenter<GroupMemberSearchViewer> {

    private GroupMemberSearchViewer mViewer;
    private PttCustomGrp mGrp;

    @Override
    public void attach(GroupMemberSearchViewer viewer) {
        this.mViewer = viewer;
    }

    @Override
    public void detach(GroupMemberSearchViewer viewer) {
        this.mViewer = viewer;
    }

    public void bindData(PttCustomGrp grp){
        this.mGrp = grp;
    }
    public void unBindData(){
        this.mGrp = null;
    }

    public void search(final String key){
        if(TextUtils.isEmpty(key)){
            return ;
        }
        this.mViewer.showProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CustomGroupMemberInfo> results = searchMemebersByKey(key);

                mViewer.dismissProgress();

                mViewer.onSearchResult(results);
            }
        }).start();

    }

    private List<CustomGroupMemberInfo> searchMemebersByKey(String key){
        List<CustomGroupMemberInfo> results = new ArrayList<CustomGroupMemberInfo>();
        List<CustomGroupMemberInfo> memberInfos = mGrp.getMember_list();
        for (int i = 0;i<memberInfos.size();i++){
            CustomGroupMemberInfo info = memberInfos.get(i);
            if(info.getMemberName().contains(key)||info.getMemberNum().contains(key)){
                results.add(info);
                continue;
            }
        }
        return results;
    }


}
