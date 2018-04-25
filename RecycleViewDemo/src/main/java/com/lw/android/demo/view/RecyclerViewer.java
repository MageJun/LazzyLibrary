package com.lw.android.demo.view;

import java.util.List;

/**
 * Created by zed on 2018/4/25.
 */

public interface RecyclerViewer<T> {

    void requestDate();

    void showProgress();

    void hideProgress();

    void onDataNotifytion(List<T> datas);
}
