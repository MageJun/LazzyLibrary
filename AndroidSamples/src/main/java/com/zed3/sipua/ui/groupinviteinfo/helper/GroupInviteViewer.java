package com.zed3.sipua.ui.groupinviteinfo.helper;

public interface GroupInviteViewer<T> {

    void showProgress();

    void dismissProgress();

    void dataLoadComplete(T object);

}
