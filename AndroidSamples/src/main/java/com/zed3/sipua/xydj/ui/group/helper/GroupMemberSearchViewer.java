package com.zed3.sipua.xydj.ui.group.helper;

import java.util.List;

public interface GroupMemberSearchViewer<T> {

    void showProgress();
    void dismissProgress();
    void onSearchResult(List<T> results);
}
