package com.zed3.sipua.xydj.ui.groupinviteinfo.helper;

public interface GroupInviteInfoViewer<T> {

    void showProgress();

    void dismissProgress();

    void dataLoadComplete(T object);

}
